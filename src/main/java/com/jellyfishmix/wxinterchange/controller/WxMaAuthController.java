package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.dto.WxMaCodeToSessionDTO;
import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.enums.UserEnum;
import com.jellyfishmix.wxinterchange.service.AccountService;
import com.jellyfishmix.wxinterchange.service.UserInfoService;
import com.jellyfishmix.wxinterchange.service.WxMaAuthService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JellyfishMIX
 * @date 2020/4/5 3:55 下午
 */
@RestController
@RequestMapping("/wxma_auth")
public class WxMaAuthController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private WxMaAuthService wxMaAuthService;

    /**
     * 微信code-openid换取
     * @param code 用户登录凭证（有效期五分钟）。开发者需要在开发者服务器后台调用 [auth.code2Session](https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html)，使用 code 换取 openid 和 session_key 等信息。
     */
    @GetMapping("/code_to_session")
    public ResultVO codeToSession(@RequestParam("code") String code) {
        WxMaCodeToSessionDTO wxMaCodeToSessionDTO = wxMaAuthService.codeToSession(code);
        if (!wxMaCodeToSessionDTO.getSuccess()) {
            return ResultVOUtil.fail(wxMaCodeToSessionDTO.getStateCode(), wxMaCodeToSessionDTO.getStateMsg());
        }
        return ResultVOUtil.success(wxMaCodeToSessionDTO.getStateCode(), wxMaCodeToSessionDTO.getStateMsg(), wxMaCodeToSessionDTO.getCodeToSessionSuccessResponse());
    }

    /**
     * 登录/注册
     * @param username 用户名
     * @param openid 微信用户openid
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(@RequestParam("username") String username,
                          @RequestParam("openid") String openid,
                          @RequestParam("avatarUrl") String avatarUrl) {
        // 查询openid是否已存在，未存在则执行注册逻辑
        UserInfoDTO userInfoDTO = userInfoService.queryByOpenid(openid);
        if (userInfoDTO.getStateCode().equals(UserEnum.USER_INFO_NULL.getStateCode())) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setOpenid(openid);
            userInfo.setAvatarUrl(avatarUrl);
            userInfoDTO = accountService.register(userInfo);
        }
        UserInfo userInfo = userInfoDTO.getUserInfo();
        return ResultVOUtil.success(UserEnum.SUCCESS.getStateCode(), UserEnum.SUCCESS.getStateMsg(), userInfo);
    }
}
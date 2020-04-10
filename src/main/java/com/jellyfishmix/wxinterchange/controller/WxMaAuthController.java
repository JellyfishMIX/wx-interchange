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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JellyfishMIX
 * @date 2020/4/5 3:55 下午
 */
@RestController
@RequestMapping("/wxma_auth")
@Slf4j
public class WxMaAuthController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private WxMaAuthService wxMaAuthService;

    /**
     * 微信code-openid换取
     * @param code
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
     * @param username
     * @param openid
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(@RequestParam("username") String username,
                          @RequestParam("openid") String openid,
                          @RequestParam("userAvatarUrl") String avatarUrl) {
        // 查询openid是否已存在，未存在则执行注册逻辑
        UserInfoDTO userInfoDTO = userInfoService.selectUserInfoByOpenid(openid);
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

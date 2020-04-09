package com.jellyfishmix.wxinterchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jellyfishmix.wxinterchange.config.WxMaConfig;
import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.enums.UserEnum;
import com.jellyfishmix.wxinterchange.enums.WxMaAuthErrorResponseEnum;
import com.jellyfishmix.wxinterchange.query.WxMaAuthErrorResponse;
import com.jellyfishmix.wxinterchange.query.WxMaAuthSuccessResponse;
import com.jellyfishmix.wxinterchange.service.AccountService;
import com.jellyfishmix.wxinterchange.service.UserInfoService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.utils.StateCodeEnumUtil;
import com.jellyfishmix.wxinterchange.utils.WxMaAuthResponseUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import com.jellyfishmix.wxinterchange.vo.WxMaAuthVO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author JellyfishMIX
 * @date 2020/4/5 3:55 下午
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WxMaController {
    @Autowired
    private WxMaConfig wxMaConfig;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AccountService accountService;

    /**
     * 微信code-openid换取
     * @param code
     */
    @PostMapping("/auth")
    public WxMaAuthVO auth(@RequestParam("code") String code) {
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("进入auth的方法");
        log.info("code = {}", code);

        // 拉取access_token和openid, method = GET
        String url = "https://api.weixin.qq.com/sns/jscode2session?"
                + "appid=" + wxMaConfig.getAppid()
                + "&secret=" + wxMaConfig.getSecret()
                + "&js_code=" + code
                + "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        // access_token和openid在response中，response是一个JSON数据包
        String responseJSON = restTemplate.getForObject(url, String.class);
        log.info("response={}", responseJSON);

        // responseJSON为失败的逻辑

        if (responseJSON == null) {
            WxMaAuthErrorResponse wxMaAuthErrorResponse = new WxMaAuthErrorResponse(WxMaAuthErrorResponseEnum.NULL);
            return WxMaAuthResponseUtil.fail(wxMaAuthErrorResponse);
        }

        String errorCode = "errcode";
        if (responseJSON.contains(errorCode)) {
            WxMaAuthErrorResponse wxMaAuthErrorResponse = null;
            JSONObject jsonObject = new JSONObject(responseJSON);
            Integer errcode = jsonObject.getInt("errcode");
            WxMaAuthErrorResponseEnum wxMaAuthErrorResponseEnum = StateCodeEnumUtil.getByStateCode(errcode, WxMaAuthErrorResponseEnum.class);
            if (wxMaAuthErrorResponseEnum == null) {
                wxMaAuthErrorResponse = new WxMaAuthErrorResponse(WxMaAuthErrorResponseEnum.UNKNOWN);
            } else {
                wxMaAuthErrorResponse = new WxMaAuthErrorResponse(wxMaAuthErrorResponseEnum);
            }
            return WxMaAuthResponseUtil.fail(wxMaAuthErrorResponse);
        }

        // responseJSON为成功的逻辑

        WxMaAuthSuccessResponse wxMaAuthSuccessResponse = null;
        try {
            wxMaAuthSuccessResponse = objectMapper.readValue(responseJSON, WxMaAuthSuccessResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return WxMaAuthResponseUtil.success(wxMaAuthSuccessResponse);
    }

    /**
     * 登录/注册
     * @param userName
     * @param openid
     * @return
     */
    public ResultVO login(@RequestParam("userName") String userName,
                          @RequestParam("openid") String openid) {
        // 查询openid是否已存在，未存在则执行注册逻辑
        UserInfoDTO userInfoDTO = userInfoService.selectUserInfoByOpenid(openid);
        if (!userInfoDTO.getStateCode().equals(UserEnum.SUCCESS.getStateCode())) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(userName);
            userInfo.setOpenid(openid);
            userInfoDTO = accountService.register(userInfo);
        }
        UserInfo userInfo = userInfoDTO.getUserInfo();
        return ResultVOUtil.success(UserEnum.SUCCESS.getStateCode(), UserEnum.SUCCESS.getStateInfo(), userInfo);
    }
}

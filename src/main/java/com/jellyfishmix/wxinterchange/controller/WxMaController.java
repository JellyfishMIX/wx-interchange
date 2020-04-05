package com.jellyfishmix.wxinterchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jellyfishmix.wxinterchange.config.WxMaConfig;
import com.jellyfishmix.wxinterchange.enums.WxMaAuthErrorResponseEnumState;
import com.jellyfishmix.wxinterchange.query.WxMaAuthErrorResponse;
import com.jellyfishmix.wxinterchange.query.WxMaAuthSuccessResponse;
import com.jellyfishmix.wxinterchange.utils.WxMaAuthResponseUtil;
import com.jellyfishmix.wxinterchange.vo.WxMaAuthVO;
import lombok.extern.slf4j.Slf4j;
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
            WxMaAuthErrorResponse wxMaAuthErrorResponse = new WxMaAuthErrorResponse(WxMaAuthErrorResponseEnumState.NULL);
            return WxMaAuthResponseUtil.fail(wxMaAuthErrorResponse);
        }

        String errorCode = "errcode";
        if (!responseJSON.contains(errorCode)) {
            WxMaAuthErrorResponse wxMaAuthErrorResponse = null;
            try {
                wxMaAuthErrorResponse = objectMapper.readValue(responseJSON, WxMaAuthErrorResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
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
}

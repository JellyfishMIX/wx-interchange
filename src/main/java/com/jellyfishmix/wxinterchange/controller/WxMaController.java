package com.jellyfishmix.wxinterchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jellyfishmix.wxinterchange.config.WxMaConfig;
import com.jellyfishmix.wxinterchange.entity.WxMaAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, String> auth(@RequestParam("code") String code) {
        Map<String, String> resultMap = new HashMap<>();

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

        WxMaAuthResponse wxMaAuthResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        if (responseJSON == null) {
            resultMap.put("error", "微信服务器返回JSON错误");
            return resultMap;
        }

        try {
             wxMaAuthResponse = mapper.readValue(responseJSON, WxMaAuthResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (wxMaAuthResponse == null) {
            resultMap.put("error", "无效的code");
            return resultMap;
        }
        resultMap.put("session_key", wxMaAuthResponse.getSession_key());
        resultMap.put("openid", wxMaAuthResponse.getOpenid());

        return resultMap;
    }
}

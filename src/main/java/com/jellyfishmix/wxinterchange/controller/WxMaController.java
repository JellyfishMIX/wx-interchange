package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.config.WxMaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    public void auth(@RequestParam("code") String code) {
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
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);
    }
}

package com.jellyfishmix.wxinterchange.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author JellyfishMIX
 * @date 2020/4/5 3:29 下午
 */
@Component
@ConfigurationProperties(prefix = "wx.miniapp.config")
@Getter
public class WxMaConfig {
    String appid;
    String secret;
}

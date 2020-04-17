package com.jellyfishmix.wxinterchange.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author JellyfishMIX
 * @date 2020/4/17 10:20 上午
 */
@Component
@ConfigurationProperties(prefix = "qiniu")
@Data
public class QiNiuConfig {
    String accessKey;
    String secretKey;
    String bucketName;
}

package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/4/5 6:02 下午
 */
@Data
public class WxMaAuthResponse {
    private String session_key;
    private String openid;
}

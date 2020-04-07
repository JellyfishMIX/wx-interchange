package com.jellyfishmix.wxinterchange.query;

import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/4/5 6:02 下午
 */
@Data
public class WxMaAuthSuccessResponse {
    private String openid;

    /**
     * 会话密钥，每次授权后动态刷新
     */
    private String session_key;
}

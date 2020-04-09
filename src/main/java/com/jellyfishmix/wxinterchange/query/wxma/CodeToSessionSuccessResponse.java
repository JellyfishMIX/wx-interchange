package com.jellyfishmix.wxinterchange.query.wxma;

import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/4/5 6:02 下午
 */
@Data
public class CodeToSessionSuccessResponse {
    private String openid;

    /**
     * 会话密钥，动态刷新
     */
    private String session_key;
}

package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.WxMaCodeToSessionDTO;

/**
 * @author JellyfishMIX
 * @date 2020/4/9 3:55 下午
 */
public interface WxMaAuthService {
    /**
     * 微信code换取openid和session_key
     * @param code
     * @return
     */
    WxMaCodeToSessionDTO codeToSession(String code);
}

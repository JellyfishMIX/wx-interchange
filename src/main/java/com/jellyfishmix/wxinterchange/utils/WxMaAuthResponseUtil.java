package com.jellyfishmix.wxinterchange.utils;

import com.jellyfishmix.wxinterchange.query.WxMaAuthErrorResponse;
import com.jellyfishmix.wxinterchange.query.WxMaAuthSuccessResponse;
import com.jellyfishmix.wxinterchange.vo.WxMaAuthVO;

/**
 * @author JellyfishMIX
 * @date 2020/4/6 2:48 上午
 */
public class WxMaAuthResponseUtil {
    public static WxMaAuthVO success(WxMaAuthSuccessResponse wxMaAuthSuccessResponse) {
        WxMaAuthVO<WxMaAuthSuccessResponse> wxMaAuthVO = new WxMaAuthVO<WxMaAuthSuccessResponse>();
        wxMaAuthVO.setStatus(0);
        wxMaAuthVO.setData(wxMaAuthSuccessResponse);
        return wxMaAuthVO;
    }

    public static WxMaAuthVO fail(WxMaAuthErrorResponse wxMaAuthErrorResponse) {
        WxMaAuthVO<WxMaAuthErrorResponse> wxMaAuthVO = new WxMaAuthVO<WxMaAuthErrorResponse>();
        wxMaAuthVO.setStatus(1);
        wxMaAuthVO.setData(wxMaAuthErrorResponse);
        return wxMaAuthVO;
    }
}

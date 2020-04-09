package com.jellyfishmix.wxinterchange.utils;

import com.jellyfishmix.wxinterchange.query.wxma.CodeToSessionErrorResponse;
import com.jellyfishmix.wxinterchange.query.wxma.CodeToSessionSuccessResponse;
import com.jellyfishmix.wxinterchange.vo.WxMaAuthVO;

/**
 * @author JellyfishMIX
 * @date 2020/4/6 2:48 上午
 */
public class WxMaAuthResponseUtil {
    public static WxMaAuthVO success(CodeToSessionSuccessResponse codeToSessionSuccessResponse) {
        WxMaAuthVO<CodeToSessionSuccessResponse> wxMaAuthVO = new WxMaAuthVO<CodeToSessionSuccessResponse>();
        wxMaAuthVO.setSuccess(true);
        wxMaAuthVO.setData(codeToSessionSuccessResponse);
        return wxMaAuthVO;
    }

    public static WxMaAuthVO fail(CodeToSessionErrorResponse codeToSessionErrorResponse) {
        WxMaAuthVO<CodeToSessionErrorResponse> wxMaAuthVO = new WxMaAuthVO<CodeToSessionErrorResponse>();
        wxMaAuthVO.setSuccess(false);
        wxMaAuthVO.setData(codeToSessionErrorResponse);
        return wxMaAuthVO;
    }
}

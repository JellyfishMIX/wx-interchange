package com.jellyfishmix.wxinterchange.query;

import com.jellyfishmix.wxinterchange.enums.WxMaAuthErrorResponseEnum;
import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/4/6 2:17 上午
 */
@Data
public class WxMaAuthErrorResponse {
    private Integer errcode;
    private String errmsg;

    public WxMaAuthErrorResponse(WxMaAuthErrorResponseEnum wxMaAuthErrorResponseEnum) {
        this.errcode = wxMaAuthErrorResponseEnum.getStateCode();
        this.errmsg = wxMaAuthErrorResponseEnum.getStateMsg();
    }
}

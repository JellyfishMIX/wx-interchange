package com.jellyfishmix.wxinterchange.query.wxma;

import com.jellyfishmix.wxinterchange.enums.WxMaAuthEnum;
import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/4/6 2:17 上午
 */
@Data
public class CodeToSessionErrorResponse {
    private Integer errcode;
    private String errmsg;

    public CodeToSessionErrorResponse(WxMaAuthEnum wxMaAuthEnum) {
        this.errcode = wxMaAuthEnum.getStateCode();
        this.errmsg = wxMaAuthEnum.getStateMsg();
    }
}

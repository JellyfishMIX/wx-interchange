package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/6 3:22 上午
 */
@Getter
public enum WxMaAuthErrorResponseEnumState implements StateCodeEnum {
    NULL(99, "微信服务器错误，response为空"),
    BUSY(-1, "系统繁忙，此时请开发者稍候再试"),
    REQUEST_SUCCESS(0, "请求成功"),
    CODE_INVALID(40029, "code无效"),
    FREQUENCY_LIMIT(45011, "频率限制，每个用户每分钟100次");

    private Integer stateCode;
    private String stateMsg;

    WxMaAuthErrorResponseEnumState(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}

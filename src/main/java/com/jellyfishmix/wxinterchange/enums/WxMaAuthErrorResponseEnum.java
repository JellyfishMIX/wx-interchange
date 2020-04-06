package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/6 3:22 上午
 * 开发中使用，暂时弃置
 */
@Getter
public enum WxMaAuthErrorResponseEnum implements StateCodeEnum {
    UNKNOWN(-99, "未知类型错误"),
    NULL(99, "微信服务器错误，response为空"),
    BUSY(-1, "系统繁忙，此时请开发者稍候再试"),
    REQUEST_SUCCESS(0, "请求成功"),
    CODE_INVALID(40029, "code无效"),
    FREQUENCY_LIMIT(45011, "频率限制，每个用户每分钟100次");

    private Integer stateCode;
    private String stateMsg;

    WxMaAuthErrorResponseEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}

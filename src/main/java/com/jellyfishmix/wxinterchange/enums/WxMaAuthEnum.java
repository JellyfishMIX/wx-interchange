package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/6 3:22 上午
 * 开发中使用，暂时弃置
 */
@Getter
public enum WxMaAuthEnum implements StateCodeEnum {
    // code换取openid Enum
    REQUEST_SUCCESS(0, "请求成功"),
    UNKNOWN(-99, "微信服务器返回未知类型错误"),
    NULL(99, "微信服务器错误，response为空"),
    BUSY(-1, "微信系统繁忙，请稍候再试"),
    CODE_INVALID(40029, "code无效"),
    FREQUENCY_LIMIT(45011, "微信频率限制，每个用户每分钟100次请求"),
    CODE_BEEN_USED(40163, "code已被使用过，请刷新code");

    private Integer stateCode;
    private String stateMsg;

    WxMaAuthEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}

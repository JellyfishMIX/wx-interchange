package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/17 10:43 上午
 */
@Getter
public enum  FileEnum implements StateCodeEnum {
    SUCCESS(0, "操作成功");

    private Integer stateCode;
    private String stateMsg;

    FileEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}

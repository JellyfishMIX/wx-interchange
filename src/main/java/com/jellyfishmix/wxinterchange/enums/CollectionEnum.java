package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/29 8:32 下午
 */
@Getter
public enum CollectionEnum implements StateCodeEnum {
    // 操作处理结果Enum
    SUCCESS(0, "操作成功"),

    // 操作标志Enum
    UPDATE_FILE_COUNT(101, "更新收藏集文件计数");

    private Integer stateCode;
    private String stateMsg;

    CollectionEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}

package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/6/4 4:33 下午
 */
@Getter
public enum SearchStatisticsEnum {
    // 操作处理结果Enum
    SUCCESS(0, "操作成功");

    private Integer stateCode;
    private String stateMsg;

    SearchStatisticsEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }

}

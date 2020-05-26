package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/5/26 8:59 上午
 */
@Getter
public enum FileStatisticsEnum {
    SUCCESS(0, "操作成功"),
    FILE_STATISTICS_NULL(1, "FileStatistics为空");


    private Integer stateCode;
    private String stateMsg;

    FileStatisticsEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}

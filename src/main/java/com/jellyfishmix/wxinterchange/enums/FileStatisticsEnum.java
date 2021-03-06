package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/5/26 8:59 上午
 */
@Getter
public enum FileStatisticsEnum implements StateCodeEnum {
    // 操作处理结果Enum
    SUCCESS(0, "操作成功"),
    FILE_STATISTICS_NULL(1, "FileStatistics为空"),
    DAILY_PROCESSING_ERROR(2, "FileStatistics日常统计处理出错");

    private Integer stateCode;
    private String stateMsg;

    FileStatisticsEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}

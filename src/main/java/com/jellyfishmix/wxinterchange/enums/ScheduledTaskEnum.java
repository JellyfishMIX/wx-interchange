package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/5/28 12:12 上午
 */
@Getter
public enum ScheduledTaskEnum implements StateCodeEnum {
    // 操作处理结果Enum
    SUCCESS(0, "操作成功"),
    START_EXCEPTION(1, "在scheduler.start()处出错"),
    SHUTDOWN_EXCEPTION(2, "在scheduler.shutdown()处出错");

    private Integer stateCode;
    private String stateMsg;

    ScheduledTaskEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}

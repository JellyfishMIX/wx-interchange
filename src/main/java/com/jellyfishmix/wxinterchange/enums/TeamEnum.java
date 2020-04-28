package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/11 9:28 下午
 */
@Getter
public enum TeamEnum implements StateCodeEnum {
    // 操作处理结果Enum
    SUCCESS(0, "操作成功"),
    TEAM_INFO_NULL(1, "项目组信息为空"),
    TEAM_INFO_PARAM_NULL(2, "项目组信息传参为空"),

    // 操作标志Enum
    UPDATE_NUMBER_COUNT(101, "更新项目组成员计数"),
    UPDATE_MANAGED_NUMBER_COUNT(103, "更新项目组管理员计数"),
    UPDATE_JOINED_NUMBER_COUNT(104, "更新项目组加入者计数"),
    UPDATE_FILE_COUNT(105, "更新项目组文件计数");

    private Integer stateCode;
    private String stateMsg;

    TeamEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}

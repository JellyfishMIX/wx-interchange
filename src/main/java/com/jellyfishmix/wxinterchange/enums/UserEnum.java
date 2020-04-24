package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 3:53 下午
 */
@Getter
public enum UserEnum implements StateCodeEnum {
    // 操作处理结果Enum
    SUCCESS(0, "操作成功"),
    USER_INFO_NULL(1, "用户信息为空"),
    INSERT_USER_INFO_ERROR(2, "新增用户信息错误"),
    UPDATE_USER_INFO_ERROR(3, "修改用户信息错误"),

    // 操作标志Enum
    UPDATE_CREATED_TEAM_COUNT(101, "更新用户创建的项目组数量"),
    UPDATE_MANAGED_TEAM_COUNT(102, "更新用户管理的项目组数量"),
    UPDATE_JOINED_TEAM_COUNT(103, "更新用户加入的项目组数量");

    private Integer stateCode;
    private String stateMsg;

    UserEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}
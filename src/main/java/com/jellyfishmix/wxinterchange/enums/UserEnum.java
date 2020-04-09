package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 3:53 下午
 */
@Getter
public enum UserEnum implements StateCodeEnum {
    SUCCESS(0, "操作成功"),
    USER_INFO_NULL(1, "用户信息为空"),
    INSERT_USER_INFO_ERROR(2, "新增用户信息错误"),
    UPDATE_USER_INFO_ERROR(3, "修改用户信息错误");

    private Integer stateCode;
    private String stateMsg;

    UserEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}
package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/5/2 4:39 下午
 */
@Getter
public enum  TeamUserEnum implements StateCodeEnum {
    // 项目组成员等级
    CREATED_NUMBER(1, "创建者"),
    MANAGED_NUMBER(2, "管理者"),
    JOINED_NUMBER(3, "加入者");

    private Integer stateCode;
    private String stateMsg;

    TeamUserEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}

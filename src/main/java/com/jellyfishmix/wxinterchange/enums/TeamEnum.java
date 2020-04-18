package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/11 9:28 下午
 */
@Getter
public enum TeamEnum implements StateCodeEnum {
    SUCCESS(0, "操作成功"),
    TEAM_INFO_NULL(1, "项目组信息为空");

    private Integer stateCode;
    private String stateMsg;

    TeamEnum(Integer stateCode, String stateMsg) {
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
    }
}
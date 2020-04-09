package com.jellyfishmix.wxinterchange.dto;

import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.enums.UserEnum;
import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/4/9 11:54 上午
 */
@Data
public class UserInfoDTO {
    private Integer stateCode;
    private String stateInfo;
    private UserInfo userInfo;

    public UserInfoDTO(UserEnum userEnum) {
        this.stateCode = userEnum.getStateCode();
        this.stateInfo = userEnum.getStateInfo();
    }

    public UserInfoDTO(UserEnum userEnum, UserInfo userInfo) {
        this.stateCode = userEnum.getStateCode();
        this.stateInfo = userEnum.getStateInfo();
        this.userInfo = userInfo;
    }
}

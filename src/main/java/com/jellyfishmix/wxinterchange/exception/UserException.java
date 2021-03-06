package com.jellyfishmix.wxinterchange.exception;

import com.jellyfishmix.wxinterchange.enums.UserEnum;
import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 2:34 下午
 */
@Getter
public class UserException extends RuntimeException {
    private static final long serialVersionUID = -716814775732772532L;

    private Integer stateCode;
    private String stateMsg;

    public UserException(UserEnum userEnum) {
        super(userEnum.getStateMsg());
        this.stateCode = userEnum.getStateCode();
        this.stateMsg = userEnum.getStateMsg();
    }
}

package com.jellyfishmix.wxinterchange.exception;

import com.jellyfishmix.wxinterchange.enums.TeamEnum;
import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/24 2:49 下午
 */
@Getter
public class TeamException extends RuntimeException {
    private static final long serialVersionUID = 1991050109332567844L;

    private Integer stateCode;
    private String stateMsg;

    public TeamException(TeamEnum teamEnum) {
        super(teamEnum.getStateMsg());
        this.stateCode = teamEnum.getStateCode();
        this.stateMsg = teamEnum.getStateMsg();
    }
}

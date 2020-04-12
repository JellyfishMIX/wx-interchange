package com.jellyfishmix.wxinterchange.dto;

import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.enums.TeamEnum;
import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/4/12 11:34 上午
 */
@Data
public class TeamInfoDTO {
    private Integer stateCode;
    private String stateMsg;
    private TeamInfo teamInfo;

    public TeamInfoDTO(TeamEnum teamEnum) {
        this.stateCode = teamEnum.getStateCode();
        this.stateMsg = teamEnum.getStateMsg();
    }

    public TeamInfoDTO(TeamEnum teamEnum, TeamInfo teamInfo) {
        this.stateCode = teamEnum.getStateCode();
        this.stateMsg = teamEnum.getStateMsg();
        this.teamInfo = teamInfo;
    }
}

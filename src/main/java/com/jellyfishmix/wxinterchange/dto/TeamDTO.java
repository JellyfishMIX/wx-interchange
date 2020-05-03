package com.jellyfishmix.wxinterchange.dto;

import com.jellyfishmix.wxinterchange.enums.TeamEnum;
import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/5/2 6:25 下午
 */
@Data
public class TeamDTO {
    private Integer stateCode;
    private String stateMsg;

    public TeamDTO(TeamEnum teamEnum) {
        this.stateCode = teamEnum.getStateCode();
        this.stateMsg = teamEnum.getStateMsg();
    }
}

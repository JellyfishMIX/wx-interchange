package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
import com.jellyfishmix.wxinterchange.enums.TeamEnum;
import com.jellyfishmix.wxinterchange.service.TeamInfoService;
import com.jellyfishmix.wxinterchange.service.TeamUserService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JellyfishMIX
 * @date 2020/4/11 10:52 上午
 */
@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamInfoService teamInfoService;
    @Autowired
    private TeamUserService teamUserService;

    @PostMapping("/create_team")
    public ResultVO create(@RequestParam("uid") String uid,
                           @RequestParam("teamName") String teamName,
                           @RequestParam("teamAvatarUrl") String teamAvatarUrl,
                           @RequestParam("teamGrade") Integer teamGrade,
                           @RequestParam("creatorAvatarUrl") String creatorAvatarUrl) {
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTeamName(teamName);
        teamInfo.setAvatarUrl(teamAvatarUrl);
        teamInfo.setGrade(teamGrade);
        teamInfo = teamInfoService.insert(teamInfo);

        // teamUser表中添加记录
        TeamUser teamUser = new TeamUser();
        teamUser.setTid(teamInfo.getTid());
        teamUser.setUid(uid);
        // userGrade，1 为创建者等级
        teamUser.setUserGrade(1);
        teamUser.setUserAvatarUrl(creatorAvatarUrl);

        teamUserService.insert(teamUser);

        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfo);
    }
}

package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.dto.TeamInfoDTO;
import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
import com.jellyfishmix.wxinterchange.enums.TeamEnum;
import com.jellyfishmix.wxinterchange.service.TeamInfoService;
import com.jellyfishmix.wxinterchange.service.TeamUserService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 创建项目组
     *
     * @param uid 创建者uid
     * @param teamName 项目组名称
     * @param teamAvatarUrl 项目组头像URL
     * @param teamGrade 项目组等级，官方项目组为1，普通项目组为2，保留0
     * @return
     */
    @PostMapping("/create_team")
    public ResultVO createTeam(@RequestParam("uid") String uid,
                               @RequestParam("teamName") String teamName,
                               @RequestParam("teamAvatarUrl") String teamAvatarUrl,
                               @RequestParam("teamGrade") Integer teamGrade) {
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTeamName(teamName);
        teamInfo.setAvatarUrl(teamAvatarUrl);
        teamInfo.setGrade(teamGrade);
        teamInfo = teamInfoService.insert(teamInfo);

        // teamUser表中添加记录
        TeamUser teamUser = new TeamUser();
        teamUser.setTid(teamInfo.getTid());
        teamUser.setUid(uid);
        teamUser.setTeamName(teamName);
        teamUser.setTeamAvatarUrl(teamAvatarUrl);
        // userGrade，1 为创建者等级
        teamUser.setUserGrade(1);

        teamUserService.insert(teamUser);

        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfo);
    }

    /**
     * 通过tid查询teamInfo
     *
     * @param tid 项目组tid
     * @return
     */
    @GetMapping("/query_team_info_by_tid")
    public ResultVO queryTeamInfoByTid(@RequestParam("tid") String tid) {
        TeamInfoDTO teamInfoDTO = teamInfoService.queryByTid(tid);
        // 查询teamInfo为null
        if (teamInfoDTO.getStateCode().equals(TeamEnum.TEAM_INFO_NULL.getStateCode())) {
            return ResultVOUtil.fail(TeamEnum.TEAM_INFO_NULL.getStateCode(), TeamEnum.TEAM_INFO_NULL.getStateMsg());
        }
        TeamInfo teamInfo = teamInfoDTO.getTeamInfo();
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfo);
    }
}

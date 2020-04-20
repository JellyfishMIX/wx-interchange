package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.dto.TeamInfoDTO;
import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
import com.jellyfishmix.wxinterchange.enums.TeamEnum;
import com.jellyfishmix.wxinterchange.enums.UserEnum;
import com.jellyfishmix.wxinterchange.service.TeamInfoService;
import com.jellyfishmix.wxinterchange.service.TeamUserService;
import com.jellyfishmix.wxinterchange.service.UserService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private UserService userService;

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
    @Transactional
    public ResultVO createTeam(@RequestParam("uid") String uid,
                               @RequestParam("teamName") String teamName,
                               @RequestParam("teamAvatarUrl") String teamAvatarUrl,
                               @RequestParam("teamGrade") Integer teamGrade) {
        // 校验uid是否存在
        UserInfoDTO userInfoDTO = userService.queryByUid(uid);
        if (userInfoDTO.getStateCode().equals(UserEnum.USER_INFO_NULL.getStateCode())) {
            return ResultVOUtil.fail(UserEnum.USER_INFO_NULL.getStateCode(), UserEnum.USER_INFO_NULL.getStateMsg());
        }

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

    /**
     * 通过tid查询项目组成员列表
     *
     * @param tid 项目组tid
     * @return
     */
    @GetMapping("/query_team_user_list_by_tid")
    public ResultVO queryTeamUserListByTid(@RequestParam("tid") String tid) {
        List<TeamUser> teamUserList = teamUserService.queryTeamUserListByTid(tid);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamUserList);
    }

    /**
     * 修改项目组名称
     *
     * @param tid 将要修改的项目组的tid
     * @param newTeamName 新项目组名称
     * @return
     */
    @PostMapping("/update_team_name")
    public ResultVO updateTeamName(@RequestParam("tid") String tid,
                                   @RequestParam("newTeamName") String newTeamName) {
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTid(tid);
        teamInfo.setTeamName(newTeamName);
        TeamInfoDTO teamInfoDTO = teamInfoService.update(teamInfo);
        // 如果tid对应的teamInfo为空
        if (teamInfoDTO.getStateCode().equals(TeamEnum.TEAM_INFO_NULL.getStateCode())) {
            return ResultVOUtil.fail(TeamEnum.TEAM_INFO_NULL.getStateCode(), TeamEnum.TEAM_INFO_NULL.getStateMsg());
        }
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfoDTO.getTeamInfo());
    }

    /**
     * 进入项目组
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    @PostMapping("/enter_team")
    @Transactional
    public ResultVO enterTeam(@RequestParam("tid") String tid,
                              @RequestParam("uid") String uid) {
        TeamInfoDTO teamInfoDTO = null;
        // 检查uid用户是否已加入了tid项目组
        TeamUser teamUser = teamUserService.queryTeamUserByTidAndUid(tid, uid);
        // 如果未加入，则将此uid用户加入tid对应的项目组
        if (teamUser == null) {
            teamInfoDTO = teamUserService.joinTeam(tid, uid);
            // teamInfoDTO判业务异常逻辑先不写
            return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfoDTO.getTeamInfo());
        }

        teamInfoDTO = teamInfoService.queryByTid(tid);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfoDTO.getTeamInfo());
    }

    /**
     * 获取官方项目组列表
     *
     * @return
     */
    @GetMapping("/query_official_team_list")
    public ResultVO queryOfficialTeamList() {
        List<TeamInfo> teamInfoList = teamInfoService.queryOfficialTeamList();
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfoList);
    }

    // @GetMapping("/delete_team_by_tid")
    // public ResultVO deleteTeamByTid(@RequestParam("tid") String tid) {
    //
    // }
}
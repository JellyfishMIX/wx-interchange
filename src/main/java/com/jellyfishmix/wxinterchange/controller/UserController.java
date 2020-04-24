package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.dto.TeamUserDTO;
import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.enums.UserEnum;
import com.jellyfishmix.wxinterchange.service.TeamService;
import com.jellyfishmix.wxinterchange.service.UserService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JellyfishMIX
 * @date 2020/4/12 11:19 上午
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TeamService teamService;

    /**
     * 通过uid查询userInfo
     *
     * @param uid 用户uid
     * @return
     */
    @GetMapping("/query_user_info_by_uid")
    public ResultVO queryUserInfoByUid(@RequestParam("uid") String uid) {
        UserInfoDTO userInfoDTO = userService.queryByUid(uid);
        // 查询userInfo为null
        if (userInfoDTO.getStateCode().equals(UserEnum.USER_INFO_NULL.getStateCode())) {
            return ResultVOUtil.fail(UserEnum.USER_INFO_NULL.getStateCode(), UserEnum.USER_INFO_NULL.getStateMsg());
        }
        UserInfo userInfo = userInfoDTO.getUserInfo();
        return ResultVOUtil.success(UserEnum.SUCCESS.getStateCode(), UserEnum.SUCCESS.getStateMsg(), userInfo);
    }

    /**
     * 获取我创建的项目组列表（非官方项目组）
     *
     * @param uid 用户uid
     * @return
     */
    @GetMapping("/query_created_team_list_by_uid")
    public ResultVO queryCreatedTeamListByUid(@RequestParam("uid") String uid) {
        Integer userGrade = 1;
        List<TeamUserDTO> teamUserDTOList = userService.queryTeamListByUidAndUserGrade(uid, userGrade);
        return ResultVOUtil.success(UserEnum.SUCCESS.getStateCode(), UserEnum.SUCCESS.getStateMsg(), teamUserDTOList);
    }

    /**
     * 获取我管理的项目组列表（非官方项目组）
     *
     * @param uid 用户uid
     * @return
     */
    @GetMapping("/query_managed_team_list_by_uid")
    public ResultVO queryManagedTeamListByUid(@RequestParam("uid") String uid) {
        Integer userGrade = 2;
        List<TeamUserDTO> teamUserDTOList = userService.queryTeamListByUidAndUserGrade(uid, userGrade);
        return ResultVOUtil.success(UserEnum.SUCCESS.getStateCode(), UserEnum.SUCCESS.getStateMsg(), teamUserDTOList);
    }

    /**
     * 获取我加入的项目组列表（非官方项目组）
     *
     * @param uid 用户uid
     * @return
     */
    @GetMapping("/query_joined_team_list_by_uid")
    public ResultVO queryJoinedTeamListByUid(@RequestParam("uid") String uid) {
        Integer userGrade = 3;
        List<TeamUserDTO> teamUserDTOList = userService.queryTeamListByUidAndUserGrade(uid, userGrade);
        return ResultVOUtil.success(UserEnum.SUCCESS.getStateCode(), UserEnum.SUCCESS.getStateMsg(), teamUserDTOList);
    }
}

package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.TeamDTO;
import com.jellyfishmix.wxinterchange.dto.TeamFileDTO;
import com.jellyfishmix.wxinterchange.dto.TeamInfoDTO;
import com.jellyfishmix.wxinterchange.dto.TeamUserDTO;
import com.jellyfishmix.wxinterchange.entity.*;
import com.jellyfishmix.wxinterchange.enums.TeamEnum;

import java.util.List;

/**
 * @author JellyfishMIX
 * @since 2020-04-11 21:09:44
 */
public interface TeamService {
    /**
     * 通过tid查询单条数据
     *
     * @param tid 项目组tid
     * @return 实例对象
     */
    TeamInfoDTO queryTeamInfoByTid(String tid);

    /**
     * 获取官方项目组列表
     *
     * @return
     */
    List<TeamInfo> queryOfficialTeamList();

    /**
     * 通过tid查询项目组成员列表
     *
     * @param tid 项目组tid
     * @param pageIndex 页码，从1开始
     * @param pageSize 每页容量
     * @return
     */
    List<TeamUserDTO> queryTeamUserListByTid(String tid, int pageIndex, int pageSize);

    /**
     * 通过tid和uid查询单条teamUser
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    TeamUser queryTeamUserByTidAndUid(String tid, String uid);

    /**
     * 查询项目组文件列表，通过上传日期排序
     *
     * @param tid 项目组tid
     * @param pageIndex 页码，从1开始
     * @param pageSize 每页容量
     * @return
     */
    List<TeamFileDTO> queryTeamFileListOrderByCreationTime(String tid, int pageIndex, int pageSize);

    /**
     * 通过关键词搜索项目组内的文件
     *
     * @param tid 项目组tid
     * @param keyword 关键词
     * @param pageIndex 页码，从1开始
     * @param pageSize 每页行数
     * @return
     */
    List<TeamFileDTO> searchTeamFileListByKeyword(String tid, String keyword, int pageIndex, int pageSize);

    /**
     * 创建项目组
     *
     * @param teamInfo 实例对象
     * @param teamUser 实例对象
     * @return
     */
    TeamInfo createTeam(TeamInfo teamInfo, TeamUser teamUser);

    /**
     * 向项目组上传文件
     *
     * @param tid 项目组tid
     * @param uid 上传者uid
     * @param fileInfoList 文件信息List
     */
    void uploadFileListToTeam(String tid, String uid, List<FileInfo> fileInfoList);

    /**
     * 将fileId文件添加至项目组
     *
     * @param tid 项目组tid
     * @param uid 操作者uid
     * @param fileInfoList 列表
     */
    void addFileListToTeamByFileIdList(String tid, String uid, List<FileInfo> fileInfoList);

    /**
     * 向项目组上传项目组头像文件
     *
     * @param tid 项目组tid
     * @param teamAvatar 项目组头像文件
     * @return 项目组头像文件TeamAvatar
     */
    TeamAvatar uploadTeamAvatar(String tid, TeamAvatar teamAvatar);

    /**
     * 加入项目组
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    TeamInfoDTO joinTeam(String tid, String uid);

    /**
     * 修改数据
     *
     * @param teamInfo 实例对象
     * @return 实例对象
     */
    TeamInfo updateTeamInfo(TeamInfo teamInfo);

    /**
     * 更新项目组的计数属性
     *
     * @param tid 项目组tid
     * @param teamEnum 操作标志Enum
     * @param countChangeNum 计数更改的数量，有正负
     */
    void updateTeamInfoCountProperty(String tid, TeamEnum teamEnum, Integer countChangeNum);

    /**
     * 删除项目组内的文件（单个）
     *
     * @param tid 项目组tid
     * @param fileId 文件fileId
     * @return
     */
    void deleteFileFromTeam(String tid, String fileId);

    /**
     * 删除项目组内的文件（多个）
     *
     * @param tid 项目组tid
     * @param fileInfoList 文件信息list
     * @return
     */
    void deleteFileListFromTeam(String tid, List<FileInfo> fileInfoList);

    /**
     * 删除项目组成员
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return 包含stateCode和stateMsg的TeamDTO
     */
    TeamDTO deleteTeamUser(String tid, String uid);

    /**
     * 解散项目组
     *
     * @param uid 用户uid
     * @param tid 项目组tid
     * @return 包含stateCode和stateMsg的TeamDTO
     */
    TeamDTO disbandGroup(String uid, String tid);
}
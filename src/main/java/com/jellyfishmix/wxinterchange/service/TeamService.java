package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.FileInfoDTO;
import com.jellyfishmix.wxinterchange.dto.TeamFileDTO;
import com.jellyfishmix.wxinterchange.dto.TeamInfoDTO;
import com.jellyfishmix.wxinterchange.dto.TeamUserDTO;
import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.entity.TeamFile;
import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
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
     * @return
     */
    List<TeamUserDTO> queryTeamUserListByTid(String tid);

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
     * @param fileInfo 实例对象
     * @param teamFile 项目组文件对象
     * @return 实例对象
     */
    FileInfoDTO uploadFileToTeam(FileInfo fileInfo, TeamFile teamFile);

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
    TeamInfoDTO updateTeamInfo(TeamInfo teamInfo);

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
}
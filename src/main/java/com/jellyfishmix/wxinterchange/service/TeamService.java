package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.TeamInfoDTO;
import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.entity.TeamFile;
import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
import java.util.List;

/**
 * 项目组成员表(TeamUser)表服务接口
 *
 * @author makejava
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
     * 通过uid和userGrade查询我所在的项目组（非官方组）
     *
     * @param uid 用户uid
     * @param userGrade 项目组成员等级
     * @return 对象列表
     */
    List<TeamUser> queryTeamListByUidAndUserGrade(String uid, Integer userGrade);

    /**
     * 通过tid查询项目组成员列表
     *
     * @param tid 项目组tid
     * @return
     */
    List<TeamUser> queryTeamUserListByTid(String tid);

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
     * @param pageIndex 页码
     * @param pageSize 每页容量
     * @return
     */
    List<TeamFile> queryTeamFileListOrderByCreationTime(String tid, int pageIndex, int pageSize);

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
    FileInfo uploadFileToTeam(FileInfo fileInfo, TeamFile teamFile);

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
     * 修改项目组文件计数
     *
     * @param tid 项目组tid
     * @param countChangeNum 计数更改的数量，有正负
     * @return
     */
    void updateFileCount(String tid, Integer countChangeNum);

    /**
     * 删除项目组内的文件（单个）
     *
     * @param tid 项目组tid
     * @param fileId 文件fileId
     * @return
     */
    void deleteFileFromTeam(String tid, String fileId);
}
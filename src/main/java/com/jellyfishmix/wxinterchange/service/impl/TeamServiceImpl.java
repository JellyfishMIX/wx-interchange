package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dao.FileInfoDao;
import com.jellyfishmix.wxinterchange.dao.TeamFileDao;
import com.jellyfishmix.wxinterchange.dao.TeamInfoDao;
import com.jellyfishmix.wxinterchange.dto.TeamInfoDTO;
import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.entity.TeamFile;
import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
import com.jellyfishmix.wxinterchange.dao.TeamUserDao;
import com.jellyfishmix.wxinterchange.enums.TeamEnum;
import com.jellyfishmix.wxinterchange.service.TeamService;
import com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目组成员表(TeamUser)表服务实现类
 *
 * @author makejava
 * @since 2020-04-11 21:09:44
 */
@Service("teamService")
public class TeamServiceImpl implements TeamService {
    @Resource
    private TeamUserDao teamUserDao;
    @Resource
    private TeamInfoDao teamInfoDao;
    @Resource
    private FileInfoDao fileInfoDao;
    @Resource
    private TeamFileDao teamFileDao;

    /**
     * 通过tid查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    @Override
    public TeamInfoDTO queryTeamInfoByTid(String tid) {
        TeamInfo teamInfo = teamInfoDao.queryByTid(tid);
        TeamInfoDTO teamInfoDTO = null;
        // 查询teamInfo为null
        if (teamInfo == null) {
            teamInfoDTO = new TeamInfoDTO(TeamEnum.TEAM_INFO_NULL);
            return teamInfoDTO;
        }
        teamInfoDTO = new TeamInfoDTO(TeamEnum.SUCCESS, teamInfo);
        return teamInfoDTO;
    }

    /**
     * 获取官方项目组列表
     *
     * @return
     */
    @Override
    public List<TeamInfo> queryOfficialTeamList() {
        List<TeamInfo> teamInfoList = teamInfoDao.queryOfficialTeamList();
        return teamInfoList;
    }

    /**
     * 通过uid和userGrade查询我所在的项目组（非官方组）
     *
     * @param uid 用户uid
     * @param userGrade 项目组成员等级
     * @return 对象列表
     */
    @Override
    public List<TeamUser> queryTeamListByUidAndUserGrade(String uid, Integer userGrade) {
        List<TeamUser> teamUserList = teamUserDao.queryTeamListByUidAndUserGrade(uid, userGrade);
        return teamUserList;
    }

    /**
     * 通过tid查询项目组成员列表
     *
     * @param tid 项目组tid
     * @return
     */
    @Override
    public List<TeamUser> queryTeamUserListByTid(String tid) {
        List<TeamUser> teamUserList = teamUserDao.queryTeamUserListByTid(tid);
        return teamUserList;
    }

    /**
     * 通过uid和tid查询单条teamUser
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    @Override
    public TeamUser queryTeamUserByTidAndUid(String tid, String uid) {
        TeamUser teamUser = teamUserDao.queryTeamUserByTidAndUid(tid, uid);
        return teamUser;
    }

    /**
     * 查询项目组文件列表，通过上传日期排序
     *
     * @param tid 项目组tid
     * @param pageIndex 页码
     * @param pageSize 每页容量
     * @return
     */
    @Override
    public List<TeamFile> queryTeamFileListOrderByCreationTime(String tid, int pageIndex, int pageSize) {
        List<TeamFile> teamFileList = teamFileDao.queryTeamFileListOrderByCreationTime(tid, pageIndex, pageSize);
        return teamFileList;
    }

    /**
     * 创建项目组
     *
     * @param teamInfo 实例对象
     * @param teamUser 实例对象
     * @return 实例对象
     */
    @Override
    public TeamInfo createTeam(TeamInfo teamInfo, TeamUser teamUser) {
        // 生成唯一tid
        String tid = UniqueKeyUtil.getUniqueKey();
        teamInfo.setTid(tid);
        this.teamInfoDao.insert(teamInfo);
        teamUser.setTid(tid);
        this.teamUserDao.insert(teamUser);

        // 查询新insert的teamInfo信息
        teamInfo = teamInfoDao.queryByTid(tid);
        return teamInfo;
    }

    /**
     * 向项目组上传文件
     *
     * @param fileInfo 实例对象
     * @return 实例对象
     */
    @Override
    public FileInfo uploadFileToTeam(FileInfo fileInfo, TeamFile teamFile) {
        String fileId = UniqueKeyUtil.getUniqueKey();
        fileInfo.setFileId(fileId);
        teamFile.setFileId(fileId);
        this.fileInfoDao.insert(fileInfo);
        this.teamFileDao.insert(teamFile);

        String tid = teamFile.getTid();
        // 修改项目组文件计数
        this.updateFileCount(tid, 1);

        fileInfo = fileInfoDao.queryByFileId(fileInfo.getFileId());
        return fileInfo;
    }

    /**
     * 加入项目组
     *
     * @param uid 用户uid
     * @param tid 项目组tid
     * @return
     */
    @Override
    public TeamInfoDTO joinTeam(String tid, String uid) {
        TeamInfo teamInfoFromQuery = teamInfoDao.queryByTid(tid);
        // 新成员加入，项目组成员++
        teamInfoFromQuery.setNumberCount(teamInfoFromQuery.getNumberCount() + 1);
        teamInfoFromQuery.setJoinedNumberCount(teamInfoFromQuery.getJoinedNumberCount() + 1);

        TeamInfo teamInfoForUpdate = new TeamInfo();
        teamInfoForUpdate.setTid(tid);
        teamInfoForUpdate.setNumberCount(teamInfoFromQuery.getNumberCount());
        teamInfoForUpdate.setJoinedNumberCount(teamInfoFromQuery.getJoinedNumberCount());
        teamInfoDao.updateByTid(teamInfoForUpdate);

        // teamUser表中添加记录
        TeamUser teamUser = new TeamUser();
        teamUser.setTid(teamInfoFromQuery.getTid());
        teamUser.setUid(uid);
        // userGrade，3 为普通成员等级
        teamUser.setUserGrade(3);

        teamUserDao.insert(teamUser);
        return new TeamInfoDTO(TeamEnum.SUCCESS, teamInfoFromQuery);
    }

    /**
     * 修改数据
     *
     * @param teamInfo 实例对象
     * @return 实例对象
     */
    @Override
    public TeamInfoDTO updateTeamInfo(TeamInfo teamInfo) {
        int effectedNum = this.teamInfoDao.updateByTid(teamInfo);
        TeamInfoDTO teamInfoDTO = null;
        if (effectedNum <= 0) {
            teamInfoDTO = new TeamInfoDTO(TeamEnum.TEAM_INFO_NULL);
            return teamInfoDTO;
        }
        teamInfoDTO = this.queryTeamInfoByTid(teamInfo.getTid());
        return teamInfoDTO;
    }

    /**
     * 修改项目组文件计数
     *
     * @param tid            项目组tid
     * @param countChangeNum 计数更改的数量，有正负
     * @return
     */
    @Override
    public void updateFileCount(String tid, Integer countChangeNum) {
        TeamInfo teamInfoFromQuery = teamInfoDao.queryByTid(tid);
        TeamInfo teamInfoForUpdate = new TeamInfo();
        teamInfoForUpdate.setTid(tid);
        teamInfoForUpdate.setFileCount(teamInfoFromQuery.getFileCount() + countChangeNum);
        teamInfoDao.updateByTid(teamInfoForUpdate);
    }

    /**
     * 删除项目组内的文件（单个）
     *
     * @param tid    项目组tid
     * @param fileId 文件fileId
     * @return
     */
    @Override
    public void deleteFileFromTeam(String tid, String fileId) {
        teamFileDao.deleteByFileId(fileId);
        fileInfoDao.deleteByFileId(fileId);

        this.updateFileCount(tid, -1);
    }
}
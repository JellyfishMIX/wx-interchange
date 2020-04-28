package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dao.*;
import com.jellyfishmix.wxinterchange.dto.FileInfoDTO;
import com.jellyfishmix.wxinterchange.dto.TeamFileDTO;
import com.jellyfishmix.wxinterchange.dto.TeamInfoDTO;
import com.jellyfishmix.wxinterchange.dto.TeamUserDTO;
import com.jellyfishmix.wxinterchange.entity.*;
import com.jellyfishmix.wxinterchange.enums.TeamEnum;
import com.jellyfishmix.wxinterchange.enums.UserEnum;
import com.jellyfishmix.wxinterchange.exception.TeamException;
import com.jellyfishmix.wxinterchange.service.FileService;
import com.jellyfishmix.wxinterchange.service.RedisLockService;
import com.jellyfishmix.wxinterchange.service.TeamService;
import com.jellyfishmix.wxinterchange.service.UserService;
import com.jellyfishmix.wxinterchange.utils.PageCalculatorUtil;
import com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JellyfishMIX
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
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Resource
    private TeamAvatarDao teamAvatarDao;
    @Autowired
    private RedisLockService redisLockService;

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
     * 通过tid查询项目组成员列表
     *
     * @param tid 项目组tid
     * @return
     */
    @Override
    public List<TeamUserDTO> queryTeamUserListByTid(String tid) {
        List<TeamUserDTO> teamUserList = teamUserDao.queryTeamUserListByTid(tid);
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
     * @param pageIndex 页码，从1开始
     * @param pageSize 每页容量
     * @return
     */
    @Override
    public List<TeamFileDTO> queryTeamFileListOrderByCreationTime(String tid, int pageIndex, int pageSize) {
        int rowIndex = PageCalculatorUtil.calculatorRowIndex(pageIndex, pageSize);
        return teamFileDao.queryTeamFileListOrderByCreationTime(tid, rowIndex, pageSize);
    }

    /**
     * 通过关键词搜索项目组内的文件
     *
     * @param tid       项目组tid
     * @param keyword   关键词
     * @param pageIndex 页码，从1开始
     * @param pageSize  每页行数
     * @return
     */
    @Override
    public List<TeamFileDTO> searchTeamFileListByKeyword(String tid, String keyword, int pageIndex, int pageSize) {
        int rowIndex = PageCalculatorUtil.calculatorRowIndex(pageIndex, pageSize);
        return teamFileDao.queryTeamFileListByKeyword(tid, keyword, rowIndex, pageSize);
    }

    /**
     * 创建项目组
     *
     * @param teamInfo 实例对象
     * @param teamUser 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = TeamException.class)
    public TeamInfo createTeam(TeamInfo teamInfo, TeamUser teamUser) {
        // 生成唯一tid
        String tid = UniqueKeyUtil.getUniqueKey();
        teamInfo.setTid(tid);
        this.teamInfoDao.insert(teamInfo);
        teamUser.setTid(tid);
        this.teamUserDao.insert(teamUser);

        // uid用户，createdTeamCount ++
        userService.updateUserInfoCountProperty(teamUser.getUid(), UserEnum.UPDATE_CREATED_TEAM_COUNT, 1);

        // 查询新insert的teamInfo信息
        teamInfo = teamInfoDao.queryByTid(tid);
        return teamInfo;
    }

    /**
     * 向项目组上传文件
     *
     * @param tid 项目组tid
     * @param uid 上传者uid
     * @param fileInfoList 文件信息List
     */
    @Override
    @Transactional(rollbackFor = TeamException.class)
    public void uploadFileToTeam(String tid, String uid, List<FileInfo> fileInfoList) {
        List<TeamFile>  teamFileList = new ArrayList<>();
        for (int i = 0; i < fileInfoList.size(); i++) {
            String fileId = UniqueKeyUtil.getUniqueKey();
            FileInfo fileInfo = fileInfoList.get(i);
            fileInfo.setFileId(fileId);
            fileInfo.setUid(uid);
            fileInfoList.set(i, fileInfo);

            TeamFile teamFile = new TeamFile();
            teamFile.setTid(tid);
            teamFile.setFileId(fileId);
            teamFile.setUid(uid);
            teamFileList.add(teamFile);
        }
        this.fileInfoDao.insertList(fileInfoList);

        // 加分布式锁，避免出现S锁和X锁循环等待死锁
        // 分布式锁过期时间
        int timeout = 20 * 1000;
        long time = System.currentTimeMillis() + timeout;
        // 加锁
        while (!redisLockService.lock(tid, String.valueOf(time))) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.teamFileDao.insertList(teamFileList);
        // 修改项目组文件计数
        this.updateTeamInfoCountProperty(tid, TeamEnum.UPDATE_FILE_COUNT, fileInfoList.size());

        // 解锁
        redisLockService.unlock(tid, String.valueOf(time));
    }

    /**
     * 向项目组上传项目组头像文件
     *
     * @param tid        项目组tid
     * @param teamAvatar 项目组头像文件
     */
    @Override
    public TeamAvatar uploadTeamAvatar(String tid, TeamAvatar teamAvatar) {
        String avatarId = UniqueKeyUtil.getUniqueKey();
        teamAvatar.setAvatarId(avatarId);
        teamAvatarDao.insert(teamAvatar);
        // 删除原头像操作，更改项目组头像URL时再做
        return teamAvatar;
    }

    /**
     * 加入项目组
     *
     * @param uid 用户uid
     * @param tid 项目组tid
     * @return
     */
    @Override
    @Transactional(rollbackFor = TeamException.class)
    public TeamInfoDTO joinTeam(String tid, String uid) {
        // 新成员加入，项目组成员++
        this.updateTeamInfoCountProperty(tid, TeamEnum.UPDATE_NUMBER_COUNT, 1);
        this.updateTeamInfoCountProperty(tid, TeamEnum.UPDATE_JOINED_NUMBER_COUNT, 1);

        // teamUser表中添加记录
        TeamInfo teamInfoFromQuery = teamInfoDao.queryByTid(tid);
        TeamUser teamUser = new TeamUser();
        teamUser.setTid(teamInfoFromQuery.getTid());
        teamUser.setUid(uid);
        // userGrade，3 为普通成员等级
        teamUser.setUserGrade(3);
        teamUserDao.insert(teamUser);

        // 修改uid用户加入的项目组数量
        userService.updateUserInfoCountProperty(uid, UserEnum.UPDATE_JOINED_TEAM_COUNT, 1);
        return new TeamInfoDTO(TeamEnum.SUCCESS, teamInfoFromQuery);
    }

    /**
     * 修改数据
     *
     * @param teamInfo 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = TeamException.class)
    public TeamInfo updateTeamInfo(TeamInfo teamInfo) {
        teamInfoDao.updateByTid(teamInfo);
        // 删除原头像文件
        if (teamInfo.getAvatarUrl() != null) {
            TeamAvatar oldTeamAvatar = teamAvatarDao.queryByTid(teamInfo.getTid());
            if (oldTeamAvatar != null) {
                teamAvatarDao.deleteByAvatarId(oldTeamAvatar.getAvatarId());
                // 从七牛云bucket删除资源
                fileService.deleteFromQiniuBucket(oldTeamAvatar.getFileHash(), oldTeamAvatar.getFileKey());
            }
        }
        TeamInfo teamInfoFromQuery = teamInfoDao.queryByTid(teamInfo.getTid());
        return teamInfoFromQuery;
    }

    /**
     * 更新项目组的计数属性
     *
     * @param tid            项目组tid
     * @param teamEnum       操作标志Enum
     * @param countChangeNum 计数更改的数量，有正负
     */
    @Override
    public void updateTeamInfoCountProperty(String tid, TeamEnum teamEnum, Integer countChangeNum) {
        TeamInfo teamInfoFromQuery = teamInfoDao.queryByTid(tid);
        TeamInfo teamInfoForUpdate = new TeamInfo();
        teamInfoForUpdate.setTid(tid);
        if (teamEnum.getStateCode().equals(TeamEnum.UPDATE_NUMBER_COUNT.getStateCode())) {
            teamInfoForUpdate.setNumberCount(teamInfoFromQuery.getNumberCount() + countChangeNum);
        } else if (teamEnum.getStateCode().equals(TeamEnum.UPDATE_MANAGED_NUMBER_COUNT.getStateCode())) {
            teamInfoForUpdate.setManagedNumberCount(teamInfoFromQuery.getManagedNumberCount() + countChangeNum);
        } else if (teamEnum.getStateCode().equals(TeamEnum.UPDATE_JOINED_NUMBER_COUNT.getStateCode())) {
            teamInfoForUpdate.setJoinedNumberCount(teamInfoFromQuery.getJoinedNumberCount() + countChangeNum);
        } else if (teamEnum.getStateCode().equals(TeamEnum.UPDATE_FILE_COUNT.getStateCode())) {
            teamInfoForUpdate.setFileCount(teamInfoFromQuery.getFileCount() + countChangeNum);
        }
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
    @Transactional(rollbackFor = TeamException.class)
    public void deleteFileFromTeam(String tid, String fileId) {
        // 此处查询需要在fileInfoDao.deleteByFileId()的前面，从七牛云bucket删除资源需要使用查出的数据
        FileInfoDTO fileInfoDTO = fileInfoDao.queryByFileId(fileId);

        // 加redis分布式锁，避免检索唯一键tid（读操作）、写操作时出现S锁和X锁循环等待造成死锁
        // 分布式锁过期时间
        int timeout = 10 * 1000;
        String tidForLock = tid.concat("-deleteFileFromTeam");
        redisLockService.lock(tidForLock, String.valueOf(timeout));

        teamFileDao.deleteByFileId(fileId);
        // 修改项目组文件计数
        this.updateTeamInfoCountProperty(tid, TeamEnum.UPDATE_FILE_COUNT, -1);

        // 解锁
        redisLockService.unlock(tidForLock, String.valueOf(timeout));

        // 不可调换顺序，否则会造成teamFileDao.deleteListByFileId()时tid外键报错
        fileInfoDao.deleteByFileId(fileId);

        // 从七牛云bucket删除资源
        fileService.deleteFromQiniuBucket(fileInfoDTO.getFileHash(), fileInfoDTO.getFileKey());
    }

    /**
     * 删除项目组内的文件
     *
     * @param tid 项目组tid
     * @param fileInfoList 文件信息List
     * @return
     */
    @Override
    @Transactional(rollbackFor = TeamException.class)
    public void deleteFileListFromTeam(String tid, List<FileInfo> fileInfoList) {
        // 此处查询需要在fileInfoDao.deleteByFileId()的前面，从七牛云bucket删除资源需要使用查出的数据
        List<FileInfo> fileInfoListFromQuery = fileInfoDao.queryListByFileId(fileInfoList);

        // 加redis分布式锁，避免检索唯一键tid（读操作）、写操作时出现S锁和X锁循环等待造成死锁
        // 分布式锁过期时间
        int timeout = 20 * 1000;
        String tidForLock = tid.concat("-deleteFileListFromTeam");
        redisLockService.lock(tidForLock, String.valueOf(timeout));

        teamFileDao.deleteListByFileId(fileInfoList);
        // 修改项目组文件计数
        this.updateTeamInfoCountProperty(tid, TeamEnum.UPDATE_FILE_COUNT, -fileInfoList.size());

        // 解锁
        redisLockService.unlock(tidForLock, String.valueOf(timeout));

        // 不可调换顺序，否则会造成teamFileDao.deleteListByFileId()时tid外键报错
        fileInfoDao.deleteListByFileId(fileInfoList);

        // 从七牛云bucket删除资源
        for (int i = 0; i < fileInfoListFromQuery.size(); i++) {
            fileService.deleteFromQiniuBucket(fileInfoListFromQuery.get(i).getFileHash(), fileInfoListFromQuery.get(i).getFileKey());
        }
    }
}
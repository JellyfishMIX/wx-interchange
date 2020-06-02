package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dao.*;
import com.jellyfishmix.wxinterchange.dto.*;
import com.jellyfishmix.wxinterchange.entity.*;
import com.jellyfishmix.wxinterchange.enums.TeamEnum;
import com.jellyfishmix.wxinterchange.exception.TeamException;
import com.jellyfishmix.wxinterchange.service.FileService;
import com.jellyfishmix.wxinterchange.service.RedisService;
import com.jellyfishmix.wxinterchange.service.TeamService;
import com.jellyfishmix.wxinterchange.service.UserService;
import com.jellyfishmix.wxinterchange.utils.PageCalculatorUtil;
import com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
    @Resource
    private CollectionFileDao collectionFileDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Autowired
    private RedisService redisService;

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
     * @param pageIndex 页码，从1开始
     * @param pageSize 每页容量
     * @return
     */
    @Override
    public List<TeamUserDTO> queryTeamUserListByTid(String tid, int pageIndex, int pageSize) {
        int rowIndex = PageCalculatorUtil.calculatorRowIndex(pageIndex, pageSize);
        List<TeamUserDTO> teamUserList = teamUserDao.queryTeamUserListByTid(tid, rowIndex, pageSize);
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
     * 查询单个项目组成员等级
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    @Override
    public TeamUser queryOneTeamUser(String tid, String uid) {
        TeamUser teamUser = teamUserDao.queryTeamUserByTidAndUid(tid, uid);
        return teamUser;
    }

    /**
     * 通过关键词搜索项目组内的文件
     *
     * @param tidList   项目组tidList
     * @param keyword   关键词
     * @param pageIndex 页码，从1开始
     * @param pageSize  每页行数
     * @return
     */
    @Override
    public List<TeamFileDTO> searchTeamFileListByKeyword(List<String> tidList, String keyword, int pageIndex, int pageSize) {
        int rowIndex = PageCalculatorUtil.calculatorRowIndex(pageIndex, pageSize);
        return teamFileDao.queryTeamFileListByKeyword(tidList, keyword, rowIndex, pageSize);
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
        UserInfo userInfoWithChange = new UserInfo();
        userInfoWithChange.setUid(teamUser.getUid());
        userInfoWithChange.setCreatedTeamCount(1);
        userService.updateUserInfoWithQuery(userInfoWithChange);

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
    public void uploadFileListToTeam(String tid, String uid, List<FileInfo> fileInfoList) {
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

        // 加redis分布式锁，避免检索唯一键tid在读操作、写操作时出现S锁和X锁循环等待造成死锁
        String identifierForLock = tid.concat("-uploadFileListToTeam");
        int timeout = 20 * 1000;
        long expireTime = redisService.lockConvenient(identifierForLock, timeout);

        this.teamFileDao.insertList(teamFileList);
        // 修改项目组文件计数
        TeamInfo teamInfoWithChange = new TeamInfo();
        teamInfoWithChange.setTid(tid);
        teamInfoWithChange.setFileCount(fileInfoList.size());
        teamInfoWithChange.setLastFileUploadTime(new Date());
        this.updateTeamInfoWithQuery(teamInfoWithChange);

        // 分布式锁解锁
        redisService.unlock(identifierForLock, String.valueOf(expireTime));
    }

    /**
     * 将fileId文件添加至项目组
     *
     * @param tid          项目组tid
     * @param uid          操作者uid
     * @param fileInfoList 列表
     */
    @Override
    public void addFileListToTeamByFileIdList(String tid, String uid, List<FileInfo> fileInfoList) {
        List<FileInfo> fileInfoListFromQuery = fileInfoDao.queryListByFileId(fileInfoList);
        this.uploadFileListToTeam(tid, uid, fileInfoListFromQuery);
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
        TeamInfo teamInfoWithChange = new TeamInfo();
        teamInfoWithChange.setTid(tid);
        teamInfoWithChange.setNumberCount(1);
        teamInfoWithChange.setJoinedNumberCount(1);
        this.updateTeamInfoWithQuery(teamInfoWithChange);

        // teamUser表中添加记录
        TeamInfo teamInfoFromQuery = teamInfoDao.queryByTid(tid);
        TeamUser teamUser = new TeamUser();
        teamUser.setTid(teamInfoFromQuery.getTid());
        teamUser.setUid(uid);
        // userGrade，3 为普通成员等级
        teamUser.setUserGrade(3);
        teamUserDao.insert(teamUser);

        // 修改uid用户加入的项目组数量
        UserInfo userInfoWithChange = new UserInfo();
        userInfoWithChange.setUid(uid);
        userInfoWithChange.setJoinedTeamCount(1);
        userService.updateUserInfoWithQuery(userInfoWithChange);
        return new TeamInfoDTO(TeamEnum.SUCCESS, teamInfoFromQuery);
    }

    /**
     * 修改teamInfo的属性
     * 此方法不会先通过tid查询teamInfo
     * 如何判断使用哪个teamService.updateTeamInfo() ?
     * - 如果更新的属性可以直接覆盖已有属性，则可以使用updateTeamInfoWithoutQuery()
     * - 如果更新的数据需要在已有teamInfo的属性上做更新，则可以使用updateTeamInfoWithQuery()
     *
     * @param teamInfo 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = TeamException.class)
    public TeamInfo updateTeamInfoWithoutQuery(TeamInfo teamInfo) {
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
     * 工具服务方法，修改teamInfo的属性
     * 此方法会先查询一次tid对应的teamInfo对象，根据teamInfoWithChange，在tid查询出来的teamInfo对象基础上做更新
     * 如何判断使用哪个teamService.updateTeamInfo() ?
     * - 如果更新的属性可以直接覆盖已有属性，则可以使用updateTeamInfoWithoutQuery()
     * - 如果更新的数据需要在已有teamInfo的属性上做更新，则可以使用updateTeamInfoWithQuery()
     *
     * @param teamInfoWithChange 记录teamInfo发生的变更的对象
     */
    @Override
    public void updateTeamInfoWithQuery(TeamInfo teamInfoWithChange) {
        TeamInfo teamInfoFromQuery = teamInfoDao.queryByTid(teamInfoWithChange.getTid());
        TeamInfo teamInfoForUpdate = new TeamInfo();
        teamInfoForUpdate.setTid(teamInfoWithChange.getTid());
        if (teamInfoWithChange.getNumberCount() != null) {
            teamInfoForUpdate.setNumberCount(teamInfoFromQuery.getNumberCount() + teamInfoWithChange.getNumberCount());
        }
        if (teamInfoWithChange.getManagedNumberCount() != null) {
            teamInfoForUpdate.setManagedNumberCount(teamInfoFromQuery.getManagedNumberCount() + teamInfoWithChange.getManagedNumberCount());
        }
        if (teamInfoWithChange.getJoinedNumberCount() != null) {
            teamInfoForUpdate.setJoinedNumberCount(teamInfoFromQuery.getJoinedNumberCount() + teamInfoWithChange.getJoinedNumberCount());
        }
        if (teamInfoWithChange.getFileCount() != null) {
            teamInfoForUpdate.setFileCount(teamInfoFromQuery.getFileCount() + teamInfoWithChange.getFileCount());
        }
        if (teamInfoWithChange.getLastFileUploadTime() != null) {
            teamInfoForUpdate.setLastFileUploadTime(teamInfoWithChange.getLastFileUploadTime());
        }
        teamInfoDao.updateByTid(teamInfoForUpdate);
    }

    /**
     * 修改tid为官方项目组
     *
     * @param tid 项目组tid
     */
    @Override
    @Transactional(rollbackFor = TeamException.class)
    public void changeToOfficialTeam(String tid) {
        // 查询全体用户的uid
        List<String> uidList = userInfoDao.queryAllUid();
        // 查询已经存在项目组中的用户uid
        List<String> existUid = teamUserDao.queryTeamAllUid(tid);
        for (String uid : uidList) {
            if (!existUid.contains(uid)) {
                this.joinTeam(tid, uid);
            }
        }
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTid(tid);
        // 项目组等级，官方项目组为1，普通项目组为2，保留0
        teamInfo.setGrade(1);
        teamInfoDao.updateByTid(teamInfo);
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

        // 加redis分布式锁，避免检索唯一键tid在读操作、写操作时出现S锁和X锁循环等待造成死锁
        // 分布式锁过期时间
        String identifierForLock = tid.concat("-deleteFileFromTeam");
        int timeout = 10 * 1000;
        long expireTime = redisService.lockConvenient(identifierForLock, timeout);

        teamFileDao.deleteByFileId(fileId);
        // 修改项目组文件计数
        TeamInfo teamInfoWithChange = new TeamInfo();
        teamInfoWithChange.setTid(tid);
        teamInfoWithChange.setFileCount(-1);
        this.updateTeamInfoWithQuery(teamInfoWithChange);

        // 分布式锁解锁
        redisService.unlock(identifierForLock, String.valueOf(expireTime));

        // 收藏集中将被删除的fileId替换成404，对应404文件
        collectionFileDao.updateFileIdTo404(fileId);

        // 不可调换顺序，否则会造成teamFileDao.deleteListByFileId()时tid外键报错
        fileInfoDao.deleteByFileId(fileId);

        // 从七牛云bucket删除资源
        fileService.deleteFromQiniuBucket(fileInfoDTO.getFileHash(), fileInfoDTO.getFileKey());
    }

    /**
     * 删除项目组内的文件（多个）
     *
     * @param tid 项目组tid
     * @param fileInfoList 文件信息list
     * @return
     */
    @Override
    @Transactional(rollbackFor = TeamException.class)
    public void deleteFileListFromTeam(String tid, List<FileInfo> fileInfoList) {
        // 此处查询需要在fileInfoDao.deleteByFileId()的前面，从七牛云bucket删除资源需要使用查出的数据
        List<FileInfo> fileInfoListFromQuery = fileInfoDao.queryListByFileId(fileInfoList);

        // 加redis分布式锁，避免检索唯一键tid在读操作、写操作时出现S锁和X锁循环等待造成死锁
        // 分布式锁过期时间
        String identifierForLock = tid.concat("-deleteFileListFromTeam");
        int timeout = 20 * 1000;
        long expireTime = redisService.lockConvenient(identifierForLock, timeout);

        teamFileDao.deleteListByFileId(fileInfoList);
        // 修改项目组文件计数
        TeamInfo teamInfoWithChange = new TeamInfo();
        teamInfoWithChange.setTid(tid);
        teamInfoWithChange.setFileCount(-fileInfoList.size());
        this.updateTeamInfoWithQuery(teamInfoWithChange);

        // 分布式锁解锁
        redisService.unlock(identifierForLock, String.valueOf(expireTime));

        // 收藏集中将被删除的fileId替换成404，对应404文件
        for (int i = 0; i < fileInfoListFromQuery.size(); i++) {
            String fileId = fileInfoListFromQuery.get(i).getFileId();
            collectionFileDao.updateFileIdTo404(fileId);
        }

        // 不可调换顺序，否则会造成teamFileDao.deleteListByFileId()时tid外键报错
        fileInfoDao.deleteListByFileIdOfFileInfo(fileInfoList);

        // 从七牛云bucket删除资源
        for (int i = 0; i < fileInfoListFromQuery.size(); i++) {
            fileService.deleteFromQiniuBucket(fileInfoListFromQuery.get(i).getFileHash(), fileInfoListFromQuery.get(i).getFileKey());
        }
    }

    /**
     * 删除项目组成员
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return 包含stateCode和stateMsg的TeamDTO
     */
    @Override
    @Transactional(rollbackFor = TeamException.class)
    public TeamDTO deleteTeamUser(String tid, String uid) {
        TeamUser teamUserFromQuery = teamUserDao.queryTeamUserByTidAndUid(tid, uid);

        // 加redis分布式锁，避免检索唯一键tid在读操作、写操作时出现S锁和X锁循环等待造成死锁
        // 分布式锁过期时间
        String identifierForLock = tid.concat("-deleteTeamUser");
        int timeout = 10 * 1000;
        long expireTime = redisService.lockConvenient(identifierForLock, timeout);

        TeamInfo teamInfoWithChange = new TeamInfo();
        teamInfoWithChange.setTid(tid);
        UserInfo userInfoWithChange = new UserInfo();
        userInfoWithChange.setUid(uid);
        if (teamUserFromQuery.getUserGrade().equals(TeamEnum.CREATOR.getStateCode())) {
            return new TeamDTO(TeamEnum.CREATED_NUMBER_DELETED_FAIL);
        } else if (teamUserFromQuery.getUserGrade().equals(TeamEnum.MANAGER.getStateCode())) {
            teamInfoWithChange.setManagedNumberCount(-1);
            userInfoWithChange.setManagedTeamCount(-1);
        } else if (teamUserFromQuery.getUserGrade().equals(TeamEnum.JOINER.getStateCode())) {
            teamInfoWithChange.setJoinedNumberCount(-1);
            userInfoWithChange.setJoinedTeamCount(-1);
        }
        teamInfoWithChange.setNumberCount(-1);
        this.updateTeamInfoWithQuery(teamInfoWithChange);
        userService.updateUserInfoWithQuery(userInfoWithChange);

        teamUserDao.delete(tid, uid);

        // 分布式锁解锁
        redisService.unlock(identifierForLock, String.valueOf(expireTime));

        return new TeamDTO(TeamEnum.SUCCESS);
    }

    /**
     * 解散项目组
     *
     * @param uid 用户uid
     * @param tid 项目组tid
     * @return 包含stateCode和stateMsg的TeamDTO
     */
    @Override
    @Transactional(rollbackFor = TeamException.class)
    public TeamDTO disbandGroup(String uid, String tid) {
        // 校验uid是否为tid的creator
        TeamUser teamUser = teamUserDao.queryTeamUserByTidAndUid(tid, uid);
        if (!teamUser.getUserGrade().equals(TeamEnum.CREATOR.getStateCode())) {
            return new TeamDTO(TeamEnum.PERMISSION_DENIED);
        }

        // 加redis分布式锁，避免检索唯一键tid, uid 在读操作、写操作时出现S锁和X锁循环等待造成死锁
        // 分布式锁过期时间
        String identifierForLock = tid.concat("-disbandGroup");
        int timeout = 100 * 1000;
        long expireTime = redisService.lockConvenient(identifierForLock, timeout);

        // 修改user_info中的count
        List<TeamUserDTO> teamUserDTOList = teamUserDao.queryAllTeamUserListByTid(tid);
        for (TeamUserDTO teamUserDTO : teamUserDTOList) {
            UserInfo userInfoWithChange = new UserInfo();
            userInfoWithChange.setUid(teamUserDTO.getUid());
            if (teamUserDTO.getUserGrade().equals(TeamEnum.CREATOR.getStateCode())) {
                userInfoWithChange.setCreatedTeamCount(-1);
            } else if (teamUserDTO.getUserGrade().equals(TeamEnum.MANAGER.getStateCode())) {
                userInfoWithChange.setManagedTeamCount(-1);
            } else if (teamUserDTO.getUserGrade().equals(TeamEnum.JOINER.getStateCode())) {
                userInfoWithChange.setJoinedTeamCount(-1);
            }
            userService.updateUserInfoWithQuery(userInfoWithChange);
        }

        // 删除全部的team_user
        teamUserDao.deleteAllByTid(tid);
        // 查询全部的team_file
        List<TeamFileDTO> teamFileDTOList = teamFileDao.queryAllByTid(tid);
        // 删除全部的team_file
        teamFileDao.deleteAllByTid(tid);
        // 删除team_info
        teamInfoDao.deleteByTid(tid);

        // 分布式锁解锁
        redisService.unlock(identifierForLock, String.valueOf(expireTime));

        for (int i = 0; i < teamFileDTOList.size(); i++) {
            // 收藏集中将被删除的fileId替换成404，对应404文件
            String fileId = teamFileDTOList.get(i).getFileId();
            collectionFileDao.updateFileIdTo404(fileId);

            // 从七牛云bucket删除资源
            fileService.deleteFromQiniuBucket(teamFileDTOList.get(i).getFileHash(), teamFileDTOList.get(i).getFileKey());
        }

        // 删除相关的file_info
        // 不可调换顺序，否则会造成collectionFileDao.updateFileIdTo404(fileId)报错
        if (teamFileDTOList.size() > 0) {
            fileInfoDao.deleteListByFileIdOfTeamFile(teamFileDTOList);
        }

        return new TeamDTO(TeamEnum.SUCCESS);
    }
}
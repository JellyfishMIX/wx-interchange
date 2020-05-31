package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dao.TeamInfoDao;
import com.jellyfishmix.wxinterchange.dao.TeamUserDao;
import com.jellyfishmix.wxinterchange.dto.TeamUserDTO;
import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.entity.CollectionInfo;
import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.enums.UserEnum;
import com.jellyfishmix.wxinterchange.exception.UserException;
import com.jellyfishmix.wxinterchange.dao.UserInfoDao;
import com.jellyfishmix.wxinterchange.service.CollectionService;
import com.jellyfishmix.wxinterchange.service.TeamService;
import com.jellyfishmix.wxinterchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 11:20 上午
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private TeamUserDao teamUserDao;
    @Resource
    private TeamInfoDao teamInfoDao;
    @Autowired
    private TeamService teamService;
    @Autowired
    private CollectionService collectionService;

    /**
     * 通过uid查找用户信息
     *
     * @param uid
     * @return
     */
    @Override
    public UserInfoDTO queryByUid(String uid) {
        UserInfoDTO userInfoDTO = null;
        UserInfo userInfo = userInfoDao.queryByUid(uid);
        // 查询userInfo为null
        if (userInfo == null) {
            userInfoDTO = new UserInfoDTO(UserEnum.USER_INFO_NULL);
            return userInfoDTO;
        }
        userInfoDTO = new UserInfoDTO(UserEnum.SUCCESS, userInfo);
        return userInfoDTO;
    }

    /**
     * 通过openid查找用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public UserInfoDTO queryByOpenid(String openid) {
        UserInfoDTO userInfoDTO = null;
        UserInfo userInfo = userInfoDao.queryByOpenid(openid);
        if (userInfo == null) {
            userInfoDTO = new UserInfoDTO(UserEnum.USER_INFO_NULL);
            return userInfoDTO;
        }
        userInfoDTO = new UserInfoDTO(UserEnum.SUCCESS, userInfo);
        return userInfoDTO;
    }

    /**
     * 通过uid和userGrade查询我所在的项目组（非官方组）
     *
     * @param uid 用户uid
     * @param userGrade 项目组成员等级
     * @return 对象列表
     */
    @Override
    public List<TeamUserDTO> queryTeamListByUidAndUserGrade(String uid, Integer userGrade) {
        List<TeamUserDTO> teamUserList = teamUserDao.queryTeamListByUidAndUserGrade(uid, userGrade);
        return teamUserList;
    }

    /**
     * 通过uid查询我所在的项目组（官方项目组）
     *
     * @param uid 用户uid
     * @return
     */
    @Override
    public List<TeamUserDTO> queryOfficialTeamListByUid(String uid) {
        List<TeamUserDTO> teamUserDTOList = teamUserDao.queryOfficialTeamListByUid(uid);
        return teamUserDTOList;
    }

    /**
     * 新增用户信息
     *
     * @param userInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = UserException.class)
    public UserInfoDTO insertUserInfo(UserInfo userInfo) {
        String uid = userInfo.getUid();
        int effectedNum = userInfoDao.insertUserInfo(userInfo);
        if (effectedNum <= 0) {
            throw new UserException(UserEnum.INSERT_USER_INFO_ERROR);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO(UserEnum.SUCCESS);

        // 每个新用户默认拥有一个默认收藏集
        CollectionInfo collectionInfo = new CollectionInfo();
        collectionInfo.setUid(uid);
        collectionInfo.setCollectionName("默认收藏集");
        collectionService.createCollection(collectionInfo);

        // 每个新用户默认加入所有官方项目组
        List<TeamInfo> officialTeamInfoList = teamInfoDao.queryOfficialTeamList();
        for (TeamInfo officialTeamInfo : officialTeamInfoList) {
            teamService.joinTeam(officialTeamInfo.getTid(), uid);
        }
        return userInfoDTO;
    }

    /**
     * 修改userInfo信息
     * 此方法不会先通过uid查询userInfo
     * 如何判断使用哪个userService.updateUserInfo() ?
     * - 如果更新的属性可以直接覆盖已有属性，则可以使用updateUserInfoWithoutQuery()
     * - 如果更新的数据需要在已有userInfo的属性上做更新，则可以使用updateUserInfoWithQuery()
     *
     * @param userInfo 用户信息实例对象
     * @return
     */
    @Override
    public UserInfo updateUserInfoWithoutQuery(UserInfo userInfo) {
        int effectedNum = userInfoDao.updateUserInfoByUid(userInfo);
        if (effectedNum <= 0) {
            throw new UserException(UserEnum.INSERT_USER_INFO_ERROR);
        }
        UserInfo userInfoFromQuery = userInfoDao.queryByUid(userInfo.getUid());
        return userInfoFromQuery;
    }

    /**
     * 工具服务方法，修改userInfo的属性
     * 此方法会先查询一次uid对应的userInfo对象，根据userInfoWithChange，在uid查询出来的userInfo对象基础上做更新
     * 如何判断使用哪个userService.updateUserInfo() ?
     * - 如果更新的属性可以直接覆盖已有属性，则可以使用updateUserInfoWithoutQuery()
     * - 如果更新的数据需要在已有teamInfo的属性上做更新，则可以使用updateUserInfoWithQuery()
     *
     * @param userInfoWithChange 记录userInfo发生的变更的对象
     */
    @Override
    public void updateUserInfoWithQuery(UserInfo userInfoWithChange) {
        UserInfo userInfoFromQuery = userInfoDao.queryByUid(userInfoWithChange.getUid());
        UserInfo userInfoForUpdate = new UserInfo();
        userInfoForUpdate.setUid(userInfoWithChange.getUid());
        if (userInfoWithChange.getCreatedTeamCount() != null) {
            userInfoForUpdate.setCreatedTeamCount(userInfoFromQuery.getCreatedTeamCount() + userInfoWithChange.getCreatedTeamCount());
        }
        if (userInfoWithChange.getManagedTeamCount() != null) {
            userInfoForUpdate.setManagedTeamCount(userInfoFromQuery.getManagedTeamCount() + userInfoWithChange.getManagedTeamCount());
        }
        if (userInfoWithChange.getJoinedTeamCount() != null) {
            userInfoForUpdate.setJoinedTeamCount(userInfoFromQuery.getJoinedTeamCount() + userInfoWithChange.getJoinedTeamCount());
        }
        userInfoDao.updateUserInfoByUid(userInfoForUpdate);
    }
}
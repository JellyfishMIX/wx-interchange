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
     * 修改用户信息
     *
     * @param userInfo
     * @return
     */
    @Override
    public UserInfoDTO updateUserInfo(UserInfo userInfo) {
        int effectedNum = userInfoDao.updateUserInfoByUid(userInfo);
        if (effectedNum <= 0) {
            throw new UserException(UserEnum.INSERT_USER_INFO_ERROR);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO(UserEnum.SUCCESS);
        return userInfoDTO;
    }

    /**
     * 更新用户的计数属性
     *
     * @param uid            用户uid
     * @param userEnum       操作标志Enum
     * @param countChangeNum 计数更改的数量，有正负
     */
    @Override
    public void updateUserInfoCountProperty(String uid, UserEnum userEnum, Integer countChangeNum) {
        UserInfo userInfoFromQuery = userInfoDao.queryByUid(uid);
        UserInfo userInfoForUpdate = new UserInfo();
        userInfoForUpdate.setUid(uid);
        if (userEnum.getStateCode().equals(UserEnum.UPDATE_CREATED_TEAM_COUNT.getStateCode())) {
            userInfoForUpdate.setCreatedTeamCount(userInfoFromQuery.getCreatedTeamCount() + countChangeNum);
        } else if (userEnum.getStateCode().equals(UserEnum.UPDATE_MANAGED_TEAM_COUNT.getStateCode())) {
            userInfoForUpdate.setManagedTeamCount(userInfoFromQuery.getManagedTeamCount() + countChangeNum);
        } else if (userEnum.getStateCode().equals(UserEnum.UPDATE_JOINED_TEAM_COUNT.getStateCode())) {
            userInfoForUpdate.setJoinedTeamCount(userInfoFromQuery.getJoinedTeamCount() + countChangeNum);
        }
        userInfoDao.updateUserInfoByUid(userInfoForUpdate);
    }

    // delete暂时不做
}
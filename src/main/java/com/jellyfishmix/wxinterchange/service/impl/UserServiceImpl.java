package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.enums.UserEnum;
import com.jellyfishmix.wxinterchange.exception.UserException;
import com.jellyfishmix.wxinterchange.dao.UserInfoDao;
import com.jellyfishmix.wxinterchange.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 11:20 上午
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 新增用户信息
     *
     * @param userInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = UserException.class)
    public UserInfoDTO insertUserInfo(UserInfo userInfo) {
        int effectedNum = userInfoDao.insertUserInfo(userInfo);
        if (effectedNum <= 0) {
            throw new UserException(UserEnum.INSERT_USER_INFO_ERROR);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO(UserEnum.SUCCESS);
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
        int effectedNum = userInfoDao.updateUserInfo(userInfo);
        if (effectedNum <= 0) {
            throw new UserException(UserEnum.INSERT_USER_INFO_ERROR);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO(UserEnum.SUCCESS);
        return userInfoDTO;
    }

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

    // delete暂时不做
}
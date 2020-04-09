package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.enums.UserEnum;
import com.jellyfishmix.wxinterchange.exception.UserException;
import com.jellyfishmix.wxinterchange.mapper.UserInfoMapper;
import com.jellyfishmix.wxinterchange.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 11:20 上午
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 新增用户信息
     * @param userInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = UserException.class)
    public UserInfoDTO insertUserInfo(UserInfo userInfo) {
        int effectedNum = userInfoMapper.insertUserInfo(userInfo);
        if (effectedNum <= 0) {
            throw new UserException(UserEnum.INSERT_USER_INFO_ERROR);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO(UserEnum.SUCCESS);
        return userInfoDTO;
    }

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    @Override
    public UserInfoDTO updateUserInfo(UserInfo userInfo) {
        int effectedNum = userInfoMapper.updateUserInfo(userInfo);
        if (effectedNum <= 0) {
            throw new UserException(UserEnum.INSERT_USER_INFO_ERROR);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO(UserEnum.SUCCESS);
        return userInfoDTO;
    }

    /**
     * 通过uid查找用户信息
     * @param uid
     * @return
     */
    @Override
    public UserInfoDTO selectUserInfoByUid(String uid) {
        UserInfoDTO userInfoDTO = null;
        UserInfo userInfo = userInfoMapper.selectUserInfoByUid(uid);
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
    public UserInfoDTO selectUserInfoByOpenid(String openid) {
        UserInfoDTO userInfoDTO = null;
        UserInfo userInfo = userInfoMapper.selectUserInfoByOpenid(openid);
        if (userInfo == null) {
            userInfoDTO = new UserInfoDTO(UserEnum.USER_INFO_NULL);
            return userInfoDTO;
        }
        userInfoDTO = new UserInfoDTO(UserEnum.SUCCESS, userInfo);
        return userInfoDTO;
    }

    // delete暂时不做
}

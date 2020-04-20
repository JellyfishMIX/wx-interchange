package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.entity.UserInfo;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 11:18 上午
 */
public interface UserService {
    /**
     * 新增用户信息
     * @param userInfo
     * @return
     */
    UserInfoDTO insertUserInfo(UserInfo userInfo);

    /**
     * 修改用户信息
     * @param userInfo 用户信息实例对象
     * @return
     */
    UserInfoDTO updateUserInfo(UserInfo userInfo);

    /**
     * 通过uid查找用户信息
     * @param uid
     * @return
     */
    UserInfoDTO queryByUid(String uid);

    /**
     * 通过openid查找用户信息
     * @param openid
     * @return
     */
    UserInfoDTO queryByOpenid(String openid);

    // delete暂时不做
}

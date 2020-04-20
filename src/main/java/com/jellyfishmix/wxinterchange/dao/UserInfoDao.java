package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.UserInfo;

/**
 * @author JellyfishMIX
 * @date 2020/4/7 6:58 下午
 */
public interface UserInfoDao {
    /**
     * 新增用户信息
     *
     * @param userInfo 用户信息
     * @return
     */
    int insertUserInfo(UserInfo userInfo);

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return
     */
    int updateUserInfo(UserInfo userInfo);

    /**
     * 通过uid查找用户信息
     *
     * @param uid 用户uid
     * @return
     */
    UserInfo queryByUid(String uid);

    /**
     * 通过openid查找用户信息
     *
     * @param openid 用户openid
     * @return
     */
    UserInfo queryByOpenid(String openid);

    // delete暂时不做
}

package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.TeamUserDTO;
import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.enums.UserEnum;

import java.util.List;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 11:18 上午
 */
public interface UserService {
    /**
     * 通过uid查找用户信息
     * @param uid 用户uid
     * @return
     */
    UserInfoDTO queryByUid(String uid);

    /**
     * 通过openid查找用户信息
     * @param openid 用户openid
     * @return
     */
    UserInfoDTO queryByOpenid(String openid);

    /**
     * 通过uid和userGrade查询我所在的项目组（非官方组）
     *
     * @param uid 用户uid
     * @param userGrade 项目组成员等级
     * @return 对象列表
     */
    List<TeamUserDTO> queryTeamListByUidAndUserGrade(String uid, Integer userGrade);

    /**
     * 新增用户信息
     *
     * @param userInfo 用户信息
     * @return
     */
    UserInfoDTO insertUserInfo(UserInfo userInfo);

    /**
     * 修改用户信息
     * 
     * @param userInfo 用户信息实例对象
     * @return
     */
    UserInfoDTO updateUserInfo(UserInfo userInfo);

    /**
     * 更新用户的计数属性
     *
     * @param uid 用户uid
     * @param userEnum 操作标志Enum
     * @param countChangeNum 计数更改的数量，有正负
     */
    void updateUserInfoCountProperty(String uid, UserEnum userEnum, Integer countChangeNum);

    // delete暂时不做
}

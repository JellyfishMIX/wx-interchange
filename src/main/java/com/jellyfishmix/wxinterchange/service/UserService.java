package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.TeamUserDTO;
import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.entity.UserInfo;

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
     * 通过uid查询我所在的项目组（官方项目组）
     *
     * @param uid 用户uid
     * @return
     */
    List<TeamUserDTO> queryOfficialTeamListByUid(String uid);

    /**
     * 新增用户信息
     *
     * @param userInfo 用户信息
     * @return
     */
    UserInfoDTO insertUserInfo(UserInfo userInfo);

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
    UserInfo updateUserInfoWithoutQuery(UserInfo userInfo);

    /**
     * 工具服务方法，修改userInfo的属性
     * 此方法会先查询一次uid对应的userInfo对象，根据userInfoWithChange，在uid查询出来的userInfo对象基础上做更新
     * 如何判断使用哪个userService.updateUserInfo() ?
     * - 如果更新的属性可以直接覆盖已有属性，则可以使用updateUserInfoWithoutQuery()
     * - 如果更新的数据需要在已有teamInfo的属性上做更新，则可以使用updateUserInfoWithQuery()
     *
     * @param userInfoWithChange 记录userInfo发生的变更的对象
     */
    void updateUserInfoWithQuery(UserInfo userInfoWithChange);
}

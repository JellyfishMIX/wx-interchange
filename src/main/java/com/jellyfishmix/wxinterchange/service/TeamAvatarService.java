package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.entity.TeamAvatar;

/**
 * 项目组头像表(TeamAvatar)表服务接口
 *
 * @author makejava
 * @since 2020-04-27 21:51:47
 */
public interface TeamAvatarService {
    /**
     * 通过ID查询单条数据
     *
     * @param avatarId 项目组头像文件avatarId
     * @return 实例对象
     */
    TeamAvatar queryByAvatarId(String avatarId);

    /**
     * 新增数据
     *
     * @param teamAvatar 实例对象
     * @return 实例对象
     */
    TeamAvatar insert(TeamAvatar teamAvatar);

    /**
     * 修改数据
     *
     * @param teamAvatar 实例对象
     * @return 实例对象
     */
    TeamAvatar update(TeamAvatar teamAvatar);

    /**
     * 通过主键删除数据
     *
     * @param avatarId 项目组头像文件avatarId
     */
    void deleteByAvatarId(String avatarId);
}
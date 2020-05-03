package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.TeamAvatar;

import java.util.List;

/**
 * 项目组头像表(TeamAvatar)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-27 21:51:47
 */
public interface TeamAvatarDao {
    /**
     * 通过avatarId查询单条数据
     *
     * @param avatarId avatarId
     * @return 实例对象
     */
    TeamAvatar queryByAvatarId(String avatarId);

    /**
     * 通过tid查询
     *
     * @param tid 项目组tid
     * @return
     */
    TeamAvatar queryByTid(String tid);

    /**
     * 通过fileHash查询项目组头像文件信息
     *
     * @param fileHash 全局唯一的文件Hash值
     * @return
     */
    List<TeamAvatar> queryByFileHash(String fileHash);

    /**
     * 新增数据
     *
     * @param teamAvatar 实例对象
     * @return 影响行数
     */
    int insert(TeamAvatar teamAvatar);

    /**
     * 通过avatarId删除
     *
     * @param avatarId 项目组头像avatarId
     * @return 影响行数
     */
    int deleteByAvatarId(String avatarId);
}
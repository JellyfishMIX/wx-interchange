package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.entity.TeamAvatar;
import com.jellyfishmix.wxinterchange.dao.TeamAvatarDao;
import com.jellyfishmix.wxinterchange.service.TeamAvatarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 项目组头像表(TeamAvatar)表服务实现类
 *
 * @author makejava
 * @since 2020-04-27 21:51:47
 */
@Service("teamAvatarService")
public class TeamAvatarServiceImpl implements TeamAvatarService {
    @Resource
    private TeamAvatarDao teamAvatarDao;

    // TODO 合并teamService和teamAvatarService

    /**
     * 通过avatarId查询单条数据
     *
     * @param avatarId 项目组头像文件avatarId
     * @return 实例对象
     */
    @Override
    public TeamAvatar queryByAvatarId(String avatarId) {
        return teamAvatarDao.queryByAvatarId(avatarId);
    }

    /**
     * 新增数据
     *
     * @param teamAvatar 实例对象
     * @return 实例对象
     */
    @Override
    public TeamAvatar insert(TeamAvatar teamAvatar) {
        teamAvatarDao.insert(teamAvatar);
        return teamAvatar;
    }

    /**
     * 修改数据
     *
     * @param teamAvatar 实例对象
     * @return 实例对象
     */
    @Override
    public TeamAvatar update(TeamAvatar teamAvatar) {
        teamAvatarDao.update(teamAvatar);
        return queryByAvatarId(teamAvatar.getAvatarId());
    }

    /**
     * 通过主键删除数据
     *
     * @param avatarId 项目组头像文件avatarId
     */
    @Override
    public void deleteByAvatarId(String avatarId) {
        teamAvatarDao.deleteByAvatarId(avatarId);
    }
}
package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dao.UserInfoDao;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
import com.jellyfishmix.wxinterchange.dao.TeamUserDao;
import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.service.TeamUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目组成员表(TeamUser)表服务实现类
 *
 * @author makejava
 * @since 2020-04-11 21:09:44
 */
@Service("teamUserService")
public class TeamUserServiceImpl implements TeamUserService {
    @Resource
    private TeamUserDao teamUserDao;
    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TeamUser queryById(Integer id) {
        return this.teamUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TeamUser> queryAllByLimit(int offset, int limit) {
        return this.teamUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过uid查询我创建的项目组
     *
     * @param uid 用户uid
     * @param userGrade 项目组成员等级
     * @return 对象列表
     */
    @Override
    public List<TeamUser> queryTeamListByUidAndUserGrade(String uid, Integer userGrade) {
        List<TeamUser> teamUserList = teamUserDao.queryTeamListByUidAndUserGrade(uid, userGrade);
        return teamUserList;
    }

    /**
     * 通过tid查询项目组成员列表
     *
     * @param tid 项目组tid
     * @return
     */
    @Override
    public List<TeamUser> queryTeamUserListByTid(String tid) {
        List<TeamUser> teamUserList = teamUserDao.queryTeamUserListByTid(tid);
        return teamUserList;
    }

    /**
     * 新增数据
     *
     * @param teamUser 实例对象
     * @return 实例对象
     */
    @Override
    public TeamUser insert(TeamUser teamUser) {
        // 暂时不加通过uid查询无userInfo的错误校验
        UserInfo userInfo = userInfoDao.queryByUid(teamUser.getUid());
        teamUser.setUsername(userInfo.getUsername());
        teamUser.setUserAvatarUrl(userInfo.getAvatarUrl());
        this.teamUserDao.insert(teamUser);
        return teamUser;
    }

    /**
     * 修改数据
     *
     * @param teamUser 实例对象
     * @return 实例对象
     */
    @Override
    public TeamUser update(TeamUser teamUser) {
        this.teamUserDao.update(teamUser);
        return this.queryById(teamUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.teamUserDao.deleteById(id) > 0;
    }
}
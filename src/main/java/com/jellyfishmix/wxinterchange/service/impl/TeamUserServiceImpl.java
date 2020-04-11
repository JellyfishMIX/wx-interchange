package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.entity.TeamUser;
import com.jellyfishmix.wxinterchange.dao.TeamUserDao;
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
     * 新增数据
     *
     * @param teamUser 实例对象
     * @return 实例对象
     */
    @Override
    public TeamUser insert(TeamUser teamUser) {
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
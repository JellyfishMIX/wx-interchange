package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.dao.TeamInfoDao;
import com.jellyfishmix.wxinterchange.service.TeamInfoService;
import com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目组表(TeamInfo)表服务实现类
 *
 * @author makejava
 * @since 2020-04-11 09:52:53
 */
@Service("teamInfoService")
public class TeamInfoServiceImpl implements TeamInfoService {
    @Resource
    private TeamInfoDao teamInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    @Override
    public TeamInfo queryByTid(String tid) {
        return this.teamInfoDao.queryByTid(tid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TeamInfo> queryAllByLimit(int offset, int limit) {
        return this.teamInfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param teamInfo 实例对象
     * @return 实例对象
     */
    @Override
    public TeamInfo insert(TeamInfo teamInfo) {
        // 生成唯一tid
        String tid = UniqueKeyUtil.getUniqueKey();
        teamInfo.setTid(tid);
        this.teamInfoDao.insert(teamInfo);

        // 查询新insert的teamInfo信息
        teamInfo = teamInfoDao.queryByTid(tid);
        return teamInfo;
    }

    /**
     * 修改数据
     *
     * @param teamInfo 实例对象
     * @return 实例对象
     */
    @Override
    public TeamInfo update(TeamInfo teamInfo) {
        this.teamInfoDao.update(teamInfo);
        return this.queryByTid(teamInfo.getTid());
    }

    /**
     * 通过主键删除数据
     *
     * @param tid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteByTid(String tid) {
        return this.teamInfoDao.deleteByTid(tid) > 0;
    }
}
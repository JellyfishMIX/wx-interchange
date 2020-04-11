package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import java.util.List;

/**
 * 项目组表(TeamInfo)表服务接口
 *
 * @author makejava
 * @since 2020-04-11 09:52:53
 */
public interface TeamInfoService {

    /**
     * 通过tid查询单条数据
     *
     * @param tid 项目组tid
     * @return 实例对象
     */
    TeamInfo queryByTid(String tid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TeamInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param teamInfo 实例对象
     * @return 实例对象
     */
    TeamInfo insert(TeamInfo teamInfo);

    /**
     * 修改数据
     *
     * @param teamInfo 实例对象
     * @return 实例对象
     */
    TeamInfo update(TeamInfo teamInfo);

    /**
     * 通过主键删除数据
     *
     * @param tid 主键
     * @return 是否成功
     */
    boolean deleteByTid(String tid);

}
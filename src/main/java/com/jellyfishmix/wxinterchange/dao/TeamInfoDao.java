package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 项目组表(TeamInfo)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-11 09:52:53
 */
public interface TeamInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param tid 项目组tid
     * @return 实例对象
     */
    TeamInfo queryByTid(Integer tid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TeamInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param teamInfo 实例对象
     * @return 对象列表
     */
    List<TeamInfo> queryAll(TeamInfo teamInfo);

    /**
     * 新增数据
     *
     * @param teamInfo 实例对象
     * @return 影响行数
     */
    int insert(TeamInfo teamInfo);

    /**
     * 修改数据
     *
     * @param teamInfo 实例对象
     * @return 影响行数
     */
    int update(TeamInfo teamInfo);

    /**
     * 通过主键删除数据
     *
     * @param tid 主键
     * @return 影响行数
     */
    int deleteByTid(Integer tid);

}
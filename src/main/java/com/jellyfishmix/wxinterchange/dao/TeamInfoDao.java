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
     * 通过tid查询单条数据
     *
     * @param tid 项目组tid
     * @return 实例对象
     */
    TeamInfo queryByTid(String tid);

    /**
     * 获取所有官方项目组列表
     *
     * @return
     */
    List<TeamInfo> queryOfficialTeamList();

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
    int updateByTid(TeamInfo teamInfo);

    /**
     * 通过tid删除数据
     *
     * @param tid 主键
     * @return 影响行数
     */
    int deleteByTid(String tid);
}
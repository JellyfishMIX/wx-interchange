package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.TeamUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 项目组成员表(TeamUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-11 21:09:44
 */
public interface TeamUserDao {
    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TeamUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过uid和userGrade查询我所在的项目组（非官方组）
     *
     * @param uid 用户uid
     * @param userGrade 项目组成员等级
     * @return 对象列表
     */
    List<TeamUser> queryTeamListByUidAndUserGrade(String uid, Integer userGrade);

    /**
     * 通过tid查询项目组成员列表
     *
     * @param tid 项目组tid
     * @return
     */
    List<TeamUser> queryTeamUserListByTid(String tid);

    /**
     * 通过tid和uid查询单条teamUser
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    TeamUser queryTeamUserByTidAndUid(String tid, String uid);

    /**
     * 新增数据
     *
     * @param teamUser 实例对象
     * @return 影响行数
     */
    int insert(TeamUser teamUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);
}
package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.dto.TeamUserDTO;
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
     * 通过uid和userGrade查询我所在的项目组（非官方项目组）
     *
     * @param uid 用户uid
     * @param userGrade 项目组成员等级
     * @return 对象列表
     */
    List<TeamUserDTO> queryTeamListByUidAndUserGrade(@Param("uid") String uid, @Param("userGrade") Integer userGrade);

    /**
     * 通过tid查询项目组成员列表
     *
     * @param tid 项目组tid
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return
     */
    List<TeamUserDTO> queryTeamUserListByTid(@Param("tid") String tid, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过uid查询我所在的项目组（官方项目组）
     *
     * @param uid 用户uid
     * @return
     */
    List<TeamUserDTO> queryOfficialTeamListByUid(String uid);

    /**
     * 通过tid查询项目组成员列表（所有）
     *
     * @param tid 项目组tid
     * @return
     */
    List<TeamUserDTO> queryAllTeamUserListByTid(String tid);

    /**
     * 查询一个项目组中所有成员的uid
     *
     * @param tid 项目组tid
     * @return
     */
    List<String> queryTeamAllUid(String tid);

    /**
     * 通过tid和uid查询单条teamUser
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    TeamUser queryTeamUserByTidAndUid(@Param("tid") String tid, @Param("uid") String uid);

    /**
     * 新增数据
     *
     * @param teamUser 实例对象
     * @return 影响行数
     */
    int insert(TeamUser teamUser);

    /**
     * 删除数据
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    int delete(@Param("tid") String tid, @Param("uid") String uid);

    /**
     * 删除tid相关的所有行
     *
     * @param tid 项目组tid
     * @return
     */
    int deleteAllByTid(String tid);
}
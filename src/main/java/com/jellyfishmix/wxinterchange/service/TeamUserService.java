package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.TeamInfoDTO;
import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
import java.util.List;

/**
 * 项目组成员表(TeamUser)表服务接口
 *
 * @author makejava
 * @since 2020-04-11 21:09:44
 */
public interface TeamUserService {
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TeamUser> queryAllByLimit(int offset, int limit);

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
     */
    void insert(TeamUser teamUser);

    /**
     * 加入项目组
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    TeamInfoDTO joinTeam(String tid, String uid);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
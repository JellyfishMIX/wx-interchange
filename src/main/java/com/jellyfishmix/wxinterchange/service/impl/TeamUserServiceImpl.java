package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dao.TeamInfoDao;
import com.jellyfishmix.wxinterchange.dto.TeamInfoDTO;
import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
import com.jellyfishmix.wxinterchange.dao.TeamUserDao;
import com.jellyfishmix.wxinterchange.enums.TeamEnum;
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
    private TeamInfoDao teamInfoDao;

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
     * 通过uid和userGrade查询我所在的项目组（非官方组）
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
     * 通过uid和tid查询单条teamUser
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    @Override
    public TeamUser queryTeamUserByTidAndUid(String tid, String uid) {
        TeamUser teamUser = teamUserDao.queryTeamUserByTidAndUid(tid, uid);
        return teamUser;
    };

    /**
     * 新增数据
     *
     * @param teamUser 实例对象
     * @return 实例对象
     */
    @Override
    public void insert(TeamUser teamUser) {
        this.teamUserDao.insert(teamUser);
    }

    /**
     * 加入项目组
     *
     * @param uid 用户uid
     * @param tid 项目组tid
     * @return
     */
    @Override
    public TeamInfoDTO joinTeam(String tid, String uid) {
        TeamInfo teamInfoFromQuery = teamInfoDao.queryByTid(tid);
        // 新成员加入，项目组成员++
        teamInfoFromQuery.setNumberCount(teamInfoFromQuery.getNumberCount() + 1);
        teamInfoFromQuery.setJoinedNumberCount(teamInfoFromQuery.getJoinedNumberCount() + 1);

        TeamInfo teamInfoForUpdate = new TeamInfo();
        teamInfoForUpdate.setTid(tid);
        teamInfoForUpdate.setNumberCount(teamInfoFromQuery.getNumberCount());
        teamInfoForUpdate.setJoinedNumberCount(teamInfoFromQuery.getJoinedNumberCount());
        teamInfoDao.update(teamInfoForUpdate);

        // teamUser表中添加记录
        TeamUser teamUser = new TeamUser();
        teamUser.setTid(teamInfoFromQuery.getTid());
        teamUser.setUid(uid);
        // userGrade，3 为普通成员等级
        teamUser.setUserGrade(3);

        this.insert(teamUser);
        return new TeamInfoDTO(TeamEnum.SUCCESS, teamInfoFromQuery);
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
package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.entity.TeamFile;
import com.jellyfishmix.wxinterchange.dao.TeamFileDao;
import com.jellyfishmix.wxinterchange.service.TeamFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目组文件表(TeamFile)表服务实现类
 *
 * @author makejava
 * @since 2020-04-20 01:42:19
 */
@Service("teamFileService")
public class TeamFileServiceImpl implements TeamFileService {
    @Resource
    private TeamFileDao teamFileDao;

    /**
     * 通过ID查询单条数据
     *
     * @param tid 项目组tid
     * @param fileId 文件fileId
     * @return 实例对象
     */
    @Override
    public TeamFile queryByTidAndFileId(String tid, String fileId) {
        return this.teamFileDao.queryByTidAndFileId(tid, fileId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TeamFile> queryAllByLimit(int offset, int limit) {
        return this.teamFileDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param teamFile 实例对象
     * @return 实例对象
     */
    @Override
    public TeamFile insert(TeamFile teamFile) {
        this.teamFileDao.insert(teamFile);
        return teamFile;
    }

    /**
     * 修改数据
     *
     * @param teamFile 实例对象
     * @return 实例对象
     */
    @Override
    public TeamFile updateByTidAndFileId(TeamFile teamFile) {
        this.teamFileDao.updateByTidAndFileId(teamFile);
        return this.queryByTidAndFileId(teamFile.getTid(), teamFile.getFileId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.teamFileDao.deleteById(id) > 0;
    }
}
package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.entity.TeamFile;
import java.util.List;

/**
 * 项目组文件表(TeamFile)表服务接口
 *
 * @author makejava
 * @since 2020-04-20 01:42:19
 */
public interface TeamFileService {
    /**
     * 通过ID查询单条数据
     *
     * @param tid 项目组tid
     * @param fileId 文件fileId
     * @return 实例对象
     */
    TeamFile queryByTidAndFileId(String tid, String fileId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TeamFile> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param teamFile 实例对象
     * @return 实例对象
     */
    TeamFile insert(TeamFile teamFile);

    /**
     * 通过tid和fileId修改数据
     *
     * @param teamFile 实例对象
     * @return 实例对象
     */
    TeamFile updateByTidAndFileId(TeamFile teamFile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);
}
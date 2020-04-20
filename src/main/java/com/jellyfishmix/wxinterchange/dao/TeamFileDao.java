package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.TeamFile;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 项目组文件表(TeamFile)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-20 01:42:19
 */
public interface TeamFileDao {

    /**
     * 通过tid和fileId查询单条数据
     *
     * @param tid 项目组tid
     * @param fileId 文件fileId
     * @return 实例对象
     */
    TeamFile queryByTidAndFileId(@Param("tid") String tid, @Param("fileId") String fileId);

    /**
     * 查询项目组文件列表，通过上传日期排序
     *
     * @param tid 项目组tid
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return
     */
    List<TeamFile> queryTeamFileListOrderByCreationTime(@Param("tid") String tid, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TeamFile> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 新增数据
     *
     * @param teamFile 实例对象
     * @return 影响行数
     */
    int insert(TeamFile teamFile);

    /**
     * 通过tid和fileId修改数据
     *
     * @param teamFile 实例对象
     * @return 影响行数
     */
    int updateByTidAndFileId(TeamFile teamFile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);
}
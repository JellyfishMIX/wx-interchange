package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.dto.TeamFileDTO;
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
    TeamFileDTO queryByTidAndFileId(@Param("tid") String tid, @Param("fileId") String fileId);

    /**
     * 查询项目组文件列表，通过上传日期排序
     *
     * @param tid 项目组tid
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return
     */
    List<TeamFileDTO> queryTeamFileListOrderByCreationTime(@Param("tid") String tid, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过关键词搜索项目组内的文件
     *
     * @param tid 项目组tid
     * @param keyword 关键词
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return
     */
    List<TeamFileDTO> queryTeamFileListByKeyword(@Param("tid") String tid, @Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

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
     * 通过fileId删除数据
     *
     * @param fileId 文件fileId
     * @return
     */
    void deleteByFileId(String fileId);
}
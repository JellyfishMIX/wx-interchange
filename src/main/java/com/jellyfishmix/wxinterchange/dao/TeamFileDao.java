package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.dto.TeamFileDTO;
import com.jellyfishmix.wxinterchange.entity.FileInfo;
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
     * 通过tid查询所有的teamFile
     *
     * @param tid 项目组tid
     * @return
     */
    List<TeamFileDTO> queryAllByTid(String tid);

    /**
     * 通过关键词搜索项目组内的文件
     *
     * @param tidList 项目组tidList
     * @param keyword 关键词
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return
     */
    List<TeamFileDTO> queryTeamFileListByKeyword(@Param("tidList") List<String> tidList, @Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 新增数据（多个）
     *
     * @param teamFileList 实例对象列表
     * @return 影响行数
     */
    void insertList(List<TeamFile> teamFileList);

    /**
     * 通过fileId删除数据
     *
     * @param fileId 文件fileId
     * @return
     */
    void deleteByFileId(String fileId);

    /**
     * 通过fileId批量删除数据
     *
     * @param fileInfoList fileInfoList，使用里面的tid删除数据
     * @return
     */
    void deleteListByFileId(List<FileInfo> fileInfoList);

    /**
     * 删除tid相关的所有行
     *
     * @param tid 项目组tid
     */
    void deleteAllByTid(String tid);
}
package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.FileInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 文件信息表(FileInfo)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-19 23:26:14
 */
public interface FileInfoDao {

    /**
     * 通过fileId查询单条数据
     *
     * @param fileId 文件fileId
     * @return 实例对象
     */
    FileInfo queryByFileId(String fileId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<FileInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 新增数据
     *
     * @param fileInfo 实例对象
     * @return 影响行数
     */
    int insert(FileInfo fileInfo);

    /**
     * 修改数据
     *
     * @param fileInfo 实例对象
     * @return 影响行数
     */
    int update(FileInfo fileInfo);

    /**
     * 通过fileId删除数据
     *
     * @param fileId 文件fileId
     * @return 影响行数
     */
    int deleteByFileId(String fileId);

}
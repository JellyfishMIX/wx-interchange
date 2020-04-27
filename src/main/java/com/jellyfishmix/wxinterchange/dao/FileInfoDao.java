package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.dto.FileInfoDTO;

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
    FileInfoDTO queryByFileId(String fileId);

    /**
     * 新增数据（多个）
     *
     * @param fileInfoList 实例对象列表
     * @return 影响行数
     */
    int insertList(List<FileInfo> fileInfoList);

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
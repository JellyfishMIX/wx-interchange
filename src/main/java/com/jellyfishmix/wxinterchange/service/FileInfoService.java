package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.entity.FileInfo;
import java.util.List;

/**
 * 文件信息表(FileInfo)表服务接口
 *
 * @author makejava
 * @since 2020-04-19 23:26:14
 */
public interface FileInfoService {

    /**
     * 通过fileId查询单条数据
     *
     * @param fileId 文件fileId
     * @return 实例对象
     */
    FileInfo queryByFileId(String fileId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<FileInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param fileInfo 实例对象
     * @return 实例对象
     */
    FileInfo insert(FileInfo fileInfo);

    /**
     * 修改数据
     *
     * @param fileInfo 实例对象
     * @return 实例对象
     */
    FileInfo update(FileInfo fileInfo);

    /**
     * 通过fileId删除数据
     *
     * @param fileId 文件fileId
     * @return 是否成功
     */
    boolean deleteByFileId(String fileId);

}
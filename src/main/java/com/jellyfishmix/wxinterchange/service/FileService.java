package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.entity.FileInfo;

/**
 * 文件信息表(FileInfo)表服务接口
 *
 * @author makejava
 * @since 2020-04-19 23:26:14
 */
public interface FileService {
    /**
     * 通过fileId查询单条数据
     *
     * @param fileId 文件fileId
     * @return 实例对象
     */
    FileInfo queryByFileId(String fileId);
}
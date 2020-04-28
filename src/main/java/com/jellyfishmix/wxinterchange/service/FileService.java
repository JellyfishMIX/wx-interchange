package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.FileInfoDTO;

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
    FileInfoDTO queryByFileId(String fileId);

    /**
     * 从七牛云bucket中删除文件
     *
     * @param fileHash 全局唯一的文件Hash值，用来检查是否可以从七牛云bucket中删除
     * @param fileKey 文件fileKey
     */
    void deleteFromQiniuBucket(String fileHash, String fileKey);
}
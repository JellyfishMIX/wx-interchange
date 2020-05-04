package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.FileInfoDTO;
import com.jellyfishmix.wxinterchange.entity.FileInfo;

import java.util.List;

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
     * 通过fileId列表查询文件信息列表
     *
     * @param fileInfoList 列表
     * @return
     */
    List<FileInfoDTO> queryListByFileIdList(List<FileInfo> fileInfoList);

    /**
     * 更新文件信息
     *
     * @param fileInfo 文件信息
     * @return 更新后的文件信息
     */
    FileInfoDTO updateFileInfo(FileInfo fileInfo);

    /**
     * 从七牛云bucket中删除文件
     *
     * @param fileHash 全局唯一的文件Hash值，用来检查是否可以从七牛云bucket中删除
     * @param fileKey 文件fileKey
     */
    void deleteFromQiniuBucket(String fileHash, String fileKey);
}
package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.CollectionFileDTO;
import com.jellyfishmix.wxinterchange.entity.CollectionFile;
import com.jellyfishmix.wxinterchange.entity.CollectionInfo;
import com.jellyfishmix.wxinterchange.enums.CollectionEnum;

import java.util.List;

/**
 * @author JellyfishMIX
 * @date 2020/4/29 6:05 下午
 */
public interface CollectionService {
    /**
     * 通过uid获取收藏集文件列表
     *
     * @param uid 用户uid
     * @param pageIndex 页码，从1开始
     * @param pageSize 每页行数
     * @return 收藏集文件列表
     */
    List<CollectionFileDTO> queryFileList(String uid, int pageIndex, int pageSize);

    /**
     * 新建一个收藏集
     *
     * @param collectionInfo 收藏集信息对象
     */
    void createCollection(CollectionInfo collectionInfo);

    /**
     * 批量添加文件至收藏集
     *
     * @param uid 操作者uid
     * @param collectionFileList 收藏集文件列表
     */
    void addedFileList(String uid, List<CollectionFile> collectionFileList);

    /**
     * 更新收藏集的计数属性
     *
     * @param collectionId 收藏集collectionId
     * @param collectionEnum 操作标志Enum
     * @param countChangeNum 计数更改的数量，有正负
     */
    void updateCollectionCountProperty(String collectionId, CollectionEnum collectionEnum, Integer countChangeNum);

    /**
     * 删除文件列表（批量）
     *
     * @param collectionId 收藏集collectionId
     * @param collectionFileList 收藏集文件列表
     */
    void deleteFileList(String collectionId, List<CollectionFile> collectionFileList);
}
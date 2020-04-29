package com.jellyfishmix.wxinterchange.service;

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
     * 更新项目组的计数属性
     *
     * @param collectionId 收藏集collectionId
     * @param collectionEnum 操作标志Enum
     * @param countChangeNum 计数更改的数量，有正负
     */
    void updateCollectionCountProperty(String collectionId, CollectionEnum collectionEnum, Integer countChangeNum);
}

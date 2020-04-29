package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.CollectionInfo;

/**
 * 文件收藏集表(CollectionInfo)表数据库访问层
 *
 * @author JellyfishMIX
 * @since 2020-04-29 15:02:48
 */
public interface CollectionInfoDao {
    /**
     * 通过collectionId查询单条数据
     *
     * @param collectionId 收藏集collectionId
     * @return 实例对象
     */
    CollectionInfo queryByCollectionId(String collectionId);

    /**
     * 通过用户uid查询单条数据
     *
     * @param uid 用户uid
     * @return
     */
    CollectionInfo queryByUid(String uid);

    /**
     * 新增数据
     *
     * @param collectionInfo 实例对象
     * @return 影响行数
     */
    int insert(CollectionInfo collectionInfo);

    /**
     * 修改数据
     *
     * @param collectionInfo 实例对象
     * @return 影响行数
     */
    int update(CollectionInfo collectionInfo);

    // delete暂不开放
}
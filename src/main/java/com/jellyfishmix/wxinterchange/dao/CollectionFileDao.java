package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.CollectionFile;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 文件收藏集的文件关联表(CollectionFile)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-29 16:01:55
 */
public interface CollectionFileDao {
    /**
     * 通过collectionId查询收藏集文件列表
     *
     * @param collectionId 收藏集collectionId
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 收藏集文件列表
     */
    List<CollectionFile> queryListByCollectionIdAndFileId(@Param("collection_id") String collectionId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 新增数据
     *
     * @param collectionFile 实例对象
     * @return 影响行数
     */
    int insert(CollectionFile collectionFile);

    /**
     * 通过collectionId和fileId删除数据
     *
     * @param collectionId 收藏集collectionId
     * @return 影响行数
     */
    int deleteById(String collectionId);
}
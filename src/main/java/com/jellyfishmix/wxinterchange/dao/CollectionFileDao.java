package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.dto.CollectionFileDTO;
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
     * 通过collectionId和fileId查询单条数据
     *
     * @param collectionId 收藏集collectionId
     * @param fileId 文件fileId
     * @return
     */
    CollectionFile queryByCollectionIdAndFileId(@Param("collectionId") String collectionId, @Param("fileId") String fileId);

    /**
     * 通过collectionId查询收藏集文件列表
     *
     * @param collectionId 收藏集collectionId
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 收藏集文件列表
     */
    List<CollectionFileDTO> queryListByCollectionId(@Param("collectionId") String collectionId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 新增数据（单个）
     *
     * @param collectionFile 实例对象
     * @return 影响行数
     */
    int insert(CollectionFile collectionFile);

    /**
     * 新增数据（多个）
     *
     * @param collectionFileList 实例对象列表
     * @return 影响行数
     */
    int insertList(List<CollectionFile> collectionFileList);

    /**
     * 收藏集中将被删除的fileId替换成404，对应404文件
     *
     * @param fileId 文件fileId
     * @return 影响行数
     */
    int updateFileIdTo404(@Param("fileId") String fileId);

    /**
     * 通过collectionId和fileId删除数据
     *
     * @param collectionId 收藏集collectionId
     * @param fileId 文件fileId
     * @return 影响行数
     */
    int deleteByCollectionIdAndFileId(@Param("collectionId") String collectionId, @Param("fileId") String fileId);

    /**
     * 删除数据（多个）
     *
     * @param collectionFileList 收藏集文件列表
     */
    void deleteList(List<CollectionFile> collectionFileList);
}
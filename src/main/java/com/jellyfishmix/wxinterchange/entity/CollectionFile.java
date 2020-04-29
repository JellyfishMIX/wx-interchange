package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 文件收藏集的文件关联表(CollectionFile)实体类
 *
 * @author makejava
 * @since 2020-04-29 16:01:55
 */
@Data
public class CollectionFile {
    /**
    * 代理主键
    */
    private Integer id;
    /**
    * 收藏集collection_id
    */
    private String collectionId;
    /**
    * 文件fileId
    */
    private String fileId;
    /**
    * 创建时间，自动写入
    */
    private Date creationTime;
    /**
    * 修改时间，自动写入
    */
    private Date modifiedTime;
}
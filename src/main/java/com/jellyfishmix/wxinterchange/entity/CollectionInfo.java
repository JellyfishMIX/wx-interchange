package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 文件收藏集表(CollectionInfo)实体类
 *
 * @author makejava
 * @since 2020-04-29 15:02:48
 */
@Data
public class CollectionInfo {
    /**
    * 代理主键
    */
    private Integer id;
    /**
    * 收藏集collection_id
    */
    private String collectionId;
    /**
    * 收藏集名称
    */
    private String collectionName;
    /**
    * 创建者uid
    */
    private String uid;
    /**
    * 收藏集文件计数
    */
    private Integer fileCount;
    /**
    * 创建时间，自动写入
    */
    private Date creationTime;
    /**
    * 修改时间，自动写入
    */
    private Date modifiedTime;
}
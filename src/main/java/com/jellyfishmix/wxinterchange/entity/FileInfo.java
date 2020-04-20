package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;

/**
 * 文件信息表(FileInfo)实体类
 *
 * @author makejava
 * @since 2020-04-19 23:26:14
 */
@Data
public class FileInfo {
    /**
    * 代理主键
    */
    private Integer id;
    /**
    * 文件fileId
    */
    private String fileId;
    /**
    * 文件资源fileKey
    */
    private String fileKey;
    /**
    * 全局唯一的文件fileHash值
    */
    private String fileHash;
    /**
    * 文件名
    */
    private String fileName;
    /**
    * 文件资源URL
    */
    private String fileUrl;
    /**
    * 文件大小, 单位为b
    */
    private Integer fileSize;
    /**
    * 文件类型
    */
    private String mimeType;
    /**
     * 上传者uid，外键
     */
    private String uid;
    /**
    * 创建时间，自动写入
    */
    private Date creationTime;
    /**
    * 修改时间，自动写入
    */
    private Date modifiedTime;
}
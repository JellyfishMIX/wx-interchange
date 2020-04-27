package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 项目组头像表(TeamAvatar)实体类
 *
 * @author makejava
 * @since 2020-04-27 21:51:47
 */
@Data
public class TeamAvatar {
    /**
    * 代理主键
    */
    private Integer id;
    /**
    * 头像文件avatarId
    */
    private String avatarId;
    /**
    * 所属项目组tid，外键
    */
    private String tid;
    /**
    * 文件资源fileKey
    */
    private String fileKey;
    /**
    * 全局唯一的文件Hash值
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
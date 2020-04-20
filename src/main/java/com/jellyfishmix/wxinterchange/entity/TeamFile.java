package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 项目组文件表(TeamFile)实体类
 *
 * @author makejava
 * @since 2020-04-20 01:42:19
 */
@Data
public class TeamFile {
    /**
    * 代理主键
    */
    private Integer id;
    /**
    * 项目组tid，外键
    */
    private String tid;
    /**
    * 文件fileId，外键
    */
    private String fileId;
    /**
    * 上传者uid，外键
    */
    private String uid;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件资源URL
     */
    private String fileUrl;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 文件类型
     */
    private String mimeType;
    /**
     * 上传者名称
     */
    private String username;
    /**
    * 创建时间，自动写入
    */
    private Date creationTime;
    /**
    * 修改时间，自动写入
    */
    private Date modifiedTime;
}
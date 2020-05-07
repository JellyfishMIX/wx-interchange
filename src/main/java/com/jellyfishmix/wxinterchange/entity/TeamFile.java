package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;

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
    * 创建时间，自动写入
    */
    private Date creationTime;
    /**
    * 修改时间，自动写入
    */
    private Date modifiedTime;
}
package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;

/**
 * 项目组表(TeamInfo)实体类
 *
 * @author makejava
 * @since 2020-04-11 09:52:53
 */
@Data
public class TeamInfo {
    /**
    * 代理主键
    */
    private Integer id;
    /**
    * 项目组tid，随机生成，唯一键
    */
    private String tid;
    /**
    * 项目组名称
    */
    private String teamName;
    /**
    * 项目组头像URL，非必须
    */
    private String avatarUrl;
    /**
    * 项目组等级，官方项目组为1，普通项目组为2，保留0
    */
    private Integer grade;
    /**
    * 项目组成员数量
    */
    private Integer numberCount;
    /**
    * 项目组创建者数量
    */
    private Integer createdNumberCount;
    /**
    * 项目组管理者数量
    */
    private Integer managedNumberCount;
    /**
    * 项目组加入者数量
    */
    private Integer joinedNumberCount;
    /**
    * 项目组文件数量
    */
    private Integer fileCount;
    /**
     * 最后一次文件上传时间
     */
    private Date lastFileUploadTime;
    /**
    * 创建时间，自动写入
    */
    private Date creationTime;
    /**
    * 修改时间，自动写入
    */
    private Date modifiedTime;
}
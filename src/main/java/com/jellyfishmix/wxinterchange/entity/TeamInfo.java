package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

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
    * 绑定的微信群gid，非必须，外键
    */
    private String gid;
    /**
    * 项目组名称
    */
    private String teamName;
    /**
    * 项目组头像URL，非必须
    */
    private String avatarUrl;
    /**
    * 项目组等级，测试项目组为99，官方项目组为1，普通项目组为2，保留0
    */
    private Integer grade;
    /**
    * 项目组成员数量
    */
    private Integer numberCounts;
    /**
    * 项目组创建者数量
    */
    private Integer createdNumberCounts;
    /**
    * 项目组管理者数量
    */
    private Integer managedNumberCounts;
    /**
    * 项目组加入者数量
    */
    private Integer joinedNumberCounts;
    /**
    * 项目组文件数量
    */
    private Integer fileCounts;
    /**
    * 创建时间，自动写入
    */
    private Date creationTime;
    /**
    * 修改时间，自动写入
    */
    private Date modifiedTime;
}
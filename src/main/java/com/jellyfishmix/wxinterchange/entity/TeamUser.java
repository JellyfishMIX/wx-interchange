package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;

/**
 * 项目组成员表(TeamUser)实体类
 *
 * @author makejava
 * @since 2020-04-11 21:09:44
 */
@Data
public class TeamUser {
    /**
    * 代理主键
    */
    private Integer id;
    /**
    * 项目组tid，外键
    */
    private String tid;
    /**
    * 用户uid，外键
    */
    private String uid;
    /**
    * 项目组成员等级，1为创建者，2为管理员，3为普通成员
    */
    private Integer userGrade;
    /**
    * 创建时间，自动写入（加入项目组的时间）
    */
    private Date creationTime;
    /**
    * 修改时间，自动写入
    */
    private Date modifiedTime;
}
package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author JellyfishMIX
 * @date 2020/4/6 6:19 下午
 * 用户表
 */
@Data
public class UserInfo {
    /**
     * 代理主键
     */
    private Integer id;
    /**
     * 用户uid，随机生成，唯一键
     */
    private String uid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户头像url
     */
    private String avatarUrl;
    /**
     * 用户全局等级，超级管理员为1，官方合作者为2，普通用户为3，保留0
     */
    private Integer globalGrade;
    /**
     * 微信用户openid，唯一键
     */
    private String openid;
    /**
     * 创建的项目组数量
     */
    private Integer createdTeamCount;
    /**
     * 管理的项目组数量
     */
    private Integer managedTeamCount;
    /**
     * 加入的项目组数量
     */
    private Integer joinedTeamCount;
    /**
     * 创建时间，自动写入
     */
    private Date creationTime;
    /**
     * 修改时间，自动写入
     */
    private Date modifiedTime;
}

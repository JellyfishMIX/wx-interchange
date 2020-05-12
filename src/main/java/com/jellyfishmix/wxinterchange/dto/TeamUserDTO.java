package com.jellyfishmix.wxinterchange.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author JellyfishMIX
 * @date 2020/4/24 10:12 上午
 */
@Data
public class TeamUserDTO {
    /**
     * 项目组tid，外键
     */
    private String tid;
    /**
     * 用户uid，外键
     */
    private String uid;
    /**
     * 项目组名称
     */
    private String teamName;
    /**
     * 用户名
     */
    private String username;
    /**
     * 项目组头像URL，非必须，头像文件储存在微信服务器
     */
    private String teamAvatarUrl;
    /**
     * 项目组成员头像URL，非必须，头像文件储存在微信服务器
     */
    private String userAvatarUrl;
    /**
     * 项目组等级，官方项目组为1，普通项目组为2，保留0
     */
    private Integer teamGrade;
    /**
     * 项目组成员等级，1为创建者，2为管理员，3为普通成员
     */
    private Integer userGrade;
    /**
     * 项目组成员数量
     */
    private Integer numberCount;
    /**
     * 项目组文件数量
     */
    private Integer fileCount;
    /**
     * 创建时间，自动写入（加入项目组的时间）
     */
    private Date creationTime;
    /**
     * 修改时间，自动写入
     */
    private Date modifiedTime;
}

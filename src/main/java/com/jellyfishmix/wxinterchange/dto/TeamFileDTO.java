package com.jellyfishmix.wxinterchange.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author JellyfishMIX
 * @date 2020/4/24 11:02 上午
 */
@Data
public class TeamFileDTO {
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
     * 文件资源fileKey
     */
    private String fileKey;
    /**
     * 全局唯一的文件fileHash值
     */
    private String fileHash;
    /**
     * 创建时间，自动写入
     */
    private Date creationTime;
    /**
     * 修改时间，自动写入
     */
    private Date modifiedTime;
}

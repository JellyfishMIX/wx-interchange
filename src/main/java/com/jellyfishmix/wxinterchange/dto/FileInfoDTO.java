package com.jellyfishmix.wxinterchange.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author JellyfishMIX
 * @date 2020/4/23 9:04 下午
 */
@Data
public class FileInfoDTO {
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
     * 上传者用户名
     */
    private String username;
    /**
     * 创建时间，自动写入
     */
    private Date creationTime;
}

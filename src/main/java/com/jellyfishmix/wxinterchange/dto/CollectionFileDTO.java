package com.jellyfishmix.wxinterchange.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author JellyfishMIX
 * @date 2020/4/29 4:41 下午
 */
@Data
public class CollectionFileDTO {
    /**
     * 代理主键
     */
    private Integer id;
    /**
     * 收藏集collection_id
     */
    private String collectionId;
    /**
     * 文件fileId
     */
    private String fileId;
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
     * 上传者名称
     */
    private String username;
    /**
     * 创建时间，自动写入
     */
    private Date creationTime;
}

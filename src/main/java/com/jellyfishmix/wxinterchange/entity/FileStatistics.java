package com.jellyfishmix.wxinterchange.entity;

import lombok.Data;

import java.util.Date;

/**
 * 埋点文件统计表，以天为单位(FileStatistics)实体类
 *
 * @author makejava
 * @since 2020-05-25 09:29:35
 */
@Data
public class FileStatistics {
    /**
    * 文件统计statisticsId
    */
    private String statisticsId;
    /**
    * 当日文件数量
    */
    private Integer quantity;
    /**
    * 当日实时文件数量变化。有正负
    */
    private Integer changedQuantity;
    /**
     * 数据等级，1为天数据，2为周数据
     */
    private Integer grade;
    /**
    * 创建时间
    */
    private Date creationTime;
    /**
     * 修改时间
     */
    private Date modifiedTime;
}
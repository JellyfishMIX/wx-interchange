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
    * 当日结算文件数量变化，每24小时统计一次。统计方法：当日减前一日文件数量。有正负
    */
    private Integer changedQuantity;
    /**
    * 当日实时文件数量变化。有正负
    */
    private Integer instantChangedQuantity;
    /**
    * 创建时间
    */
    private Date creationTime;
    /**
     * 修改时间
     */
    private Date modifiedTime;
}
package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.FileStatistics;

import java.sql.Timestamp;

/**
 * 埋点文件统计表，以天为单位(FileStatistics)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-25 09:29:35
 */
public interface FileStatisticsDao {

    /**
     * 通过statisticsId查询单条数据
     *
     * @param statisticsId 文件统计statisticsId
     * @return 实例对象
     */
    FileStatistics queryByStatisticsId(String statisticsId);

    /**
     * 查询指定日期的数据
     *
     * @param firstTimestamp 起始timestamp
     * @param lastTimestamp 终止timestamp
     * @return
     */
    FileStatistics queryByDesignatedTimestamp(Timestamp firstTimestamp, Timestamp lastTimestamp);

    /**
     * 新增数据
     *
     * @param fileStatistics 实例对象
     * @return 影响行数
     */
    int insert(FileStatistics fileStatistics);

    /**
     * 修改数据
     *
     * @param fileStatistics 实例对象
     * @return 影响行数
     */
    int update(FileStatistics fileStatistics);

    /**
     * 通过通过statisticsId删除数据
     *
     * @param statisticsId 文件统计statisticsId
     * @return 影响行数
     */
    int deleteByStatisticsId(String statisticsId);

}
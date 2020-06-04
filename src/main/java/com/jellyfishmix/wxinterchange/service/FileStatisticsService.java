package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.enums.CronScheduleEnum;
import org.quartz.Scheduler;

/**
 * @author JellyfishMIX
 * @date 2020/5/25 4:50 下午
 */
public interface FileStatisticsService {
    /**
     * 每日计划处理
     *
     * @param scheduler 调度程序的实例
     * @param cronScheduleEnum cron表达式Enum
     */
    void dailyProcessing(Scheduler scheduler, CronScheduleEnum cronScheduleEnum);

    /**
     * 更改当日实时文件数量变化
     *
     * @param changedQuantityNum 改变的数量
     */
    void updateInstantChangedQuantity(int changedQuantityNum);
}

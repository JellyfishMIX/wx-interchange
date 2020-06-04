package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.enums.CronScheduleEnum;
import org.quartz.Scheduler;

/**
 * @author JellyfishMIX
 * @date 2020/6/3 9:03 下午
 */
public interface SearchStatisticsService {
    /**
     * 每日计划处理
     *
     * @param scheduler 调度程序的实例
     * @param cronScheduleEnum cron时间表Enum
     */
    void dailyProcessing(Scheduler scheduler, CronScheduleEnum cronScheduleEnum);
}

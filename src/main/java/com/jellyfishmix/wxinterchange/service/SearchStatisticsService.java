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
     * @param cronScheduleEnum cron表达式Enum
     */
    void dailyProcessing(Scheduler scheduler, CronScheduleEnum cronScheduleEnum);

    /**
     * 每周计划处理
     *
     * @param scheduler 调度程序的实例
     * @param cronScheduleEnum cron表达式Enum
     */
    void weeklyProcessing(Scheduler scheduler, CronScheduleEnum cronScheduleEnum);

    /**
     * 记录搜索keyword
     *
     * @param keyword 搜索keyword
     */
    void recordSearchKeyword(String keyword);
}

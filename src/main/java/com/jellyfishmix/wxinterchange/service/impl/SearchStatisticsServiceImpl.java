package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.enums.CronScheduleEnum;
import com.jellyfishmix.wxinterchange.quartz.SearchStatisticsDailyJob;
import com.jellyfishmix.wxinterchange.quartz.SearchStatisticsWeeklyJob;
import com.jellyfishmix.wxinterchange.service.SearchStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

/**
 * @author JellyfishMIX
 * @date 2020/6/3 9:13 下午
 */
@Service("searchStatisticsService")
@Slf4j
public class SearchStatisticsServiceImpl implements SearchStatisticsService {
    /**
     * 每日计划处理
     *
     * @param scheduler        调度程序的实例
     * @param cronScheduleEnum cron表达式Enum
     */
    @Override
    public void dailyProcessing(Scheduler scheduler, CronScheduleEnum cronScheduleEnum) {
        try {
            // 重要:
            // 定义一个job，并绑定到我们自定义的HelloJob的class对象
            // 这里并不会马上创建一个HelloJob实例，实例创建是在scheduler安排任务触发执行时创建的
            // 这种机制也为后面使用Spring集成提供了便利
            JobDetail jobDetail = JobBuilder.newJob(SearchStatisticsDailyJob.class)
                    .withIdentity("searchStatistics.dailyProcessing()")
                    .build();

            // 声明一个触发器，在执行schedule.start()方法开始调用的时候执行
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("searchStatistics.dailyProcessing()")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronScheduleEnum.getCronExpr()))
                    .build();

            // 告诉quartz使用定义的触发器trigger安排执行任务job
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 每周计划处理
     *
     * @param scheduler        调度程序的实例
     * @param cronScheduleEnum cron表达式Enum
     */
    @Override
    public void weeklyProcessing(Scheduler scheduler, CronScheduleEnum cronScheduleEnum) {
        try {
            // 重要:
            // 定义一个job，并绑定到我们自定义的HelloJob的class对象
            // 这里并不会马上创建一个HelloJob实例，实例创建是在scheduler安排任务触发执行时创建的
            // 这种机制也为后面使用Spring集成提供了便利
            JobDetail jobDetail = JobBuilder.newJob(SearchStatisticsWeeklyJob.class)
                    .withIdentity("searchStatistics.weeklyProcessing()")
                    .build();

            // 声明一个触发器，在执行schedule.start()方法开始调用的时候执行
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("searchStatistics.weeklyProcessing()")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronScheduleEnum.getCronExpr()))
                    .build();

            // 告诉quartz使用定义的触发器trigger安排执行任务job
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }
}

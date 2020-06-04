package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.enums.CronScheduleEnum;
import com.jellyfishmix.wxinterchange.enums.ScheduledTaskEnum;
import com.jellyfishmix.wxinterchange.exception.ScheduledTaskException;
import com.jellyfishmix.wxinterchange.service.FileStatisticsService;
import com.jellyfishmix.wxinterchange.service.ScheduledTaskService;
import com.jellyfishmix.wxinterchange.service.SearchStatisticsService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * @author JellyfishMIX
 * @date 2020/5/28 3:21 上午
 */
@Service("scheduledTaskService")
public class ScheduledTaskServiceImpl implements ScheduledTaskService {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private FileStatisticsService fileStatisticsService;
    @Autowired
    private SearchStatisticsService searchStatisticsService;
    /**
     * 启动定时任务系统
     */
    @Override
    public void start() {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            // 每天1:00执行
            fileStatisticsService.dailyProcessing(scheduler, CronScheduleEnum.DAILY_01_00);
            // 每天1:10执行
            searchStatisticsService.dailyProcessing(scheduler, CronScheduleEnum.DAILY_01_10);
            // 每周周日1:20执行
            searchStatisticsService.weeklyProcessing(scheduler, CronScheduleEnum.WEEKLY_SUN_01_20);
            scheduler.start();
        } catch (SchedulerException e) {
            throw new ScheduledTaskException(ScheduledTaskEnum.START_EXCEPTION, e.getMessage());
        }
    }

    /**
     * 关闭定时任务系统
     */
    @Override
    public void shutdown() {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.shutdown();
        } catch (SchedulerException e) {
            throw new ScheduledTaskException(ScheduledTaskEnum.SHUTDOWN_EXCEPTION, e.getMessage());
        }
    }
}

package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.enums.CronScheduleEnum;
import com.jellyfishmix.wxinterchange.enums.ScheduledTaskEnum;
import com.jellyfishmix.wxinterchange.exception.ScheduledTaskException;
import com.jellyfishmix.wxinterchange.service.FileStatisticsService;
import com.jellyfishmix.wxinterchange.service.ScheduledTaskService;
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
    private FileStatisticsService fileStatisticsService;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 启动定时任务系统
     */
    @Override
    public void start() {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            // 每天凌晨一点执行
            fileStatisticsService.dailyProcessing(scheduler, CronScheduleEnum.DAILY_ONE_O_CLOCK);
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

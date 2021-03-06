package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dao.FileStatisticsDao;
import com.jellyfishmix.wxinterchange.entity.FileStatistics;
import com.jellyfishmix.wxinterchange.enums.CronScheduleEnum;
import com.jellyfishmix.wxinterchange.enums.FileStatisticsEnum;
import com.jellyfishmix.wxinterchange.exception.FileStatisticsException;
import com.jellyfishmix.wxinterchange.quartz.FileStatisticsDailyJob;
import com.jellyfishmix.wxinterchange.service.FileStatisticsService;
import com.jellyfishmix.wxinterchange.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author JellyfishMIX
 * @date 2020/5/25 7:58 下午
 */
@Service("fileStatisticsService")
@Slf4j
public class FileStatisticsServiceImpl implements FileStatisticsService {
    @Resource
    private FileStatisticsDao fileStatisticsDao;

    /**
     * 每日计划处理
     *
     * @param scheduler 调度程序的实例
     * @param cronScheduleEnum cron时间表Enum
     */
    @Override
    public void dailyProcessing(Scheduler scheduler, CronScheduleEnum cronScheduleEnum) {
        try {
            // 重要:
            // 定义一个job，并绑定到我们自定义的HelloJob的class对象
            // 这里并不会马上创建一个HelloJob实例，实例创建是在scheduler安排任务触发执行时创建的
            // 这种机制也为后面使用Spring集成提供了便利
            JobDetail jobDetail = JobBuilder.newJob(FileStatisticsDailyJob.class)
                    .withIdentity("fileStatistics.dailyProcessing()")
                    .build();

            // 声明一个触发器，在执行schedule.start()方法开始调用的时候执行
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("fileStatistics.dailyProcessing()")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronScheduleEnum.getCronExpr()))
                    .build();

            // 告诉quartz使用定义的触发器trigger安排执行任务job
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 更改当日实时文件数量变化
     *
     * @param changedQuantityNum 改变的数量
     */
    @Override
    public void updateInstantChangedQuantity(int changedQuantityNum) {
        // 数据等级，1为天数据，2为周数据
        Integer grade = 1;
        Timestamp todayFirstTimestamp = DateUtil.todayFirstTimestamp();
        Timestamp todayLastTimestamp = DateUtil.todayLastTimestamp();
        FileStatistics fileStatisticsFromQuery = fileStatisticsDao.queryByDesignatedTimestamp(grade, todayFirstTimestamp, todayLastTimestamp);
        if (fileStatisticsFromQuery == null) {
            throw new FileStatisticsException(FileStatisticsEnum.FILE_STATISTICS_NULL);
        }

        FileStatistics fileStatisticsForUpdate = new FileStatistics();
        fileStatisticsForUpdate.setStatisticsId(fileStatisticsFromQuery.getStatisticsId());
        fileStatisticsForUpdate.setChangedQuantity(fileStatisticsFromQuery.getChangedQuantity() + changedQuantityNum);
        fileStatisticsDao.update(fileStatisticsForUpdate);
    }
}

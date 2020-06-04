package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.enums.CronScheduleEnum;
import com.jellyfishmix.wxinterchange.service.SearchStatisticsService;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

/**
 * @author JellyfishMIX
 * @date 2020/6/3 9:13 下午
 */
@Service("searchStatisticsService")
public class SearchStatisticsServiceImpl implements SearchStatisticsService {
    /**
     * 每日计划处理
     *
     * @param scheduler        调度程序的实例
     * @param cronScheduleEnum cron时间表Enum
     */
    @Override
    public void dailyProcessing(Scheduler scheduler, CronScheduleEnum cronScheduleEnum) {
        // try {
        //
        // }
    }
}

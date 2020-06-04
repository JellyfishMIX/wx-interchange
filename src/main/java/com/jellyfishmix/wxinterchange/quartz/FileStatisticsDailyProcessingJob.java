package com.jellyfishmix.wxinterchange.quartz;

import com.jellyfishmix.wxinterchange.service.FileStatisticsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
 *
 * @author JellyfishMIX
 * @date 2020/5/28 3:41 上午
 */
public class FileStatisticsDailyProcessingJob implements Job, Serializable {
    private static final long serialVersionUID = 1L;
    @Resource
    private FileStatisticsService fileStatisticsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        fileStatisticsService.insertTomorrowFileStatistics();
    }
}

package com.jellyfishmix.wxinterchange.quartz;

import com.jellyfishmix.wxinterchange.dao.FileStatisticsDao;
import com.jellyfishmix.wxinterchange.entity.FileStatistics;
import com.jellyfishmix.wxinterchange.service.FileStatisticsService;
import com.jellyfishmix.wxinterchange.utils.DateUtil;
import com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
 *
 * @author JellyfishMIX
 * @date 2020/5/28 3:41 上午
 */
public class FileStatisticsDailyJob implements Job, Serializable {
    @Resource
    private FileStatisticsDao fileStatisticsDao;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        this.insertTomorrowFileStatistics();
    }

    /**
     * 为当前时间的下一天创建一个新的`file_statistics`记录行
     */
    private void insertTomorrowFileStatistics() {
        FileStatistics fileStatistics = new FileStatistics();
        fileStatistics.setStatisticsId(UniqueKeyUtil.getUniqueKey());
        // 当前时间的下一天
        Timestamp targetTimestamp = DateUtil.differenceDayFromCurrentTimestamp(1);
        fileStatistics.setCreationTime(targetTimestamp);
        fileStatistics.setModifiedTime(targetTimestamp);
        fileStatistics.setQuantity(0);
        fileStatisticsDao.insert(fileStatistics);
    }
}

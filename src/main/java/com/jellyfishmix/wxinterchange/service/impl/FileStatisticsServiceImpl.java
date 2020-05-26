package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dao.FileStatisticsDao;
import com.jellyfishmix.wxinterchange.entity.FileStatistics;
import com.jellyfishmix.wxinterchange.enums.FileStatisticsEnum;
import com.jellyfishmix.wxinterchange.exception.FileStatisticsException;
import com.jellyfishmix.wxinterchange.service.FileStatisticsService;
import com.jellyfishmix.wxinterchange.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author JellyfishMIX
 * @date 2020/5/25 7:58 下午
 */
@Service("fileStatisticsService")
public class FileStatisticsServiceImpl implements FileStatisticsService {
    @Resource
    private FileStatisticsDao fileStatisticsDao;

    /**
     * 更改当日实时文件数量变化
     *
     * @param changedQuantityNum 改变的数量
     */
    @Override
    public void updateInstantChangedQuantity(int changedQuantityNum) {
        Timestamp todayFirstTimestamp = DateUtil.todayFirstTimestamp();
        Timestamp todayLastTimestamp = DateUtil.todayLastTimestamp();
        FileStatistics fileStatisticsFromQuery = fileStatisticsDao.queryByDesignatedTimestamp(todayFirstTimestamp, todayLastTimestamp);
        if (fileStatisticsFromQuery == null) {
            throw new FileStatisticsException(FileStatisticsEnum.FILE_STATISTICS_NULL);
        }

        FileStatistics fileStatisticsForUpdate = new FileStatistics();
        fileStatisticsForUpdate.setStatisticsId(fileStatisticsFromQuery.getStatisticsId());
        fileStatisticsForUpdate.setInstantChangedQuantity(fileStatisticsFromQuery.getInstantChangedQuantity() + changedQuantityNum);
        fileStatisticsDao.update(fileStatisticsForUpdate);
    }
}

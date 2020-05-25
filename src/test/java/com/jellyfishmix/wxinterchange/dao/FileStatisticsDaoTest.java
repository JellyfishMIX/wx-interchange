package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.FileStatistics;
import com.jellyfishmix.wxinterchange.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JellyfishMIX
 * @date 2020/5/25 11:04 下午
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class FileStatisticsDaoTest {
    @Resource
    private FileStatisticsDao fileStatisticsDao;

    @Test
    void queryByDesignatedTimestamp() {
        Timestamp todayFirstTimestamp = DateUtil.todayFirstTimestamp();
        Timestamp todayLastTimestamp = DateUtil.todayLastTimestamp();

        FileStatistics fileStatistics = fileStatisticsDao.queryByDesignatedTimestamp(todayFirstTimestamp, todayLastTimestamp);
        assertEquals("12345", fileStatistics.getStatisticsId());
    }
}
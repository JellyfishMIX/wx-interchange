package com.jellyfishmix.wxinterchange.service.impl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JellyfishMIX
 * @date 2020/5/25 10:55 下午
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class FileStatisticsServiceImplTest {
    @Autowired
    FileStatisticsServiceImpl fileStatisticsServiceImpl;

    @Test
    @Disabled
    void updateInstantChangedQuantity() {
        fileStatisticsServiceImpl.updateInstantChangedQuantity(15);
    }
}
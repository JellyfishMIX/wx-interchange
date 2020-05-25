package com.jellyfishmix.wxinterchange.service;

/**
 * @author JellyfishMIX
 * @date 2020/5/25 4:50 下午
 */
public interface FileStatisticsService {
    /**
     * 更改当日实时文件数量变化
     *
     * @param changedQuantityNum 改变的数量
     */
    void updateInstantChangedQuantity(int changedQuantityNum);
}

package com.jellyfishmix.wxinterchange.service;

/**
 * @author JellyfishMIX
 * @date 2020/5/28 3:19 上午
 */
public interface ScheduledTaskService {
    /**
     * 启动定时任务系统
     */
    void start();

    /**
     * 关闭定时任务系统
     */
    void shutdown();
}

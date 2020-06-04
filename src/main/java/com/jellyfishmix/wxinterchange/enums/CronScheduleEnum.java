package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * quartz cron时间表Enum
 *
 * @author JellyfishMIX
 * @date 2020/5/28 3:53 上午
 */
@Getter
public enum CronScheduleEnum {
    // 每天1:00
    DAILY_01_00("0 0 1 * * ?"),
    // 每天1:10
    DAILY_01_10("0 10 1 * * ?"),
    // 每周周日1:20（1表示周日）
    WEEKLY_SUN_01_20("0 20 1 ? * SUN");

    /**
     * cron表达式，用于配制CronTrigger实例，指示何时触发Trigger
     */
    private String cronExpr;

    CronScheduleEnum(String cronExpr) {
        this.cronExpr = cronExpr;
    }
}

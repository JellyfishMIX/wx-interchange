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
    // 每天凌晨一点
    // DAILY_ONE_O_CLOCK("0 0 1 * * ?");
    DAILY_ONE_O_CLOCK("0 11 3 * * ?");

    /**
     * cron表达式，用于配制CronTrigger实例，指示何时触发Trigger
     */
    private String cronExpr;

    CronScheduleEnum(String cronExpr) {
        this.cronExpr = cronExpr;
    }
}

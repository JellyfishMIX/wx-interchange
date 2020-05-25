package com.jellyfishmix.wxinterchange.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author JellyfishMIX
 * @date 2020/5/25 9:33 下午
 */
public class DateUtil {
    /**
     * 获取前今天最前的Date, 0:00'0''0
     *
     * @return
     */
    public static Date todayFirstDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取前今天最后的Date, 23:59'59''999
     *
     * @return
     */
    public static Date todayLastDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取前今天最前的Timestamp, 0:00'0''0
     *
     * @return
     */
    public static Timestamp todayFirstTimestamp() {
        Date todayFirstDate =  DateUtil.todayFirstDate();
        return new Timestamp(todayFirstDate.getTime());
    }

    /**
     * 获取前今天最前的Timestamp, 0:00'0''0
     *
     * @return
     */
    public static Timestamp todayLastTimestamp() {
        Date todayLastDate =  DateUtil.todayLastDate();
        return new Timestamp(todayLastDate.getTime());
    }
}

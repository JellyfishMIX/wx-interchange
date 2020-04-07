package com.jellyfishmix.wxinterchange.utils;

import java.util.Random;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 1:59 上午
 */
public class UniqueKeyUtil {
    /**
     * 生成唯一的主键，18位字符串
     * 格式：时间 + 随机数
     * 高并发情况下可能会出现重名，所以需要使用synchronized关键词来修饰
     * @return
     */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer randomInteger = random.nextInt(90000) + 10000;

        return System.currentTimeMillis() + String.valueOf(randomInteger);
    }
}

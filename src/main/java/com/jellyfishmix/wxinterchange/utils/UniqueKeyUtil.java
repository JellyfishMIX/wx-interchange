package com.jellyfishmix.wxinterchange.utils;

import java.util.Random;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 1:59 上午
 */
public class UniqueKeyUtil {
    /**
     * 生成不重复的8位字符串
     * 10进制转16进制
     * 高并发情况下可能会出现重名，所以需要使用synchronized关键词来修饰
     * @return
     */
    public static synchronized String getUniqueKey() {
        long randomInteger = System.currentTimeMillis();
        String hexString = Long.toHexString(randomInteger);
        return hexString;
    }
}

package com.jellyfishmix.wxinterchange.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 1:59 上午
 */
@Slf4j
public class UniqueKeyUtil {
    /**
     * 生成不重复的11位字符串
     * 10进制转16进制
     * 高并发情况下可能会出现重名，所以需要使用synchronized关键词来修饰
     * @return
     */
    public static synchronized String getUniqueKey() {
        long randomInteger = System.currentTimeMillis();
        // 睡眠1ms，避免出现重复的key
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            log.error("[UniqueKeyUtil]Thread.sleep() error, errMsg = {}", e.getMessage());
        }
        String hexString = Long.toHexString(randomInteger);
        return hexString;
    }
}

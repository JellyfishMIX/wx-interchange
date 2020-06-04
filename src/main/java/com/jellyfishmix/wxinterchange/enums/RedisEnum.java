package com.jellyfishmix.wxinterchange.enums;

import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/6/4 11:55 上午
 */
@Getter
public enum RedisEnum {
    DAILY_SEARCH_HOT_WORD_ZSET("dailySearchHotWord"),
    WEEKLY_SEARCH_HOT_WORD_ZSET("weeklySearchHotWord");

    private String key;

    RedisEnum(String key) {
        this.key = key;
    }
}

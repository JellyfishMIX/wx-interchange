package com.jellyfishmix.wxinterchange.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * @author JellyfishMIX
 * @date 2020/4/27 4:53 下午
 */
public interface RedisService {
    /**
     * 删除指定的key
     *
     * @param key key
     */
    void deleteKey(String key);

    // Distributed Lock

    /**
     * 加锁
     * 此方法不能使当前Thread sleep，如果需要使当前Thread sleep，请使用RedisLockService.lockConvenient
     *
     * @param key key值
     * @param value 分布式锁过期时间。当前时间 + 超时时间
     * @return true拿到锁，false未拿到锁
     */
    boolean lock(String key, String value);

    /**
     * 解锁，即删除key
     *
     * @param key key值
     * @param value 分布式锁过期时间。当前时间 + 超时时间。此处用来做校验，key和value对应再删除。
     */
    void unlock(String key, String value);

    /**
     * 快捷加锁
     *
     * @param identifierForLock 标识符
     * @param timeout 分布式锁生存时间
     * @return 分布式锁过期时间
     */
    long lockConvenient(String identifierForLock, int timeout);

    // Sorted Set

    /**
     * zincrby命令，对于一个Sorted Set，存在的就把分数加1，不存在就创建一个分数为1的成员
     *
     * @param sortedSetName 要操作的Sorted Set名字
     * @param value value
     */
    void zincrby(String sortedSetName, String value);

    /**
     * zrevrange命令, 查询集合中指定顺序的值
     * 返回有序的集合中，score大的在前面
     *
     * @param sortedSetName 要操作的sortedSet名字
     * @param start 查询范围开始位置
     * @param end 结束位置
     * @return
     */
    Set<ZSetOperations.TypedTuple<String>> queryTopSearchHotKey(String sortedSetName, Integer start, Integer end);

}

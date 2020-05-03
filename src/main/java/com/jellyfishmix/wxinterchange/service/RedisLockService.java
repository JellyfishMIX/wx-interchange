package com.jellyfishmix.wxinterchange.service;

/**
 * @author JellyfishMIX
 * @date 2020/4/27 4:53 下午
 */
public interface RedisLockService {
    /**
     * 加锁
     * @param key key值
     * @param value 当前时间 + 超时时间
     * @return true拿到锁，false未拿到锁
     */
    boolean lock(String key, String value);

    /**
     * 解锁，即删除key
     *
     * @param key key值
     * @param value 当前时间 + 超时时间。此处用来做校验，key和value对应再删除。
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
}

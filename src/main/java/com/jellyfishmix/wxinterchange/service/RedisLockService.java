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
    public boolean lock(String key, String value);

    /**
     * 解锁，即删除key
     *
     * @param key key值
     * @param value 当前时间 + 超时时间。此处用来做校验，key和value对应再删除。
     */
    public void unlock(String key, String value);
}

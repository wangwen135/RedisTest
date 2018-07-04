package com.wwh.redis.spring.service;

import java.util.TreeMap;

/**
 * <pre>
 * Redis服务接口
 * 就是一些基础的redis操作方法
 * </pre>
 * 
 * @author wwh
 * @date 2017年9月22日 下午2:29:46
 */
public interface RedisService {

    void set(String key, String value);

    String get(String key);

    /**
     * Remove the specified keys. If a given key does not exist no operation is performed for this key.
     * 
     * @param key
     * @return
     */
    void delKey(String key);

    /**
     * 判断key是否存在
     * 
     * @param key
     * @return
     */
    boolean hasKey(String key);

    /**
     * Add the specified member to the set value stored at key. If member is already a member of the set no operation is performed.
     * 
     * @param key
     * @param values
     * @return
     */
    Long setAdd(String key, String... values);

    /**
     * Return the set cardinality (number of elements). If the key does not exist 0 is returned, like for empty sets.
     * 
     * @param key
     * @return
     */
    Long setSize(String key);

    /**
     * Add the string value to the head (LPUSH) or tail (RPUSH) of the list stored at key. If the key does not exist an empty list is created just
     * before the append operation. If the key exists but is not a List an error is returned.
     * 
     * @param key
     * @param value
     * @return
     */
    Long rightPushList(String key, String value);

    /**
     * Atomically return and remove the first (LPOP) or last (RPOP) element of the list
     * 
     * @param key
     * @return
     */
    String leftPopList(String key);

    /**
     * Return the length of the list stored at the specified key. If the key does not exist zero is returned (the same behaviour as for empty lists).
     * If the value stored at key is not a list an error is returned.
     * 
     * @param key
     * @return
     */
    Long listSize(String key);

    /**
     * Set the specified hash field to the specified value.
     * 
     * @param key
     * @param field
     * @param value
     */
    void hashSet(String key, String field, String value);

    /**
     * If key holds a hash, retrieve the value associated to the specified field.
     * 
     * @param key
     * @param field
     * @return
     */
    String hashGet(String key, String field);

    /**
     * 增加计数
     * 
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    Long countIncrement(String key, String hashKey, long delta);

    /**
     * 获取计数
     * 
     * @param key
     * @param hashKey
     * @return
     */
    Long countGet(String key, String hashKey);

    /**
     * 统计速率用<br>
     * 同一个任务记录速率的key需要保存在同一个Redis服务中
     * 
     * @param prefix
     *            前缀，用于确定分片
     * @param key
     *            键值
     * @param delta
     *            增量
     * @param ttl
     *            存活时间
     * @return
     */
    Long rateIncrement(String prefix, String key, long delta, long ttl);

    /**
     * 获取速率<br>
     * 同一个任务记录速率的key需要保存在同一个Redis服务中
     * 
     * @param prefix
     *            前缀，用于确定分片
     * 
     * @return key 和 long值 对
     */
    TreeMap<String, Long> getRateByPrefix(String prefix);

    /**
     * 设置某key的超时时间
     * 
     * @param key
     *            键
     * @param ttl
     *            超时时间，单位毫秒
     * @return
     */
    Boolean expire(String key, long ttl);

}

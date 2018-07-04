package com.wwh.redis.spring.shard;

import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wwh.redis.spring.service.RedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * <pre>
 * Redis 服务实现
 * </pre>
 * 
 * @author wwh
 * @date 2017年9月22日 下午3:06:06
 */
@Service("redisService")
public class RedisShardServiceImpl implements RedisService {

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Override
    public void set(String key, String value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    @Override
    public String get(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public void delKey(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.del(key);
        }
    }

    @Override
    public boolean hasKey(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.exists(key);
        }
    }

    @Override
    public Long setAdd(String key, String... values) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.sadd(key, values);
        }
    }

    @Override
    public Long setSize(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.scard(key);
        }
    }

    @Override
    public Long rightPushList(String key, String value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.rpush(key, value);
        }
    }

    @Override
    public String leftPopList(String key) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.lpop(key);
        }
    }

    @Override
    public Long listSize(String key) {

        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.llen(key);
        }

    }

    @Override
    public void hashSet(String key, String field, String value) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            jedis.hset(key, field, value);
        }
    }

    @Override
    public String hashGet(String key, String field) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.hget(key, field);
        }

    }

    @Override
    public Long countIncrement(String key, String hashKey, long delta) {
        try (ShardedJedis jedis = shardedJedisPool.getResource()) {
            return jedis.hincrBy(key, hashKey, delta);
        }
    }

    @Override
    public Long countGet(String key, String hashKey) {

        String ret = hashGet(key, hashKey);

        if (ret == null || ret.equals("")) {
            return 0l;
        } else {
            return Long.valueOf(ret);
        }

    }

    @Override
    public Boolean expire(String key, long ttl) {

        // 转成秒
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(ttl);

        try (ShardedJedis jedis = shardedJedisPool.getResource()) {

            return jedis.expire(key, seconds) == 1;

        }
    }

    @Override
    public Long rateIncrement(String prefix, String key, long delta, long ttl) {
        // ShardedJedis 不支持keys这种方式，因为无法确定key所属的分片

        try (ShardedJedis shardedJedis = shardedJedisPool.getResource()) {

            // 需要先确定分片
            Jedis jedis = shardedJedis.getShard(prefix);

            Long l = jedis.incrBy(key, delta);

            jedis.pexpire(key, ttl);

            return l;
        }

    }

    @Override
    public TreeMap<String, Long> getRateByPrefix(String prefix) {
        // ShardedJedis 不支持keys这种方式，因为无法确定key所属的分片

        try (ShardedJedis shardedJedis = shardedJedisPool.getResource()) {

            // 所有的这一堆的key都需要保存到一个具体的分片

            Jedis jedis = shardedJedis.getShard(prefix);

            Set<String> set = jedis.keys(prefix + "*");

            TreeMap<String, Long> tmap = new TreeMap<>();

            for (String key : set) {
                String v = jedis.get(key);

                tmap.put(key, Long.valueOf(v == null || v.equals("") ? 0l : Long.valueOf(v)));
            }

            return tmap;
        }

    }

}

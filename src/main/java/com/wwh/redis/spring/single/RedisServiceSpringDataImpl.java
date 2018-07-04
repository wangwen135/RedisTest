package com.wwh.redis.spring.single;

import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.wwh.redis.spring.service.RedisService;

/**
 * <pre>
 * Redis 服务实现
 * </pre>
 * 
 * @author wwh
 * @date 2017年9月22日 下午3:06:06
 */
@Service("redisService")
public class RedisServiceSpringDataImpl implements RedisService {

    // 这边用的都是序列化为String的Redis模板

    @Resource(name = "stringRedisTemplate")
    private RedisTemplate<String, String> template;

    @Resource(name = "stringRedisTemplate")
    private ListOperations<String, String> listOps;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOps;

    @Resource(name = "stringRedisTemplate")
    private HashOperations<String, String, String> hashOps;

    @Resource(name = "stringRedisTemplate")
    private SetOperations<String, String> setOps;

    @Override
    public void set(String key, String value) {
        valueOps.set(key, value);
    }

    @Override
    public String get(String key) {
        return valueOps.get(key);
    }

    @Override
    public void delKey(String key) {
        template.delete(key);
    }

    @Override
    public boolean hasKey(String key) {
        return template.hasKey(key);
    }

    @Override
    public Long setAdd(String key, String... values) {
        return setOps.add(key, values);
        // return template.boundSetOps(key).add(values);

    }

    @Override
    public Long setSize(String key) {
        return setOps.size(key);
        // return template.boundSetOps(key).size();
    }

    @Override
    public Long rightPushList(String key, String value) {
        return listOps.rightPush(key, value);
        // return template.boundListOps(key).rightPush(value);
    }

    @Override
    public String leftPopList(String key) {

        return listOps.leftPop(key);

        // return template.boundListOps(key).leftPop();
    }

    @Override
    public Long listSize(String key) {

        return listOps.size(key);

        // return template.boundListOps(key).size();
    }

    @Override
    public void hashSet(String key, String field, String value) {

        hashOps.put(key, field, value);
    }

    @Override
    public String hashGet(String key, String field) {

        return hashOps.get(key, field);
    }

    @Override
    public Long countIncrement(String key, String hashKey, long delta) {
        return hashOps.increment(key, hashKey, delta);
    }

    @Override
    public Long countGet(String key, String hashKey) {

        String ret = hashOps.get(key, hashKey);

        if (ret == null || ret.equals("")) {
            return 0l;
        } else {
            return Long.valueOf(ret);
        }

    }

    @Override
    public Boolean expire(String key, long ttl) {
        return template.expire(key, ttl, TimeUnit.MILLISECONDS);
    }

    @Override
    public Long rateIncrement(String prefix, String key, long delta, long ttl) {

        Long l = valueOps.increment(key, delta);

        // 设置超时时间
        template.expire(key, ttl, TimeUnit.MILLISECONDS);

        return l;
    }

    @Override
    public TreeMap<String, Long> getRateByPrefix(String prefix) {

        Set<String> set = template.keys(prefix + "*");

        TreeMap<String, Long> tmap = new TreeMap<>();

        for (String key : set) {
            String v = valueOps.get(key);

            tmap.put(key, Long.valueOf(v == null || v.equals("") ? 0l : Long.valueOf(v)));
        }

        return tmap;

    }

}

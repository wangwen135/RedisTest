package com.wwh.redis.spring.shard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wwh.redis.spring.service.RedisService;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class Launcher2 {
    private final static Logger logger = LoggerFactory.getLogger(Launcher2.class);

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext2.xml");

        ShardedJedisPool pool = context.getBean(ShardedJedisPool.class);
        RedisService redisService = context.getBean(RedisService.class);

        try (ShardedJedis jedis = pool.getResource()) {
            jedis.set("x", "foo");
            jedis.set("x1", "foo1");
            jedis.set("x2", "foo2");
        }

        try (ShardedJedis jedis = pool.getResource()) {
            System.out.println(jedis.get("x"));
            System.out.println(jedis.get("x1"));
            System.out.println(jedis.get("x2"));
        }

        try (ShardedJedis jedis2 = pool.getResource()) {
            System.out.println(jedis2.get("y"));
            System.out.println(jedis2.get("y1"));
            System.out.println(jedis2.get("y2"));
        }

        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            System.out.println(redisService.hasKey("test2-" + i));
            System.out.println(redisService.get("test2-" + i));
        }

        context.close();
    }
}
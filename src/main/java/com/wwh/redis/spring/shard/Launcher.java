package com.wwh.redis.spring.shard;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wwh.redis.spring.service.RedisService;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class Launcher {
    private final static Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext2.xml");

        ShardedJedisPool pool = context.getBean(ShardedJedisPool.class);
        RedisService redisService = context.getBean(RedisService.class);

        try (ShardedJedis jedis = pool.getResource()) {
            logger.debug(jedis.get("x"));
            logger.debug(jedis.get("x1"));
            logger.debug(jedis.get("x2"));
        }


        logger.debug("set value");

        redisService.set("test2", "11111");

        logger.debug("has key : {}", redisService.hasKey("test2"));

        logger.debug("get value : {}", redisService.get("test2"));

        logger.debug("耗时测试开始");
        Long t1 = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            String key = "test2-" + random.nextInt(100);

            redisService.hasKey(key);

            redisService.get(key);

            redisService.set(key, "c" + i);

        }
        logger.debug("耗时：{}", (System.currentTimeMillis() - t1));

        context.close();
    }
}
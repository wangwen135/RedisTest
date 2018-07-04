package com.wwh.redis.spring.single;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wwh.redis.spring.service.RedisService;

public class Launcher {
    private final static Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        RedisService redisService = context.getBean(RedisService.class);

        logger.debug("set value");

        redisService.set("test1", "11111");

        logger.debug("has key : {}", redisService.hasKey("test1"));

        logger.debug("get value : {}", redisService.get("test1"));

        logger.debug("耗时测试开始");
        Long t1 = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            String key = "test1-" + random.nextInt(100);

            redisService.hasKey(key);

            redisService.get(key);

            redisService.set(key, "c" + i);

        }
        logger.debug("耗时：{}", (System.currentTimeMillis() - t1));

        context.close();
    }
}
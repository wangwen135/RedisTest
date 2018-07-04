package com.wwh.redis.spring.Annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class Launcher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        ctx.register(AppConfig.class);
        ctx.refresh();

        ShardedJedisPool pool = ctx.getBean(ShardedJedisPool.class);

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

        pool.close();

        ctx.close();
    }
}

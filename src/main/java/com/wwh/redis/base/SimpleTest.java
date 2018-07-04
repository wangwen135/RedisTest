package com.wwh.redis.base;

import redis.clients.jedis.Jedis;

public class SimpleTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.213", 6380);

        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            jedis.del("test1-" + i);
            jedis.del("test2-" + i);
        }

        jedis.close();
    }

    public static void main1(String[] args) {
        Jedis jedis = new Jedis("192.168.1.214");
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println(value);

        jedis.close();
    }
}

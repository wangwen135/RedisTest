package com.wwh.redis.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * <pre>
 * 客户端分片
 * </pre>
 * 
 */
public class ShardedJedisPoolTest {

    public static void main(String[] args) {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        JedisShardInfo si = new JedisShardInfo("192.168.1.213", 6380);
        shards.add(si);
        si = new JedisShardInfo("192.168.1.213", 6381);
        shards.add(si);
        si = new JedisShardInfo("192.168.1.213", 6382);
        shards.add(si);

        // 池连接
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        ShardedJedisPool pool = new ShardedJedisPool(jedisPoolConfig, shards);

        try (ShardedJedis jedis = pool.getResource()) {

            for (int i = 0; i < 100; i++) {
                System.out.println(i);
                jedis.del("test1-" + i);
                jedis.del("test2-" + i);
            }
        }

        pool.close();
    }

    public static void main2(String[] args) {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        JedisShardInfo si = new JedisShardInfo("192.168.1.213", 6380);
        shards.add(si);
        si = new JedisShardInfo("192.168.1.213", 6381);
        shards.add(si);
        si = new JedisShardInfo("192.168.1.213", 6382);
        shards.add(si);

        // 池连接
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        ShardedJedisPool pool = new ShardedJedisPool(jedisPoolConfig, shards);

        try (ShardedJedis jedis = pool.getResource()) {
            jedis.set("x", "foo");
            jedis.set("x1", "foo1");
            jedis.set("x2", "foo2");
        }

        try (ShardedJedis jedis2 = pool.getResource()) {
            jedis2.set("y", "bar");
            jedis2.set("y1", "bar1");
            jedis2.set("y2", "bar2");
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

        try (ShardedJedis jedis = pool.getResource()) {
            Jedis j = jedis.getShard("x1");

            // System.out.println(j.info());
            System.out.println(j.getClient().getSocket());

            Set<String> s = j.keys("x*");
            for (String string : s) {
                System.out.println(string);
            }

            j.pexpire("x1", 3000l);

        }

        try (ShardedJedis jedis = pool.getResource()) {
            Jedis j = jedis.getShard("x2");

            // System.out.println(j.info());
            System.out.println(j.getClient().getSocket());

            Set<String> s = j.keys("x*");
            for (String string : s) {
                System.out.println(string);
            }

            j.pexpire("x2", 3000l);

        }

        try (ShardedJedis jedis = pool.getResource()) {
            Jedis j = jedis.getShard("xx");

            // System.out.println(j.info());
            System.out.println(j.getClient().getSocket());

            j.set("xx1", "v");
            j.set("xx2", "v");
            j.set("xx3", "v");
            j.set("xx4", "v");
            j.set("xx5", "v");
            j.set("xx6", "v");
            j.set("xx7", "v");
            j.set("xx8", "v");
            j.set("xx9", "v");
            j.set("xx10", "v");

            Set<String> s = j.keys("xx*");
            for (String string : s) {
                System.out.println(string);
            }

        }

        pool.close();
    }
}

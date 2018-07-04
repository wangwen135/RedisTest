package com.wwh.redis.base;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.ShardInfo;

/**
 * <pre>
 * 这个是客户端分片的方式
 * 计算key的hash，然后对服务器数量取模，决定将数据存储到哪个服务器上
 * </pre>
 * 
 */
public class ShardedJedisTest {

    public static void main(String[] args) {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        JedisShardInfo si = new JedisShardInfo("192.168.1.213", 6380);
        shards.add(si);
        si = new JedisShardInfo("192.168.1.213", 6381);
        shards.add(si);
        si = new JedisShardInfo("192.168.1.213", 6382);
        shards.add(si);

        // 直接连接
        ShardedJedis jedis = new ShardedJedis(shards);
        jedis.set("a", "foo");
        jedis.set("a1", "foo1");
        jedis.set("a2", "foo2");
        jedis.set("a3", "foo3");
        jedis.set("a4", "foo4");

        System.out.println(jedis.get("a"));
        System.out.println(jedis.get("a1"));
        System.out.println(jedis.get("a2"));
        System.out.println(jedis.get("a3"));
        System.out.println(jedis.get("a4"));

        ShardInfo<?> sinfo = jedis.getShardInfo("a");
        System.out.println(sinfo);

        jedis.disconnect();
        jedis.close();
    }
}

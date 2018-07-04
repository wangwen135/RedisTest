package com.wwh.redis.base;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class ClusterTest {

    public static void main(String[] args) throws IOException {
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        // Jedis集群将尝试自动发现集群节点
        jedisClusterNodes.add(new HostAndPort("192.168.1.214", 6379));
        // jedisClusterNodes.add(new HostAndPort("192.168.1.214", 6380));
        // jedisClusterNodes.add(new HostAndPort("192.168.1.214", 6381));
        JedisCluster jc = new JedisCluster(jedisClusterNodes);
        jc.set("foo", "bar");
        jc.set("foo1", "bar1");
        jc.set("foo2", "bar2");
        jc.set("foo3", "bar3");
        jc.set("foo4", "bar4");
        jc.set("foo5", "bar5");
        jc.set("foo6", "bar6");

        System.out.println(jc.get("foo"));
        System.out.println(jc.get("foo1"));
        System.out.println(jc.get("foo2"));
        System.out.println(jc.get("foo3"));
        System.out.println(jc.get("foo4"));
        System.out.println(jc.get("foo5"));
        System.out.println(jc.get("foo6"));

        System.out.println("===================");

        Map<String, JedisPool> map = jc.getClusterNodes();
        for (Map.Entry<String, JedisPool> e : map.entrySet()) {
            System.out.println(e.getKey());
            System.out.println("===================");
        }
        
        jc.close();

    }
}

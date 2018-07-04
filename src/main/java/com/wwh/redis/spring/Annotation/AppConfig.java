package com.wwh.redis.spring.Annotation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

@Configuration
@PropertySource("classpath:redis.properties")
public class AppConfig {

    @Bean(name = "shardInfos")
    public List<JedisShardInfo> shardInfos() {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        JedisShardInfo si = new JedisShardInfo("192.168.1.213", 6380);
        shards.add(si);
        si = new JedisShardInfo("192.168.1.213", 6381);
        shards.add(si);
        si = new JedisShardInfo("192.168.1.213", 6382);
        shards.add(si);
        return shards;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        return jedisPoolConfig;
    }

    @Bean
    @Autowired
    public ShardedJedisPool shardedJedisPool(JedisPoolConfig jedisPoolConfig, List<JedisShardInfo> shards) {
        ShardedJedisPool pool = new ShardedJedisPool(jedisPoolConfig, shards);

        return pool;

    }
}

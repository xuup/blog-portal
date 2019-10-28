package com.blog.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
	
	private static volatile JedisPool jedisPool = null;

	private JedisPoolUtil(){}
	
	public static JedisPool getJedisPoolInstance(){
		if(jedisPool == null){
			synchronized (JedisPoolUtil.class) {
				if(jedisPool == null){
					@SuppressWarnings("deprecation")
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					poolConfig.setMaxTotal(100);
					poolConfig.setMaxIdle(20);
					poolConfig.setMaxWaitMillis(100*1000);
					poolConfig.setTestOnBorrow(true);
					jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
				}
			}
		}
		return jedisPool;
	}
	
}

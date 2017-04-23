package com.redis;

import redis.clients.jedis.Jedis;

public class Redis {
	
	public Jedis getJedis() {
		
		Jedis jedis = new Jedis("localhost");
		
		
		return jedis;
	}	
	
	
}

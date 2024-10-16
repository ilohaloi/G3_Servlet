package com.laiutil.redis;


import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

	 private static JedisPool jedisPool = createConnectionPool(20, 6380);

	 public static JedisPool getPool() {
		 return jedisPool;
	 }
	 private static JedisPool createConnectionPool(int conAmount, int post) {
		    JedisPoolConfig poolConfig = new JedisPoolConfig();
	        poolConfig.setMaxTotal(conAmount); // 設定最大連線數
	    	System.out.println("redis 建立成功");
	        return new JedisPool(poolConfig, "localhost", post);
	 }
	 public static void Release() {
		 if(jedisPool!=null)
			 jedisPool.destroy();
	 }

}

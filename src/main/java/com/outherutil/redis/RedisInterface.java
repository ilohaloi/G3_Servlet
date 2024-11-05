package com.outherutil.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.outherutil.Tuple;

import kotlin.Pair;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;


public interface RedisInterface {
	public final Long NO_TTL = -1L;
	 /**
	  * 單筆插入
	  * */
	public default void redisInsert(JedisPool pool,Tuple<String, String, String> data,long expirSec) {
		try(Jedis jedis = pool.getResource()) {
			jedis.hset(data.getK() + data.getV1(), "token",data.getV2());
			if(expirSec != NO_TTL)
				jedis.expire(data.getK() + data.getV1(),expirSec);
		}
	}
	/**
	 * 	物件插入
	 * */
	public default void redisInsert(JedisPool pool,String unionName,Pair<String, Map<String,String>> data,long secMillis) {
		try(Jedis jedis = pool.getResource()) {
			jedis.hset(unionName + data.component1(), data.component2());

			if(secMillis != NO_TTL)
				jedis.expire(unionName + data.component1() ,secMillis );
		}
	}

	/**
	 * 	復數物件插入
	 * */
	public default void redisInsert(JedisPool pool,String unionName,List<Pair<String, Map<String,String>>> data,long secMillis) {
		try(Jedis jedis = pool.getResource()) {

			data.forEach(p->{
				jedis.hset(unionName + p.component1(), p.component2());
				if(secMillis!=NO_TTL){
					jedis.expire(unionName+p.component1(),secMillis);
				}
			});
		}
	}

	public void redisSetExpire(JedisPool pool,String key,int timeSec);


	public Object redisGetByKey(JedisPool pool,String key);

	public List<?> redisGetAllByKey(JedisPool pool, String folderName);

	public default List<String> keyScan(Jedis jedis,String key){
		String cursor = "0";
		ScanParams scanParams = new ScanParams().match(key);
		List<String> keys = new ArrayList<>();
		 do {
		        ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
		        List<String> resultKeys = scanResult.getResult();
		        keys.addAll(resultKeys);
		        cursor = scanResult.getCursor();
		    } while (!cursor.equals("0"));
		 return keys;
	}
}

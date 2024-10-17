package com.laiutil.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.laiutil.Tuple;
import kotlin.Pair;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;


public interface RedisInterface {

	 /**
	  * 單筆插入
	  * */
	public default void redisInsert(JedisPool pool,Tuple<String, String, String> data,long expirMillis) {
		try(Jedis jedis = pool.getResource()) {
			jedis.hset(data.getK() + data.getV1(), "token",data.getV2());
			jedis.expire(data.getK() + data.getV1(),expirMillis );
		}
	}
	/**
	 * 	物件插入
	 * */
	public default void redisInsert(JedisPool pool,String setName,Pair<String, Map<String,String>> data) {
		try(Jedis jedis = pool.getResource()) {
			jedis.hset(setName + data.component1(), data.component2());
		}
	}

	public void redisUpdate(JedisPool pool,Object data);
	public void redisSetExpire(JedisPool pool,String key,int timeSec);


	public Object redisGetByKey(JedisPool pool,String key);
	public List<Object> redisGetAllByKey(JedisPool pool,String key);


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

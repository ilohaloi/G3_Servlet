package com.laiutil;

import java.util.List;
import java.util.Map;

import com.cipher.model.WebDataVo;
import com.laiutil.redis.RedisInterface;

import kotlin.Pair;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class AuthService implements RedisInterface {

	public boolean authCheck(JedisPool pool,WebDataVo data) {
		@SuppressWarnings("unchecked")
		var token = (Pair<String, String>)redisGetByKey(pool,"login:"+data.getIdentity());
		if(token == null)
			return false;
		else if(token.component2().equals(data.getToken()))
			return true;
		else
			return false;
	}

	@Override
	public void redisUpdate(JedisPool pool, Object data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void redisSetExpire(JedisPool pool, String key, int timeSec) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object redisGetByKey(JedisPool pool, String key) {
		Pair<String, String> token = null;
		try(Jedis jedis = pool.getResource()) {
			Map<String,String> data = jedis.hgetAll(key);
			for (String str : data.keySet()) {
				if(str.equals("token"))
					token = new Pair<String, String>("token", data.get(str));

			}
			return token;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List redisGetAllByKey(JedisPool pool, String folderName) {
		// TODO Auto-generated method stub
		return null;
	}

}

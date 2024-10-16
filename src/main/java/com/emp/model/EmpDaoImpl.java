package com.emp.model;


import java.util.List;
import java.util.Map;

import com.laiutil.redis.RedisInterface;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class EmpDaoImpl implements EmpInterface ,RedisInterface{

	private final String empRedisKeyFormt = "emp:";

	public boolean isAccountDuplication(String account) {
		return getByAccount(account)!=null?true:false;
	}

	@Override
	public void redisUpdate(JedisPool pool,Object data) {

		try(Jedis jedis = pool.getResource()) {

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void redisSetExpire(JedisPool pool,String key, int timeSec) {

		try(Jedis jedis = pool.getResource()) {
			jedis.expire(empRedisKeyFormt+key, timeSec);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	public EmpVo redisGetByKey(JedisPool pool, String key) {
		EmpVo emp = new EmpVo();
		try(Jedis jedis = pool.getResource()) {
			Map<String,String> data = jedis.hgetAll(empRedisKeyFormt+key);
			for (String str : data.keySet()) {
				if(str.equals("account"))
					emp.setAccount(data.get(str));
				if(str.equals("pubKey")) {
					emp.setPublicKey(data.get(str));
				}
				if(str.equals("password")) {
					emp.setPassword(data.get(str));
				}
			}
		}
		return emp;
	}

	public EmpVo typeCheck(Object data) {
		if (data instanceof EmpVo) {
	        return (EmpVo) data;
	    }
	    throw new IllegalArgumentException("Invalid data type. Expected EmpVo.");
	}

	@Override
	public List<Object> redisGetAllByKey(JedisPool pool, String key) {
		return null;
	}
}

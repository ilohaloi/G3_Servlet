package com.prod.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

import com.outherutil.Dao;
import com.outherutil.HibernateUtil;
import com.outherutil.redis.RedisInterface;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class ProdDaoImpl implements Dao ,RedisInterface{

	/**
	 * 只更新 數量 或 單價
	 * */

	@Override
	public <T> void update(int id, T data) {
		Session session  = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			ProdVo orgProd = session.get(ProdVo.class, id);
			if(orgProd!=null) {
				ProdVo newProd = (ProdVo)data;
				orgProd.setPrice(newProd.getPrice());
				orgProd.setStock(orgProd.getStock()+newProd.getStock());
				session.update(orgProd);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	@Override
	public void redisSetExpire(JedisPool pool, String key, int timeSec) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object redisGetByKey(JedisPool pool, String key) {
		try(Jedis jedis = pool.getResource()){
			Map<String, String> prodData = jedis.hgetAll(key);
			ProdVo prod = new ProdVo(prodData);
			prod.setId(Integer.valueOf(key.substring(5)));
			return prod;
		}
	}


	@Override
	public List<ProdVo> redisGetAllByKey(JedisPool pool, String folderName) {
		List<ProdVo> prods = new ArrayList<ProdVo>();
		try(Jedis jedis = pool.getResource()){
			//搜尋 prod: 所有的key
			var keys = keyScan(jedis, folderName);
			for (String id : keys) {

				//搜尋 key 裡面的所有kv
                Map<String, String> prodData = jedis.hgetAll(id);
                ProdVo prod = new ProdVo(prodData);
                
                
                prod.setId(Integer.valueOf(id.substring(5)));
                prods.add(prod);
            
			}
		}
		return prods;
	}






}

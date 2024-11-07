package com.prod.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.Session;

import com.order.model.OrderListVo;
import com.outherutil.Dao;
import com.outherutil.HibernateUtil;
import com.outherutil.redis.RedisInterface;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class ProdDaoImpl implements Dao ,RedisInterface{

	private String multipleQuery = "FROM ProdVo WHERE ";
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


	public List<ProdVo> getMultipleQuery(Map<String, String> query) {
		Session session  = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			session.beginTransaction();

			StringBuilder sBuilder = new StringBuilder();

			sBuilder.append(multipleQuery);
			Iterator<Map.Entry<String, String>> it = query.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();

				if("name".equals(entry.getKey())) {
					sBuilder.append(entry.getKey() +" LIKE :"  + entry.getKey());
				}else {

					sBuilder.append(entry.getKey() + " = :" + entry.getKey()); // 将列名与占位符绑定
				}


				if (it.hasNext()) {
					sBuilder.append(" AND "); // 如果还有下一项，则添加 "AND"
				}
			}

			// 创建 HQL 查询对象
			Query hqlQuery = session.createQuery(sBuilder.toString(), ProdVo.class);

			// 设置参数值，使用 entry 的键作为占位符名称
			for (var entry : query.entrySet()) {

				switch (entry.getKey()) {
				case "id":
					hqlQuery.setParameter(entry.getKey(), Integer.valueOf(entry.getValue()));
					break;
				case "name":
					hqlQuery.setParameter(entry.getKey(),"%" +  entry.getValue() + "%");
					break;
				case "stock":
					hqlQuery.setParameter(entry.getKey(), Integer.valueOf(entry.getValue()));
					break;
				case "category":
					hqlQuery.setParameter(entry.getKey(), entry.getValue());
					break;
				default:
					break;
				}
			}

			@SuppressWarnings("unchecked")
			List<ProdVo> list = (List<ProdVo>) hqlQuery.getResultList();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
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

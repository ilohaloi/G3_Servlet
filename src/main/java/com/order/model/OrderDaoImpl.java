package com.order.model;

import java.awt.font.OpenType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.Session;

import com.outherutil.Dao;
import com.outherutil.HibernateUtil;
import com.outherutil.redis.RedisInterface;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class OrderDaoImpl implements Dao, RedisInterface {
	private String multipleQuery = "FROM OrderListVo WHERE ";

	public void insert(OrderListVo data) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(data);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

	}

	@Override
	public <T> void update(int id, T data) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			OrderListVo orgOrder = session.get(OrderListVo.class, id);
			if (orgOrder != null) {
				OrderListVo newOrder = (OrderListVo) data;
				orgOrder.setStatus(newOrder.getStatus());
				session.update(orgOrder);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public List<OrderListVo> getByMembId(int id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "FROM OrderListVo WHERE membId = ?0";
		Query query = session.createQuery(hql, OrderListVo.class);
		query.setParameter(0, id);

		@SuppressWarnings("unchecked")
		List<OrderListVo> list = (List<OrderListVo>) query.getResultList();

		session.getTransaction().commit();
		return list;
	}

	public List<OrderDetailVo> getOrderDetail(int orderid, int membid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String hql = "FROM OrderListVo o JOIN FETCH o.orderDetails od JOIN FETCH od.prod WHERE o.orid = :orderId AND o.membId = :membId";

		Query query = session.createQuery(hql, OrderListVo.class);
		query.setParameter("orderId", orderid);
		query.setParameter("membId", membid);

		@SuppressWarnings("unchecked")
		List<OrderListVo> orders = query.getResultList();
		session.getTransaction().commit();

		return orders.get(0).getOrderDetails();
	}

	public OrderListVo getOrderDetail(int orderid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String hql = "FROM OrderListVo o JOIN FETCH o.orderDetails od JOIN FETCH od.prod WHERE o.orId = :orderId";
		Query query = session.createQuery(hql, OrderListVo.class);
		query.setParameter("orderId", orderid);
		@SuppressWarnings("unchecked")
		List<OrderListVo> orders = query.getResultList();
		session.getTransaction().commit();

		return orders.get(0);
	}

	public List<OrderListVo> getAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			String hql = "SELECT ol FROM OrderListVo ol LEFT JOIN FETCH ol.orderDetails od LEFT JOIN FETCH od.prod";
			List<OrderListVo> oList = session.createQuery(hql, OrderListVo.class).getResultList();
			session.getTransaction().commit();
			return oList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<OrderListVo> getMultipleQuery(Map<String, String> query) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			StringBuilder sBuilder = new StringBuilder();

			sBuilder.append(multipleQuery);
			Iterator<Map.Entry<String, String>> it = query.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				sBuilder.append(entry.getKey() + " = :" + entry.getKey()); // 将列名与占位符绑定

				if (it.hasNext()) {
					sBuilder.append(" AND "); // 如果还有下一项，则添加 "AND"
				}
			}

			// 创建 HQL 查询对象
			Query hqlQuery = session.createQuery(sBuilder.toString(), OrderListVo.class);

			// 设置参数值，使用 entry 的键作为占位符名称
			for (var entry : query.entrySet()) {

				switch (entry.getKey()) {
				case "orId":
					hqlQuery.setParameter(entry.getKey(), Integer.valueOf(entry.getValue()));
					break;
				case "membId":
					hqlQuery.setParameter(entry.getKey(), Integer.valueOf(entry.getValue()));
					break;
				case "status":
					hqlQuery.setParameter(entry.getKey(), entry.getValue());
					break;
				case "time":
					hqlQuery.setParameter(entry.getKey(), entry.getValue());
					break;
				default:
					break;
				}

				//hqlQuery.setParameter(entry.getKey(), entry.getValue());
			}

			@SuppressWarnings("unchecked")
			List<OrderListVo> list = (List<OrderListVo>) hqlQuery.getResultList();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}

	@Override
	public Object redisGetByKey(JedisPool pool, String key) {
		try (Jedis jedis = pool.getResource()) {
			Map<String, String> orderData = jedis.hgetAll(key);
			OrderListVo order = new OrderListVo(orderData);
			order.setOrid(Integer.valueOf(key.substring(6)));
			return order;
		}
	}

	@Override
	public List<OrderListVo> redisGetAllByKey(JedisPool pool, String folderName) {
		List<OrderListVo> orders = new ArrayList<OrderListVo>();
		try (Jedis jedis = pool.getResource()) {
			var keys = keyScan(jedis, folderName);
			for (String id : keys) {
				Map<String, String> orderData = jedis.hgetAll(id);
				OrderListVo order = new OrderListVo(orderData);
				order.setOrid(Integer.valueOf(id.substring(6)));
				orders.add(order);
			}
		}
		return orders;
	}

	@Override
	public void redisSetExpire(JedisPool pool, String key, int timeSec) {
		// TODO Auto-generated method stub

	}
}

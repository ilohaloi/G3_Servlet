package com.prod.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.laiutil.HibernateUtil;
import com.laiutil.redis.RedisUtil;
import com.prod.control.ProdService;
import com.prod.model.ProdDaoImpl;
import com.prod.model.ProdVo;

import redis.clients.jedis.JedisPool;

public class TestProd {
	static ProdVo prod;
	static ProdDaoImpl pDaoImpl;
	static ProdService pService;
	static JedisPool jedisPool;

	@BeforeAll
	public static void init() {
		HibernateUtil.getSessionFactory();
		prod = new ProdVo();
		prod.setId(1);
		prod.setName("pen");
		prod.setPrice(2000);
		prod.setStock(2000);
		pDaoImpl = new ProdDaoImpl();
		jedisPool = RedisUtil.getPool();
		pService = new ProdService();
	}

//	@Test
	public void testInsert() {
		pDaoImpl.insert(prod);
	}
//	@Test
	public void testGetAll() {
		pDaoImpl.getAll("products", ProdVo.class);
	}

//	@Test
	public void testUpdate() {
		pDaoImpl.update(3, prod);
	}
//	@Test
	public void testGetById() {
	 	pDaoImpl.getByPk(2, ProdVo.class);
	}

//	@Test
	public void testInsertRedis() {
		var prod = pDaoImpl.getAll("products", ProdVo.class);
		pService.insert(jedisPool, prod);
	}
//	@Test
	public void testGetAllkeyRedis() {
		pDaoImpl.redisGetAllByKey(jedisPool, "prod:*");
	}

	@Test
	public void testGetprodRedis() {
	 var g =  pService.getProd(jedisPool, "prod:17");
	 System.out.println(g);
	}
}

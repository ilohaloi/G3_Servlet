package com.route.model;

import java.util.List;
import java.util.Map;

import kotlin.Pair;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public interface RouteDAO_interface {
	public void insert(RouteVO routeVO);

	public void update(RouteVO routeVO);

	public void delete(Integer id);
	
	public List<RouteVO> search(String columnName,String vaule);

	public RouteVO findByPrimaryKey(Integer routeVO);

	public List<RouteVO> getAll();
	public default void redisInsert(JedisPool pool,String folderName,List<Pair<String, Map<String,String>>> data) {
		try(Jedis jedis = pool.getResource()) {
			data.forEach(p->{
				jedis.hset(folderName + p.component1(), p.component2());
			});
		}
	}
}

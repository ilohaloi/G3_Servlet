package com.route.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prod.model.ProdVo;

import kotlin.Pair;
import redis.clients.jedis.JedisPool;


public class RouteService {
	
	private RouteDAO_interface dao;

	public RouteService() {
//		dao = new RouteJDBCDAO();
		dao = new RouteDAO();
	}

	public RouteVO addRoute(int id, String name, String depiction,int days, int price) {

		RouteVO routeVO = new RouteVO();

		routeVO.setId(id);
		routeVO.setName(name);
		routeVO.setDepiction(depiction);
		routeVO.setDays(days);
		routeVO.setPrice(price);
		
		dao.insert(routeVO);

		return routeVO;
	}

	//預留給 Struts 2 或 Spring MVC 用
	public void addRoute(RouteVO routeVO) {
		dao.insert(routeVO);
	}
	
	public RouteVO updateRoute(int id, String name, String depiction,int days, int price) {

		RouteVO routeVO = new RouteVO();

		routeVO.setId(id);
		routeVO.setName(name);
		routeVO.setDepiction(depiction);
		routeVO.setDays(days);
		routeVO.setPrice(price);
		
		dao.update(routeVO);

		return dao.findByPrimaryKey(id);
	}
	
	//預留給 Struts 2 用的
	public void updateRoute(RouteVO routeVO) {
		dao.update(routeVO);
	}

	public void deleteRoute(Integer id) {
		dao.delete(id);
	}

	public RouteVO getOneRoute(Integer id) {
		return dao.findByPrimaryKey(id);
	}

	public List<RouteVO> getAll() {
		return dao.getAll();
	}
//	public void insert(JedisPool pool, List<RouteVO> prod) {
//		//第一個string是裝PK用的,第二個是KEY,第三個是VALUE
//		List<Pair<Integer,Map<String,String>>> data = new ArrayList<Pair<String,Map<String,String>>>();
//		prod.forEach(p->{
//			
//			Map<String, String> element = new HashMap<String, String>();
//			//要輸入鍵對值
//			element.put("name", p.getName());
//			element.put("price", String.valueOf(p.getPrice()));
//			element.put("depiction",p.getDepiction());
//			element.put("days", String.valueOf(p.getDays()));
//			
//												//改這段(String.valueOf(p.getId())
//			data.add(new Pair<String,Map<String,String>>(String.valueOf(p.getId()),element));
//		});
//		dao.redisInsert(pool,"prod:", data);//"prod:"冒號前的字要改資料夾名
//	}
	
}

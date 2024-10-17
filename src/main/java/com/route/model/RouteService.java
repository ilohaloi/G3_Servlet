package com.route.model;

import java.util.List;


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
}

package com.route.model;


	import java.util.List;

	
		public interface RouteDAO_interface {
	        public void insert(RouteVO routeVO);
	        public void update(RouteVO routeVO);
	        public void delete(Integer routeVO);
	        public RouteVO findByPrimaryKey(Integer routeVO);
	        public List<RouteVO> getAll();
	}
	



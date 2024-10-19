package com.route.model;
import com.route.model.RouteDAO;
import com.route.model.RouteVO;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		RouteDAO r = new RouteDAO();
		List<RouteVO> list = r.getAll();
		for(RouteVO routeVO:list) {
			System.out.print(routeVO.getId() + ",");
			System.out.print(routeVO.getName() + ",");
			System.out.print(routeVO.getPrice() + ",");
			System.out.print(routeVO.getDepiction() + ",");
			System.out.print(routeVO.getDays());
			System.out.println();
		}
	}

}

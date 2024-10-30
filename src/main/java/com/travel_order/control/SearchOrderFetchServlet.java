package com.travel_order.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.outherutil.json.JsonDeserializerInterface;
import com.outherutil.json.JsonSerializerInterface;
import com.route.model.RouteDAO;
import com.route.model.RouteVO;
import com.travel_order.model.Travel_orderDAO;
import com.travel_order.model.Travel_orderVO;

@WebServlet("/searchorderfetchservlet")
public class SearchOrderFetchServlet extends HttpServlet implements JsonDeserializerInterface , JsonSerializerInterface{
	
	private static final long serialVersionUID = -7148524428976683616L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 讓中文不亂碼
		req.setCharacterEncoding("UTF-8");
		var order = readJsonFromBufferedReader(req.getReader(),Travel_orderVO.class);
		//插入
		
		Travel_orderDAO travel_orderDAO = new Travel_orderDAO();
		var orderlist =  travel_orderDAO.search(order.getSelectedFilter(),order.getSearchQuery());
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(toJson(orderlist,false));
		orderlist.forEach(e->{
			System.out.println(e);
		});
	}
	
	
}

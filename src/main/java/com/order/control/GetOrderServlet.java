package com.order.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cipher.model.WebDataVo;
import com.outherutil.WebUtil;
import com.outherutil.json.JsonDeserializerInterface;
import com.outherutil.json.JsonSerializerInterface;

import redis.clients.jedis.JedisPool;


@WebServlet("/getorder")
public class GetOrderServlet extends HttpServlet implements JsonDeserializerInterface ,JsonSerializerInterface{
	/**
	 *
	 */
	private static final long serialVersionUID = 4918568443458431677L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = (String)req.getParameter("action");
		if(action==null)
			return;

		OrderService oService = new OrderService();
		resp.setContentType("application/json; charset=UTF-8");

		var redis = (JedisPool)getServletContext().getAttribute("redis");

		switch (action) {
		case "getOrders":
			resp.getWriter().write(toJson(oService.getOrders(redis),true));
			break;
		case "getDetail":
			String id = (String)req.getParameter("identity");
			resp.getWriter().write(toJson(oService.getOrderDetail(id)));
			break;
		}
	}
}

package com.order.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cipher.model.WebDataVo;
import com.laiutil.WebUtil;
import com.laiutil.json.JsonDeserializerInterface;
import com.laiutil.json.JsonSerializerInterface;


@WebServlet("/getorder")
public class GetOrderServlet extends HttpServlet implements JsonDeserializerInterface ,JsonSerializerInterface{
	/**
	 *
	 */
	private static final long serialVersionUID = 4918568443458431677L;
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		WebUtil.accessAllallow(arg0, arg1);
		doPost(arg0, arg1);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebDataVo data =  readJsonFromBufferedReader(req.getReader(), WebDataVo.class);
		if(data==null)
			return;
		OrderService oService = new OrderService();
		//TODO Redis
		resp.setContentType("application/json; charset=UTF-8");
		switch (data.getAction()) {
		case "getOrders":
			resp.getWriter().write(toJson(oService.getOrders(),true));
			break;
		case "getDetail":
			resp.getWriter().write(toJson(oService.getOrderDetail(data.getIdentity())));
			break;
		}
	}
}

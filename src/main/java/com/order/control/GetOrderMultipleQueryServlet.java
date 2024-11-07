package com.order.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.outherutil.WebMultipleQueryDto;
import com.outherutil.json.JsonDeserializerInterface;
import com.outherutil.json.JsonSerializerInterface;
@WebServlet("/orderQuery")
public class GetOrderMultipleQueryServlet extends HttpServlet implements JsonSerializerInterface,JsonDeserializerInterface {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		WebMultipleQueryDto wmDto = readJsonFromBufferedReader(req.getReader(), WebMultipleQueryDto.class);
		if(wmDto==null) {
			return;
		}
		OrderService oService = new OrderService();
		resp.setContentType("applecition/json; charset=UTF-8");
		resp.getWriter().write(toJson(oService.getMultipleQuery(wmDto.getQuery(), wmDto.getValue()),true));
	}
}

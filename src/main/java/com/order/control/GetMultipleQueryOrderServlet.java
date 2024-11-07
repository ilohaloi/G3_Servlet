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
public class GetMultipleQueryOrderServlet  extends HttpServlet implements JsonDeserializerInterface ,JsonSerializerInterface{
	/**
	 *
	 */
	private static final long serialVersionUID = 8021523160809414097L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebMultipleQueryDto mqDto = readJsonFromBufferedReader(req.getReader(), WebMultipleQueryDto.class);
		if(mqDto==null)
			return;
		OrderService oService = new OrderService();
		var o =  oService.getMultipleQuery(mqDto.getQueryList(),mqDto.getValueList());
		resp.getWriter().write(toJson(o, true));
	}
}

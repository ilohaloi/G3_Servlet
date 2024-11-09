package com.prod.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.outherutil.WebMultipleQueryDto;
import com.outherutil.json.JsonDeserializerInterface;
import com.outherutil.json.JsonSerializerInterface;

@WebServlet("/prodQuery")
public class GetMultipleQueryProdServlet extends HttpServlet implements JsonSerializerInterface,JsonDeserializerInterface{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebMultipleQueryDto wmDto = readJsonFromBufferedReader(req.getReader(), WebMultipleQueryDto.class);
		if(wmDto==null)
			return;
		ProdService pService = new ProdService();
		var list =  pService.getMultipleQuery(wmDto.getQuery(), wmDto.getValue());

		resp.getWriter().write(toJson(list, false));

	}

}

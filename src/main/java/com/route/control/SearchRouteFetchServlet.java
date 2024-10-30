package com.route.control;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.outherutil.json.JsonDeserializerInterface;
import com.outherutil.json.JsonSerializerInterface;
import com.route.model.RouteDAO;
import com.route.model.RouteVO;

@WebServlet("/searchroutefetchservlet")
public class SearchRouteFetchServlet extends HttpServlet implements JsonDeserializerInterface , JsonSerializerInterface{


		private static final long serialVersionUID = -7148524428976683616L;

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			try {
			// 讓中文不亂碼
			req.setCharacterEncoding("UTF-8");
			var route = readJsonFromBufferedReader(req.getReader(),RouteVO.class);
			//插入
			
			RouteDAO routeDAO = new RouteDAO();
			var routelist =  routeDAO.search(route.getSelectedFilter(),route.getSearchQuery());
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(toJson(routelist,false));
			routelist.forEach(e->{
				System.out.println(e);
			});
			
		    } catch (Exception e) {
		        // 錯誤處理
		        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
		        e.printStackTrace();
		    }
			
			
		}

}

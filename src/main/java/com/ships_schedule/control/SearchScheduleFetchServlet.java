package com.ships_schedule.control;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.outherutil.json.JsonDeserializerInterface;
import com.outherutil.json.JsonSerializerInterface;
import com.route.model.RouteDAO;
import com.route.model.RouteVO;
import com.ships_schedule.model.Ships_scheduleDAO;
import com.ships_schedule.model.Ships_scheduleVO;

@WebServlet("/searchschedulefetchservlet")
public class SearchScheduleFetchServlet extends HttpServlet implements JsonDeserializerInterface , JsonSerializerInterface{


		private static final long serialVersionUID = -7148524428976683616L;

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			// 讓中文不亂碼
			req.setCharacterEncoding("UTF-8");
			var schedule = readJsonFromBufferedReader(req.getReader(),Ships_scheduleVO.class);
			//插入
			
			Ships_scheduleDAO ships_scheduleDAO = new Ships_scheduleDAO();
			var schedulelist =  ships_scheduleDAO.search(schedule.getSelectedFilter(),schedule.getSearchQuery());
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			
			Gson gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd")
					.create();
			
			
			
			resp.getWriter().write(gson.toJson(schedulelist));
			schedulelist.forEach(e->{
				System.out.println(e);
			});
		}

}

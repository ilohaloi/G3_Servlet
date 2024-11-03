package com.ships_schedule.control;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ships_schedule.model.Ships_scheduleDAO;
import com.ships_schedule.model.Ships_scheduleVO;

//對應JS的fetch"http://localhost:8081/journey/schedule"
@WebServlet("/schedule") // 這邊要打js的網址??
public class Ships_scheduleServlet extends HttpServlet {

	@Override

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 跨域請求必要代碼允許那些網域可以連線(*)無限制,(通常用這個)
		resp.setHeader("Access-Control-Allow-Origin", "*");
		// 允許localhost:5500連線可自行更改 : 後的數字,與上面那行2選1即可
//				resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		// 允許以哪種方式請求
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		// 允許哪種頭可以訪問
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

		doGet(req, resp);
	}

	@Override // req是前端請求資料 resp是送出資料給前端
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setCharacterEncoding("UTF-8");// 讓中文不亂碼
		
		resp.setContentType("application/json");
		Ships_scheduleDAO ships_scheduleDAO = new Ships_scheduleDAO();
		List<Ships_scheduleVO> books = ships_scheduleDAO.getAll();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new SqlDateAdapter())
                .create();
		// 報錯代表pom檔沒貼資料

		String jsonString = gson.toJson(books);
		resp.getWriter().write(jsonString);
		System.out.println("資料發送到前端~~");

	}

}

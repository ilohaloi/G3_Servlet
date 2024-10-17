package com.travel_order.control;




import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coupon.myutil.SqlDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ships_schedule.model.Ships_scheduleDAO;
import com.ships_schedule.model.Ships_scheduleVO;
import com.travel_order.model.Travel_orderDAO;
import com.travel_order.model.Travel_orderVO;

@WebServlet("/inserttravel_orderfetchservlet")
public class InsertTravel_orderFetchServlet extends HttpServlet {

	private static final long serialVersionUID = -7148524428976683616L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 跨域請求 必要代碼
		 */
		// 允許那些網域可以連線 (*)無限制
		resp.setHeader("Access-Control-Allow-Origin", "*");
		// 允許localhost:5500連線 可自行更改 : 後的數字
		// resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");

		// 允許以哪種方式請求
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		// 允許哪種頭可以訪問
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

		if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
			resp.setStatus(HttpServletResponse.SC_OK);
			return;

		}
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 讓中文不亂碼
		req.setCharacterEncoding("UTF-8");
		//獲取傳送的 json 資料     body: JSON.stringify({ id: 0, name: name, stock: stock })  insertBookFetch.html 70行
		StringBuilder jsonBuilder = new StringBuilder();
		String line;

		try (BufferedReader br = req.getReader()){
			while ((line = br.readLine()) != null) {
				//把讀取到的字串添加到 jsonBuilder
				jsonBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String json = jsonBuilder.toString();
		System.out.print(json);
        // 自定義的 Gson 實例，註冊 SqlDateAdapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new SqlDateAdapter())
                .create();
		//把最終的json 序列化
		var travel_order =  gson.fromJson(json, Travel_orderVO.class);
		//插入
		Travel_orderDAO travel_orderDAO = new Travel_orderDAO();
		travel_orderDAO.insert(travel_order);
		
	}
}


package com.coupon.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp; // 引入 Timestamp
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coupon.model.Cp;
import com.coupon.model.CpDAOHibernateImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/updateCoupon")
public class UpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 5673675033351078850L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 跨域請求設定
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization"); // 加入 Authorization 或其他需要的標頭

		// 檢查 OPTIONS 請求
		if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
			resp.setStatus(HttpServletResponse.SC_OK);
			return; // 不處理請求，直接返回
		}

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		StringBuilder jsonBuilder = new StringBuilder();
		String line;

		try (BufferedReader br = req.getReader()) {
			while ((line = br.readLine()) != null) {
				jsonBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String json = jsonBuilder.toString();
		System.out.println("Received JSON: " + json);

		GsonBuilder gsonBuilder = new GsonBuilder();

		// 自定義日期解析器  // 使用 DateTimeFormatter 來定義解析格式 yyyy-MM-dd'T'HH:mm
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		gsonBuilder.registerTypeAdapter(Timestamp.class,
				(JsonDeserializer<Timestamp>) (jsonElement, typeOfT, context) -> {
					LocalDateTime dateTime = LocalDateTime.parse(jsonElement.getAsString(), formatter);
					return Timestamp.valueOf(dateTime);  // 將字符串轉換為 LocalDateTime，然後再轉換為 Timestamp
				});

		Gson gson = gsonBuilder.create();

		Cp cp = gson.fromJson(json, Cp.class);
		if (cp == null || cp.getCoup_id() == null) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write("{\"error\": \"無法解析 JSON 資料，cp 為空或 ID 為空\"}");
			return;
		}

		CpDAOHibernateImpl dCpDAOHibernateImpl = new CpDAOHibernateImpl();
		try {
			dCpDAOHibernateImpl.update(cp);
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().write("{\"message\": \"成功修改優惠券！\"}");
			System.out.println("成功修改優惠券");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().write("{\"error\": \"修改優惠券失敗：" + e.getMessage() + "\"}");
			System.out.println("修改優惠券失敗");
		}
	}

}

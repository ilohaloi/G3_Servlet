package com.member.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp; // 引入 Timestamp
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.member.model.MemberJDBC;
import com.member.model.MemberVO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/updateMember")
public class UpdateMemberServlet extends HttpServlet {

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
		Gson gson = gsonBuilder.create();

		MemberVO memberVO = gson.fromJson(json, MemberVO.class);
		if (memberVO == null || memberVO.getId() == null) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write("{\"error\": \"無法解析 JSON 資料，cp 為空或 ID 為空\"}");
			return;
		}

		MemberJDBC memberjdbc = new MemberJDBC();
		try {
			memberjdbc.update(memberVO);
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().write("{\"message\": \"成功修改會員資料！\"}");
			System.out.println("成功修改會員資料");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().write("{\"error\": \"修改會員資料失敗：" + e.getMessage() + "\"}");
			System.out.println("修改會員資料失敗");
		}
	}

}

package com.member.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
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
import com.member.model.MemberJDBC;
import com.member.model.MemberVO;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Random;

@WebServlet("/Register")
public class InsertMemberService extends HttpServlet {

	private static final long serialVersionUID = 5673675033351078850L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 跨域請求設定
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
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
		System.out.println("Received JSON: " + json); // 打印接收到的 JSON

		GsonBuilder gsonBuilder = new GsonBuilder();

		// 自定義日期解析器
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		gsonBuilder.registerTypeAdapter(Timestamp.class,
				(JsonDeserializer<Timestamp>) (jsonElement, typeOfT, context) -> {
					LocalDateTime dateTime = LocalDateTime.parse(jsonElement.getAsString(), formatter);
					return Timestamp.valueOf(dateTime);
				});

		Gson gson = gsonBuilder.create();
		MemberVO memberVO = gson.fromJson(json, MemberVO.class);

		// 在此處確保coup_name是基於自定義規則的隨機碼
		if (cp != null) {
			cp.setCoup_name(generateCustomRandomName());
		}

		// 檢查 cp 是否為 null 及 ID 是否存在
		if (cp == null)
			return;

		MemberJDBC memberJDBC = new MemberJDBC();
		memberJDBC.insert(memberVO);

		// 添加返回的 JSON 響應
		resp.setContentType("application/json"); // 設置響應類型
		resp.getWriter().write("{\"message\": \"新增成功！\"}"); // 返回 JSON 響應
		System.out.println("新增成功");
	}

	// 用於生成基於自定義規則的隨機碼
	private String generateCustomRandomName() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 可用字符
		StringBuilder randomName = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < 10; i++) { // 隨機碼長度為 10
			int index = random.nextInt(characters.length());
			randomName.append(characters.charAt(index));
		}

		return randomName.toString(); // 返回隨機生成的碼
	}
}

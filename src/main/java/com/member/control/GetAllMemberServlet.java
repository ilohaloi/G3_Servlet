package com.member.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.member.model.MemberJDBC;
import com.member.model.MemberVO;


@WebServlet("/getMember")
public class GetAllMemberServlet extends HttpServlet {

	private static final long serialVersionUID = -5337326634130632679L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

		doGet(req, resp);
	}

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        MemberJDBC memberJDBC = new MemberJDBC();
        List<MemberVO> mm = memberJDBC.getAll();

        // 使用 GsonBuilder 來處理日期格式
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd") // 指定日期格式
                .create();

        String jsonString = gson.toJson(mm);
        resp.getWriter().write(jsonString);
        System.out.println("資料已成功發送到前端囉~~");
    }
}

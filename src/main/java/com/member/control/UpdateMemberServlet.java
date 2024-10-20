package com.member.control;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.member.model.MemberJDBC;
import com.member.model.MemberVO;

@WebServlet("/updateMember")
public class UpdateMemberServlet extends HttpServlet {

    private static final long serialVersionUID = 5673675033351078850L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 跨域請求設定
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // 檢查 OPTIONS 請求
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return; // 不處理請求，直接返回
        }

        // 呼叫父類別的 service 方法，將控制交給 doPost()
        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        StringBuilder jsonBuilder = new StringBuilder();
        String line;

        try (BufferedReader br = req.getReader()) {
            while ((line = br.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"無法讀取請求資料\"}");
            e.printStackTrace();
            return;
        }

        String json = jsonBuilder.toString();
        System.out.println("Received JSON: " + json);

        Gson gson = new GsonBuilder().create();

        MemberVO memberVO = gson.fromJson(json, MemberVO.class);
        if (memberVO == null || memberVO.getId() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"無法解析 JSON 資料，email 為空或無效\"}");
            return;
        }

        MemberJDBC memberjdbc = new MemberJDBC();
        try {
            memberjdbc.update(memberVO);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"成功修改會員資料！\"}");
            System.out.println("成功修改會員資料");
        } catch (RuntimeException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"修改會員資料失敗，請稍後再試\"}");
            System.out.println("修改會員資料失敗");
        }
    }
}

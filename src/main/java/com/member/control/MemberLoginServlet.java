package com.member.control;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.member.model.MemberJDBC;
import com.member.model.MemberVO;

@WebServlet("/MemberLogin")
public class MemberLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 5673675033351078850L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 跨域請求設定
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // 處理 OPTIONS 預檢請求
        if (req.getMethod().equalsIgnoreCase("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 設置請求與響應的編碼格式
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        // 讀取 JSON 資料
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"無效的 JSON 格式。\"}");
            return;
        }

        String json = jsonBuilder.toString();

        // 將 JSON 轉換為 MemberVO 物件
        Gson gson = new Gson();
        MemberVO memberVO;
        try {
            memberVO = gson.fromJson(json, MemberVO.class);
        } catch (JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"無效的 JSON 格式。\"}");
            return;
        }

        // 驗證會員資料
        MemberJDBC memberJDBC = new MemberJDBC();
        MemberVO dbMember;

        try {
            dbMember = memberJDBC.findByEmail(memberVO.getEmail());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"伺服器錯誤，請稍後再試。\"}");
            return;
        }
        if (dbMember.getPassword().equals(memberVO.getPassword())){
            // 登入成功，建立新會話
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            session = req.getSession(true);
            session.setAttribute("member", dbMember);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"success\": \"登入成功。\"}");
        } else {
            // 登入失敗
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\": \"登入失敗，電子郵件或密碼錯誤。\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 處理 GET 請求的代碼
    }
}

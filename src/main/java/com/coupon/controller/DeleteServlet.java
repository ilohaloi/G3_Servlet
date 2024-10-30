package com.coupon.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coupon.model.CpDAOHibernateImpl;

@WebServlet("/deleteCoupon/*") // 使用 /* 來接收路徑參數
public class DeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 跨域請求設定
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // 確保處理 OPTIONS 請求
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 呼叫 doDelete 方法
        doDelete(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 獲取 URL 中的優惠券 ID
        String idParam = req.getPathInfo(); // 獲取路徑中的 ID 部分
        if (idParam == null || idParam.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"無法找到要刪除的優惠券 ID\"}");
            return;
        }

        // 解析 ID
        int coup_id;
        try {
            coup_id = Integer.parseInt(idParam.substring(1)); // 去掉開頭的斜杠
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"無效的優惠券 ID\"}");
            return;
        }

        CpDAOHibernateImpl couponDAO = new CpDAOHibernateImpl();
        int deleteStatus = couponDAO.delete(coup_id);

        if (deleteStatus == 1) {
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\": \"刪除成功！\"}");
            System.out.println("刪除成功");
        } else if (deleteStatus == 0) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"找不到該優惠券\"}");
            System.out.println("找不到要刪除的");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"刪除過程中發生錯誤\"}");
            System.out.println("刪除過程中發生錯誤");
        }
    }
}

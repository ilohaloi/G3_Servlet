package com.coupon.controller;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coupon.model.Cp;
import com.coupon.model.CpDAOHibernateImpl;
import com.google.gson.Gson;

@WebServlet("/deleteCoupon")
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

        doDelete(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        StringBuilder jsonBuilder = new StringBuilder();
        String line;

        try (BufferedReader br = req.getReader()) {
            while ((line = br.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"伺服器讀取請求內容時出現錯誤\"}");
            return;
        }

        String json = jsonBuilder.toString();
        System.out.println("Received JSON for deletion: " + json);

        Gson gson = new Gson();
        Cp request = null;

        try {
            request = gson.fromJson(json, Cp.class);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"無法解析 JSON 資料\"}");
            return;
        }

        // 檢查接收到的 ID
        System.out.println("Received coup_id for deletion: " + (request != null ? request.getCoup_id() : "null"));

        if (request == null || request.getCoup_id() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"無法解析 JSON 資料，coup_id 為空\"}");
            return;
        }

        CpDAOHibernateImpl couponDAO = new CpDAOHibernateImpl();
        int deleteStatus = couponDAO.delete(request.getCoup_id());

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

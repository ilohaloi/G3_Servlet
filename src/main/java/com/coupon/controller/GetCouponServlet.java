package com.coupon.controller;

import java.io.IOException;
import java.sql.Timestamp; // 使用 Timestamp
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.coupon.model.Cp;
import com.coupon.model.CpDAO;
import com.coupon.model.CpDAOHibernateImpl;
import com.coupon.myutil.HibernateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

@WebServlet("/getCoupon")
public class GetCouponServlet extends HttpServlet {
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
        HibernateUtil.getSessionFactory();
        resp.setContentType("application/json");
        
        CpDAO cpDAO = new CpDAOHibernateImpl();
        List<Cp> coupon = cpDAO.getAll();
        
        // 使用 GsonBuilder 進行自定義序列化
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Timestamp.class, (JsonSerializer<Timestamp>) (src, typeOfSrc, context) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            String formattedDate = sdf.format(src);
            return new JsonPrimitive(formattedDate);
        });
        
        Gson gson = gsonBuilder.create();
        String jsonString = gson.toJson(coupon);
        
        resp.getWriter().write(jsonString);
        System.out.println("資料已成功發送到前端囉~~");
//        System.out.println(jsonString);
    }
}

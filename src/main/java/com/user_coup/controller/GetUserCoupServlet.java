package com.user_coup.controller;

import java.io.IOException;
import java.sql.Timestamp; // 使用 Timestamp
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.coupon.myutil.HibernateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.user_coup.model.UserCoupDAO;
import com.user_coup.model.UserCoupDAOHibernateImpl;
import com.user_coup.model.UserCoupon;

@WebServlet("/getUserCoup")
public class GetUserCoupServlet extends HttpServlet {
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
        

        UserCoupDAO userCoupDAO = new UserCoupDAOHibernateImpl();
		List<UserCoupon> uc = userCoupDAO.getAll();
        
		uc.forEach(e->{
			int id = e.getCp().getCoup_id();
			e.setCoup_id(id);
		});
		
		
        // 使用 GsonBuilder 進行自定義序列化
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        gsonBuilder.registerTypeAdapter(Timestamp.class, (JsonSerializer<Timestamp>) (src, typeOfSrc, context) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            String formattedDate = sdf.format(src);
            return new JsonPrimitive(formattedDate);
        });
        
        Gson gson = gsonBuilder.create();
        String jsonString = gson.toJson(uc);
        
        resp.getWriter().write(jsonString);
        System.out.println("資料已成功發送到前端囉~~");
//        System.out.println(jsonString);
    }
}

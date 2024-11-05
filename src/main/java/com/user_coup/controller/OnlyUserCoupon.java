package com.user_coup.controller;

import java.awt.image.ReplicateScaleFilter;
import java.beans.Expression;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coupon.model.CpDAOHibernateImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.outherutil.json.JsonSerializerInterface;
import com.user_coup.dto.WebMembCoupDto;
import com.user_coup.model.UserCoupDAO;
import com.user_coup.model.UserCoupDAOHibernateImpl;
import com.user_coup.model.UserCoupon;

@WebServlet("/CouponsOwned")
public class OnlyUserCoupon extends HttpServlet implements JsonSerializerInterface{
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

		// 接收查詢參數
		String memberIdParam = req.getParameter("membId");
		String couponIdParam = req.getParameter("couponId");
		String issueDateStartParam = req.getParameter("issueDateStart");
		String issueDateEndParam = req.getParameter("issueDateEnd");
		String isUsedParam = req.getParameter("isUsed");

		// 輸出接收到的參數，用於調試
		System.out.println("接收到的 memberId: " + memberIdParam);
		System.out.println("接收到的 couponId: " + couponIdParam);
		System.out.println("接收到的 issueDateStart: " + issueDateStartParam);
		System.out.println("接收到的 issueDateEnd: " + issueDateEndParam);
		System.out.println("接收到的 isUsed: " + isUsedParam);

		try {
			// 將參數轉換為適當的類型
			Integer memberId = memberIdParam != null ? Integer.parseInt(memberIdParam) : null;
			Integer couponId = couponIdParam != null ? Integer.parseInt(couponIdParam) : null;
			Timestamp issueDateStart = issueDateStartParam != null
					? Timestamp.valueOf(issueDateStartParam + " 00:00:00")
					: null;
			Timestamp issueDateEnd = issueDateEndParam != null ? Timestamp.valueOf(issueDateEndParam + " 23:59:59")
					: null;
			Boolean isUsed = isUsedParam != null ? "1".equals(isUsedParam) : null;

			// 調用 DAO 方法，傳入查詢條件
			UserCoupDAO userCoupDAO = new UserCoupDAOHibernateImpl();
			List<UserCoupon> userCoupons = userCoupDAO.getCoupons(memberId, couponId, issueDateStart, issueDateEnd,
					isUsed);

			// 使用 Gson 序列化結果
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Timestamp.class, (JsonSerializer<Timestamp>) (src, typeOfSrc, context) -> {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
				return new JsonPrimitive(sdf.format(src));
			});

			Gson gson = gsonBuilder.create();
			String jsonString = gson.toJson(userCoupons);
			CpDAOHibernateImpl cpDao = new CpDAOHibernateImpl();
			var coupTypes = cpDao.getAll();
			List<WebMembCoupDto> wmDto = new ArrayList<WebMembCoupDto>();


			for (int i = 0; i < userCoupons.size(); i++) {

				if (userCoupons.get(i).getCoup_id()==coupTypes.get(i).getCoup_id()) {
					int id = userCoupons.get(i).getCoup_id();
					String coupName = coupTypes.get(i).getCoup_description();
					Timestamp expirDate = userCoupons.get(i).getCoup_expiry_date();
					double discpunt = coupTypes.get(i).getCoup_discount();
					wmDto.add(new WebMembCoupDto(id,coupName,expirDate,discpunt));
				}
			}

			// 如果結果為空，返回空陣列
			//原版
			//resp.getWriter().write(userCoupons.isEmpty() ? "[]" : jsonString);

			resp.getWriter().write(toJson(wmDto, false));


		} catch (NumberFormatException e) {
			System.err.println("參數格式錯誤: " + e.getMessage());
			resp.getWriter().write("{\"error\":\"參數格式錯誤\"}");
		} catch (Exception e) {
			System.err.println("伺服器錯誤：" + e.getMessage());
			resp.getWriter().write("{\"error\":\"伺服器錯誤，請稍後再試\"}");
		}
	}
}

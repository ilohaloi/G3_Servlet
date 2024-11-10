package com.coupon.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
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
import com.member.model.MemberJDBC;
import com.user_coup.model.UserCoupDAOHibernateImpl;
import com.user_coup.model.UserCoupon;

@WebServlet("/couponUpdate")
public class UpdateUserCouponServlet extends HttpServlet {
	private static final long serialVersionUID = -8404617440453953905L;
	private CpDAO cpDao = new CpDAOHibernateImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write("This URL only supports POST requests for updating coupon data.");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		try {
			// 取得參數並檢查是否為空
			String coupIdStr = req.getParameter("coup_id");
			String issueDateStr = req.getParameter("issueDate");
			String expiryDateStr = req.getParameter("expiryDate");

			if (coupIdStr == null || coupIdStr.trim().isEmpty() || issueDateStr == null || issueDateStr.trim().isEmpty()
					|| expiryDateStr == null || expiryDateStr.trim().isEmpty()) {

				resp.getWriter().write("{\"status\":\"error\", \"message\":\"參數缺失或無效\"}");
				return;
			}

			// 解析參數
			Integer coupID = Integer.parseInt(coupIdStr);

			// 使用 SimpleDateFormat 解析不帶秒數的日期格式
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Timestamp issueDate = new Timestamp(dateFormat.parse(issueDateStr).getTime());
			Timestamp expiryDate = new Timestamp(dateFormat.parse(expiryDateStr).getTime());

			// 使用 CpDAO 的 findByCoupId 來查找基於優惠券 ID (coup_id) 的記錄
			List<Cp> coupons = cpDao.findByCoupId(coupID);

			if (coupons != null && !coupons.isEmpty()) {
				Cp coupon = coupons.get(0); // 假設只取第一個匹配的優惠券

				MemberJDBC mJdbc = new MemberJDBC();
				var membList = mJdbc.getAll();

				UserCoupDAOHibernateImpl ucDao = new UserCoupDAOHibernateImpl();
				for (int i = 0; i < membList.size(); i++) {
					UserCoupon ucc = new UserCoupon();

					ucc.setMemb_id(membList.get(i).getId());
					ucc.setCp(coupon);
					ucc.setCoup_issue_date(issueDate);
					ucc.setCoup_expiry_date(expiryDate);
					ucc.setCoup_is_used(0);
					ucDao.add(ucc);
				}
				resp.getWriter().write("{\"status\":\"send\", \"message\":\"已發放優惠券\"}");
			} else {
				resp.getWriter().write("{\"status\":\"not_found\", \"message\":\"找不到該優惠券\"}");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			resp.getWriter().write("{\"status\":\"error\", \"message\":\"無效的優惠券 ID 格式\"}");
		} catch (ParseException e) {
			e.printStackTrace();
			resp.getWriter().write("{\"status\":\"error\", \"message\":\"日期格式錯誤\"}");
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write("{\"status\":\"error\", \"message\":\"更新過程中發生錯誤\"}");
		}
	}
}

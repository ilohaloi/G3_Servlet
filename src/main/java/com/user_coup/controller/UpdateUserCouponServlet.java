package com.user_coup.controller;

import java.io.IOException;
import java.nio.file.attribute.UserDefinedFileAttributeView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.user_coup.model.UserCoupDAOHibernateImpl;

@WebServlet("/cuponUpdate")
public class UpdateUserCouponServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		var couponID  = (Integer)req.getAttribute("couponId");

		if(couponID==null)
			return;

		UserCoupDAOHibernateImpl uDao = new UserCoupDAOHibernateImpl();
		uDao.updateCouponUsed(couponID);
		System.out.println("更新成功");
	}
}

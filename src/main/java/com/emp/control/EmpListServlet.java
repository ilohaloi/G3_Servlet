package com.emp.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.outherutil.WebUtil;
import com.outherutil.json.JsonSerializerInterface;



@WebServlet("/getEmpData")
public class EmpListServlet extends HttpServlet implements JsonSerializerInterface{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		EmpService eService = new EmpService();
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		resp.getWriter().write(toJson(eService.getAllEmpData(),false));
		System.out.println();

	}
}

package com.chat.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChatServlet extends HttpServlet {
	//跨域貼這
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 跨域請求 必要代碼
		 */
		/* 允許那些網域可以連線 (*)無限制 */
		resp.setHeader("Access-Control-Allow-Origin", "*");
		/* 允許localhost:5500連線 可自行更改 : 後的數字 */
		// resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");

		/* 允許以哪種方式請求 */
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		/* 允許哪種頭可以訪問 */
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

		doGet(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String userName = req.getParameter("userName");
		
		req.setAttribute("userName", userName);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/chat.jsp");
		dispatcher.forward(req, res);
	}
}

package com.chat.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChatServlet extends HttpServlet {
	//���K�o
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * ���ШD ���n�N�X
		 */
		/* ���\���Ǻ���i�H�s�u (*)�L���� */
		resp.setHeader("Access-Control-Allow-Origin", "*");
		/* ���\localhost:5500�s�u �i�ۦ��� : �᪺�Ʀr */
		// resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");

		/* ���\�H���ؤ覡�ШD */
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		/* ���\�����Y�i�H�X�� */
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

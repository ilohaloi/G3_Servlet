package com.chat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChatMember")
public class ChatMemberServlet extends HttpServlet{
	
	//從 Session 中取得會員 ID
	//使用者登入後，會員 ID 已經被存入 Session。
	//透過 Servlet 讀取 Session，將會員 ID 傳遞給聊天室前端。
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException{
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			
			try(PrintWriter out = res.getWriter()){
				HttpSession session = req.getSession(false);
				//下面這行改成redis版
				
				Integer memb_id = (Integer) session.getAttribute("memb_id");
				
				//
				if(memb_id != null) {
					out.print("{\"memb_id\": "+ memb_id + "}");
				} else {
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                out.print("{\"error\": \"Member not logged in\"}");
				}
				}
			}
}
			
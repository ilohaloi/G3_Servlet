package com.chat.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chat.model.ChatDAO;
import com.chat.model.ChatVO;
import com.google.gson.Gson;

@WebServlet("/api/chat/history")
public class GetAllChatServlet extends HttpServlet {
	
	private static final long serialVersionUID = -5337326634130632679L;
	
	private ChatDAO dao = new ChatDAO();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		ChatDAO chatDAO = new ChatDAO();
		List<ChatVO> chatList = chatDAO.getAll();
		Gson gson = new Gson();
		String jsonString  = gson.toJson(chatList);
		resp.getWriter().write(jsonString);
		
		System.out.println("資料已成功發送到前端囉~~");

	}

}

	
	
	
	
	
//	@Override
//	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		String userName = req.getParameter("userName");
//		
//		req.setAttribute("userName", userName);
//		
//		RequestDispatcher dispatcher = req.getRequestDispatcher("/chat.jsp");
//		dispatcher.forward(req, res);
//	}
//}


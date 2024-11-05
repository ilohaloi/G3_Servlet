package com.route.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.outherutil.json.JsonSerializerInterface;
import com.route.model.RouteDAO;
import com.route.model.RouteVO;

//對應JS的fetch"http://localhost:8081/journey/route"
@WebServlet("/route") // 注意不可重複連接
public class GetRouteServlet extends HttpServlet implements JsonSerializerInterface {

//	@Override
//	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//		// 跨域請求必要代碼允許那些網域可以連線(*)無限制
//		resp.setHeader("Access-Control-Allow-Origin", "*");
//		// 允許localhost:5500連線 可自行更改 : 後的數字
////			resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
//		// 允許以哪種方式請求
//		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
//		// 允許哪種頭可以訪問
//		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
//
//		doGet(req, resp);
//	}

	@Override // req是前端請求資料 resp是送出資料給前端
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setCharacterEncoding("UTF-8");// 讓中文不亂碼
//			HibernateUtil.getSessionFactory();//報錯是因為沒有檔案
		resp.setContentType("application/json");
		RouteDAO routeDAO = new RouteDAO();

		List<RouteVO> books = routeDAO.getAll();
		System.out.println(books);

		resp.getWriter().write(toJson(books, false));
		System.out.println("資料已成功發送到前端囉~~");

	}

}

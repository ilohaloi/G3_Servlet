package com.prod.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cipher.model.WebDataVo;
import com.outherutil.json.JsonDeserializerInterface;
import com.outherutil.json.JsonSerializerInterface;

import redis.clients.jedis.JedisPool;
@WebServlet("/prodget")
public class ProdGetServlet extends HttpServlet implements JsonDeserializerInterface,JsonSerializerInterface{

	/**
	 *
	 */
	private static final long serialVersionUID = 2559350853892450921L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = (String)req.getParameter("action");
		if(action==null)
			return;

		try {
			ProdService pService = new ProdService();
			JedisPool pool = (JedisPool)getServletContext().getAttribute("redis");
			resp.setContentType("application/json; charset=UTF-8");
			switch (action) {
			case "getAllprod":
				resp.getWriter().write(toJson(pService.getProds(pool),false));
				break;
			case "getProd":
				String id = (String)req.getParameter("identity");
				resp.getWriter().write(toJson(pService.getProd(pool,id),false));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

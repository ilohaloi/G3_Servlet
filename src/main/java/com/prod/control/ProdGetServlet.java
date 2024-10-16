package com.prod.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cipher.model.WebDataVo;
import com.laiutil.WebUtil;
import com.laiutil.json.JsonDeserializerInterface;
import com.laiutil.json.JsonSerializerInterface;

import redis.clients.jedis.JedisPool;
@WebServlet("/prodget")
public class ProdGetServlet extends HttpServlet implements JsonDeserializerInterface,JsonSerializerInterface{

	/**
	 *
	 */
	private static final long serialVersionUID = 2559350853892450921L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebUtil.accessAllallow(req, resp);
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		WebDataVo webData = readJsonFromBufferedReader(req.getReader(),WebDataVo.class);
		if(webData==null)
			return;

		try {
			ProdService pService = new ProdService();
			JedisPool pool = (JedisPool)getServletContext().getAttribute("redis");
			switch (webData.getAction()) {

			case "getAllprod":
				resp.getWriter().write(toJson(pService.getProds(pool),false));
				break;

			case "getProd":
				resp.getWriter().write(toJson(pService.getProd(pool,webData.getIdentity()),false));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

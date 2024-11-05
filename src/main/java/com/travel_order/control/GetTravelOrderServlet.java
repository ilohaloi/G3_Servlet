package com.travel_order.control;

import java.awt.Adjustable;
import java.io.IOException;
import java.rmi.server.RMIClassLoaderSpi;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.outherutil.json.JsonSerializerInterface;
import com.travel_order.model.Travel_orderDAO;
import com.travel_order.model.Travel_orderVO;
@WebServlet("/getOrder")
public class GetTravelOrderServlet extends HttpServlet implements JsonSerializerInterface{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var membid = (String)req.getParameter("membId");

		Travel_orderDAO oDao = new Travel_orderDAO();





		var order =  oDao.findByPrimaryKey(Integer.valueOf(membid));

		List<Travel_orderVO>ar  = new ArrayList<Travel_orderVO>();
		ar.add(order);
		resp.getWriter().write(toJson(ar,false));
	}
}

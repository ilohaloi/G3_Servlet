package com.order.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.order.model.WebOrder;
import com.outherutil.WebUtil;
import com.outherutil.json.JsonDeserializerInterface;


@WebServlet("/insertorder")
public class InsertOrderServlet extends HttpServlet implements JsonDeserializerInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = -1785361935994201768L;

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		WebUtil.accessAllallow(arg0, arg1);
		doPost(arg0, arg1);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebOrder webData = readJsonFromBufferedReader(req.getReader(), WebOrder.class);
		if(webData==null)
			return;
		OrderService oService = new OrderService();
		oService.insertOrder(webData);

	}
}

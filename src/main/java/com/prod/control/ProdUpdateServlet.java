package com.prod.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.outherutil.WebUtil;
import com.outherutil.json.JsonDeserializerInterface;
import com.prod.model.ProdVo;


@WebServlet("/produpdate")
public class ProdUpdateServlet extends HttpServlet implements JsonDeserializerInterface{
	/**
	 *
	 */
	private static final long serialVersionUID = 7430052710171805834L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			var prod = readJsonFromBufferedReader(req.getReader(), ProdVo.class);
			if(prod==null)
				return;
			ProdService pService = new ProdService();
			pService.update(prod);
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

	}
}

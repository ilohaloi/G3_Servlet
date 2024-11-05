package com.prod.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.outherutil.json.JsonSerializerInterface;
import com.prod.model.ProdVo;

@WebServlet("/prodinsert")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,
	    maxFileSize = 1024 * 1024 * 10,
	    maxRequestSize = 1024 * 1024 * 30
	)
public class ProdInsertServlet extends HttpServlet implements JsonSerializerInterface {
	/**
	 *
	 */
	private static final long serialVersionUID = -3693841330979040890L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
		ProdService pService = new ProdService();
			ProdVo prod = new ProdVo(req);
			pService.uploadImgs(req.getParts(), prod);
			pService.insert(prod);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setContentType("application/json");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write(createJsonKvObject("error","上傳失敗"));
		}

	}
}

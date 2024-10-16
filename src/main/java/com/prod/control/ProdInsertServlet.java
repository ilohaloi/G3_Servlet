package com.prod.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laiutil.WebUtil;
import com.laiutil.json.JsonSerializerInterface;
import com.prod.model.ProdVo;

@WebServlet("/prodinsert")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
	    maxFileSize = 1024 * 1024 * 10,      // 10MB
	    maxRequestSize = 1024 * 1024 * 30    // 50MB
	)
public class ProdInsertServlet extends HttpServlet implements JsonSerializerInterface {
	/**
	 *
	 */
	private static final long serialVersionUID = -3693841330979040890L;
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {

		WebUtil.accessAllallow(arg0, arg1);
		doPost(arg0, arg1);

	}
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

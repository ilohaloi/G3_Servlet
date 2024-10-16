package com.cipher.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cipher.model.KeyFormatInterface;
import com.cipher.model.KeyGenerateInterface;
import com.laiutil.WebUtil;
import com.laiutil.json.JsonSerializerInterface;



@WebServlet("/createtempaeskey")
public class CreateTempAesKeyServlet extends HttpServlet implements KeyGenerateInterface,KeyFormatInterface,JsonSerializerInterface{
	/**
	 *
	 */
	private static final long serialVersionUID = -5628461626877694871L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebUtil.accessAllallow(req, resp);
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		SecretKey key = getAesKey()!=null?getAesKey():null;
		Map<String,String> n = new HashMap<String, String>();
		System.out.println(getBase64FromKey(key));
		n.put("key", getBase64FromKey(key));
		String jsonString = createJsonKvObject(n);
		System.out.println("獲取key了");
		resp.setContentType("application/json");
		resp.getWriter().write(jsonString);
	}
}

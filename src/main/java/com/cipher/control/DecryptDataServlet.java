package com.cipher.control;

import java.io.IOException;
import java.security.PrivateKey;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cipher.model.CipherInterface;
import com.cipher.model.KeyFormatInterface;
import com.cipher.model.KeyGenerateInterface;
import com.cipher.model.WebDataVo;
import com.emp.control.EmpService;
import com.emp.model.EmpVo;
import com.outherutil.Tuple;
import com.outherutil.json.JsonDeserializerInterface;
import com.outherutil.json.JsonSerializerInterface;

import redis.clients.jedis.JedisPool;

@WebServlet("/decryptdata")
public class DecryptDataServlet extends HttpServlet
		implements JsonDeserializerInterface, KeyFormatInterface, KeyGenerateInterface, CipherInterface ,JsonSerializerInterface{

	private static final long serialVersionUID = -1390169436743831857L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		WebDataVo data =readJsonFromBufferedReader(req.getReader(), WebDataVo.class);
		if(data==null)
			return;

		try {
			String decryptData = null;
			switch (data.getAction()) {
			case "empReg":
				decryptData = decrypt(data.getData(),getAesKeyFromBase64(data.getKey()),AES);
				EmpVo empData = jsonToObject(decryptData,EmpVo.class);
				req.setAttribute("registerData", empData);
				req.getRequestDispatcher("/empregister").forward(req, resp);
				break;
			case"empLogin":
				Tuple<String, String, PrivateKey> loginData = new Tuple<String, String, PrivateKey>(data.getIdentity(), data.getData(),null);
				req.setAttribute("loginData", loginData);
				req.getRequestDispatcher("/emplogin").forward(req, resp);
				break;
				//TODO 切割出去
			case"getEmpPubKey":
				EmpService eService = new EmpService();
				try {
					String base64PubKey = eService.getEmpBase64PubKey((JedisPool)getServletContext().getAttribute("redis"), data.getData());
					resp.getWriter().write(createJsonKvObject("key", base64PubKey));
				} catch (Exception e) {
					resp.getWriter().write(createJsonKvObject("info","無此帳號"));
				}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

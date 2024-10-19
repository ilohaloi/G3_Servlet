package com.emp.control;

import java.io.IOException;
import java.security.KeyPair;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bettercloud.vault.Vault;
import com.cipher.model.KeyGenerateInterface;
import com.emp.model.EmpVo;
import com.outherutil.json.JsonSerializerInterface;

import redis.clients.jedis.JedisPool;


@WebServlet("/empregister")
public class EmpRegisterServlet extends HttpServlet implements KeyGenerateInterface,JsonSerializerInterface{
	/**
	 *
	 */
	private static final long serialVersionUID = -31362297204700304L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{

		EmpVo emp = (EmpVo)req.getAttribute("registerData");
		EmpService eService = new EmpService();
		KeyPair key = getRsakey();
		if(emp==null)
			return;

		//dev 開發環境用
		Vault vault = (Vault)getServletContext().getAttribute("vault")==null?null:(Vault)getServletContext().getAttribute("vault");
		if(vault==null)
			return;

		resp.setContentType("application/json; charset=UTF-8");
		//TODO 表層代碼精簡化
		String eString = new String();
		if(!eService.formatCheck(emp,eString)) {
			resp.getWriter().write(createJsonKvObject("info", eString,"color", "red"));
		}
		else if(eService.isAccountDuplication(emp)){
			resp.getWriter().write(createJsonKvObject("info", "帳號重複","color", "red"));
			return;
		}
		else if(!eService.insertEmp(emp, key.getPrivate(),vault , "keys/empKey")) {
			resp.getWriter().write(createJsonKvObject("info", "稍後在試 v","color", "red"));
			return;
		}
		else if(!eService.insertEmp(emp, key.getPublic())) {
			resp.getWriter().write(createJsonKvObject("info", "稍後在試 d","color", "red"));
			return;
		}
		else if(!eService.insertEmp((JedisPool)getServletContext().getAttribute("redis"), emp, key.getPublic())) {
			resp.getWriter().write(createJsonKvObject("info", "稍後在試 j","color", "red"));
			return;
		}else {
			resp.getWriter().write(createJsonKvObject("info", "創建成功","color","green","data", "test"));
		}
	}
}
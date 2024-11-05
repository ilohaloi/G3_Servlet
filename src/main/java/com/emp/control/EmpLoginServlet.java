package com.emp.control;

import java.io.IOException;
import java.security.PrivateKey;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.bettercloud.vault.Vault;
import com.otherutil.vault.VaultFuntion;
import com.outherutil.Tuple;
import com.outherutil.json.JsonSerializerInterface;

import kotlin.Pair;
import redis.clients.jedis.JedisPool;

@WebServlet("/emplogin")
public class EmpLoginServlet extends HttpServlet implements JsonSerializerInterface {
	/**
	 *
	 */
	private static final long serialVersionUID = -2334079218065657608L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// dev 開發環境用
		Vault vault = (Vault) getServletContext().getAttribute("vault") == null ? null: (Vault) getServletContext().getAttribute("vault");
		if (vault == null)
			return;

		// 帳號 密碼 私鑰
		Tuple<String, String, PrivateKey> emp = (Tuple<String, String, PrivateKey>) req.getAttribute("loginData");
		VaultFuntion vFuntion = new VaultFuntion(vault, "keys/empKey");
		EmpService eService = new EmpService();

		if (!eService.login(emp, vFuntion.getAllData())) {
			resp.setContentType("application/json; charset=UTF-8");
			resp.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
			resp.getWriter().write(createJsonKvObject("info", "帳號密碼輸入錯誤"));
		} else {
			HttpSession session = req.getSession();
			eService.insertLoginAuth((JedisPool)getServletContext().getAttribute("redis"), emp.getK(),session.getId());
			resp.getWriter().write(createJsonKvObject("token",session.getId()));
		}

	}
}

package com.laiutil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil{
	public static void accessAllallow(HttpServletRequest req, HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
//		if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
//			resp.setStatus(HttpServletResponse.SC_OK);
//			return;
//		}
	}
}

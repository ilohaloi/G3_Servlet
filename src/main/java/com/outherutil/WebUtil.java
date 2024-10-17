package com.outherutil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil{
	public static void accessAllallow(HttpServletRequest req, HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS,PUT");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
	}
}

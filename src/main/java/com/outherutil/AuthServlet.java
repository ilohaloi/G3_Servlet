package com.outherutil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/blank")
public class AuthServlet extends HttpServlet {
		/**
	 *
	 */
	private static final long serialVersionUID = -3138149323414415053L;

		@Override
		protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			return;
		}
}

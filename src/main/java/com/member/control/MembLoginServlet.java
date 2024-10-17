package com.member.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.outherutil.json.JsonDeserializerInterface;

@WebServlet("/memblogin")
public class MembLoginServlet extends HttpServlet implements  JsonDeserializerInterface {

	private static final long serialVersionUID = -2010470964896900381L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("email");
		String password = req.getParameter("password");
		System.out.println(username);
		System.out.println(password);
		String auth = "poko5488";

		if(password.equals(auth)) {
			resp.getWriter().write("success");
		}
	}
}




//<form action="http://localhost:8081/TIA103G3_Servlet/memblogin" method="post">
//<label for="username">帳號</label>
//<input type="text" id="username" name="username">
//<label for="username">密碼</label>
//<input type="password" id="password" name="password">
//<button type="submit">login</button>
//</form>


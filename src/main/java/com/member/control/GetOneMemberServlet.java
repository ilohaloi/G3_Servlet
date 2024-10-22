package com.member.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.member.model.MemberJDBC;
import com.member.model.MemberVO;

@WebServlet("/getOneMember")
public class GetOneMemberServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private MemberJDBC memberJDBC = new MemberJDBC();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set CORS headers
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Set response content type
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        // Get email parameter from the request
        String email = req.getParameter("email");
        if (email == null || email.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Email parameter is missing\"}");
            return;
        }

        // Find member by email
        MemberVO member = memberJDBC.findByEmail(email);

        // If member not found, return error message
        if (member == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Member not found\"}");
            return;
        }

        // Use Gson to convert member object to JSON
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String jsonResponse = gson.toJson(member);

        // Send the JSON response
        resp.getWriter().write(jsonResponse);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}

package com.member.control;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.member.model.MemberJDBC;
import com.member.model.MemberVO;
import com.outherutil.json.JsonSerializerInterface;
import com.outherutil.redis.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@WebServlet("/MemberLoginCheck")
public class MemberLoginCheckServlet extends HttpServlet implements JsonSerializerInterface {

    private static final long serialVersionUID = 5673675033351078850L;
    private static JedisPool pool = RedisUtil.getPool();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        if (req.getMethod().equalsIgnoreCase("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"無效的 JSON 格式。\"}");
            return;
        }

        String json = jsonBuilder.toString();

        Gson gson = new Gson();
        MemberVO memberVO;
        try {
            memberVO = gson.fromJson(json, MemberVO.class);
        } catch (JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"無效的 JSON 格式。\"}");
            return;
        }

        if (memberVO.getId() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"會員 ID 無效。\"}");
            return;
        }

        try (Jedis jedis = pool.getResource()) {
            String redisKey = "member:" + memberVO.getId();
            if (jedis.exists(redisKey)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(createJsonKvObject("message", "會員已登入"));
                System.out.println(memberVO.getId());
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write(createJsonKvObject("message", "會員尚未登入"));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Redis 服務器錯誤，請稍後再試。\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        response.getWriter().write("{\"\"}");
    }
}

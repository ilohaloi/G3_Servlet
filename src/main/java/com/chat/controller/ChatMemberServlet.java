package com.chat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.member.model.MemberVO;
import com.outherutil.redis.RedisUtil;
import com.outherutil.json.JsonSerializerInterface;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@WebServlet("/ChatMember")
public class ChatMemberServlet extends HttpServlet implements JsonSerializerInterface{
	
	private static JedisPool pool = RedisUtil.getPool();
	
	//從 Session 中取得會員 ID
	//使用者登入後，會員 ID 已經被存入 Session。
	//透過 Servlet 讀取 Session，將會員 ID 傳遞給聊天室前端。
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
			
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		
		// 讀 request 送來的資料
		StringBuilder payload = new StringBuilder();
        String line;
        String member_mail;
        Gson gson = new Gson();
        MemberVO memberVO;	
        
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
            	payload.append(line);
            }

         // 將 JSON 轉換為 MemberVO 物件
            String json_str = payload.toString();
            
            memberVO = gson.fromJson(json_str, MemberVO.class);
            
        } catch (IOException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("{\"error\": \"無效的 JSON 格式。\"}");
            return;
        }catch (JsonSyntaxException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("{\"error\": \"無效的 JSON 格式。\"}");
            return;
        }

        
		try(Jedis jedis = pool.getResource()){
			
			String redisKey = "session:member:" + String.valueOf(memberVO.getId());
			memberVO.setEmail(jedis.get(redisKey));
//			PrintWriter out = res.getWriter();
//			
//			
//			HttpSession session = req.getSession(false);
//			//下面這行改成redis版
//				
//			Integer memb_id = (Integer) session.getAttribute("memb_id");
//				
//			//
//			if(memb_id != null) {
//				out.print("{\"memb_id\": "+ memb_id + "}");
//			} else {
//				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//	               out.print("{\"error\": \"Member not logged in\"}");
//			}
			
		} catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\": \"Redis 服務器錯誤，請稍後再試。\"}");
            return;
        }
		
		res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write(createJsonKvObject("id", String.valueOf(memberVO.getId()), "email", memberVO.getEmail()));

	}
}
			
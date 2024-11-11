package com.chat.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outherutil.redis.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@WebServlet("/api/chat/history")
public class ChatHistoryServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static JedisPool pool = RedisUtil.getPool();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       
        String ID = req.getParameter("id");
        System.out.println("ID:"+ID);
    	
    	Map<String, List<String>> chatHistories = getAllChatHistories(req.getParameter("id"));
        
        // 設置回應格式為 JSON
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        // 使用 ObjectMapper 將 Map 轉為 JSON
        String jsonResponse = objectMapper.writeValueAsString(chatHistories);
        out.print(jsonResponse);
        out.flush();
    }
    //
    //
    private Map<String, List<String>> getAllChatHistories(String ID) {
        Map<String, List<String>> chatHistories = new HashMap<>();

        try (Jedis jedis = pool.getResource()) {
        	jedis.select(5);
        	
        	if(ID == null) {
                // 1. 取得所有 chat:history:* 的鍵
                Set<String> keys = jedis.keys("chat:history:*");
                System.out.println("keys:"+keys);
                // 2. 遍歷每個鍵，取得每個聊天記錄
                for (String key : keys) {
                    List<String> messages = jedis.lrange(key, 0, -1);
                    chatHistories.put(key, messages);
                }
        	}else {
        		System.out.println("ID:"+ID);
        		String key = "chat:history:" + ID;
        		List<String> messages = jedis.lrange(key, 0, -1);
                chatHistories.put(key, messages);
        	}

        }
        return chatHistories;
    }
}

package com.chat.controller;

import java.io.IOException;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.chat.model.WebChatDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.member.model.MemberJDBC;
import com.outherutil.json.JsonDeserializerInterface;
import com.outherutil.json.JsonSerializerInterface;
import com.outherutil.redis.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@ServerEndpoint(value = "/ChatWS/{userId}") //
public class ChatWebSocket implements JsonDeserializerInterface ,JsonSerializerInterface{

	private static ConcurrentHashMap<String, Session> online_list = new ConcurrentHashMap<>();
	private static TreeMap<Integer, Boolean> check_list = new TreeMap<>();
	private static JedisPool pool = RedisUtil.getPool();
	private final Gson gson = new Gson();
	private String userId;

	@OnOpen
	public void onOpen(@PathParam("userId") String userId, Session userSession) throws IOException {
		try (Jedis jedis = pool.getResource()) {
			this.userId = userId;
			userSession.getBasicRemote().sendText("歡迎 " + userId + " 加入聊天室！");
			// 保存新的 Session 到 Redis db(5)
			jedis.select(5);
			if (userId.equals("500")) {
				System.out.println("客服 " + userId + " 已連接");
			} else {
				jedis.set("chat:member:" + userId, userSession.getId());
				System.out.println("用戶 " + userId + " 已連接");
			}
			online_list.put(userId, userSession);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@OnMessage
	public void onMessage(String message, Session userSession) throws IOException {

				
		System.out.println(message);
		
		
		
		Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.create();
		var content = gson.fromJson(message, WebChatDto.class);
		
		//訊息傳送邏輯
	    int receiver_id = 500;

		System.out.println("receiverID:" + receiver_id);
				//500
		Session receiver_sessionSession = online_list.get(receiver_id + "");

		if (receiver_sessionSession != null) {
			
			if (receiver_sessionSession.isOpen()) {
				
					switch (content.getSender()) {
					case "employ":
						Integer targetMemberId = content.getId(); // 假設 WebChatDto 中包含 receiverId 欄位
						 System.out.println("Target Member ID: " + targetMemberId);
					        Session memberSession = online_list.get(String.valueOf(targetMemberId));

					        if (memberSession != null && memberSession.isOpen()) {
					            memberSession.getBasicRemote().sendText(content.getContent());
					            userSession.getBasicRemote().sendText(createJsonKvObject("id","employ",
										"data",content.getContent()));
					            System.out.println("訊息成功傳送給會員 " + targetMemberId);
					        } else {
					        	
					        	
					        	userSession.getBasicRemote().sendText(createJsonKvObject("context","會員 " + targetMemberId + " 目前不在線上。","id","employ",
					        			"data",content.getContent()));
					            
					        }
					        
						break;
					case "member":
						receiver_sessionSession.getBasicRemote().sendText(createJsonKvObject("id","member",
								"data",content.getContent()));
						
						break;
					default:
						break;
					}
					
				
				
				//顧客傳訊息給客服
				receiver_sessionSession.getBasicRemote().sendText(content.getContent());
				System.out.println("isOpen:TRUE");
			} else {
				userSession.getBasicRemote().sendText(receiver_id + " send a massage to you");
				
			}
			
		}		
	
		
		if(!check_list.containsKey(receiver_id)) {
			check_list.put(receiver_id,true);
			userSession.getBasicRemote().sendText( "你的客服" + receiver_id + "不在線上 ^^，他在睡覺歐");
			System.out.println(777);
		}
	
	
		MemberJDBC mb = new MemberJDBC();
		content.setName(mb.findByPK(content.getId()).getName());
		
		
		try (Jedis jedis = pool.getResource()) {
//	        try (Jedis jedis = RedisUtil.getPool().getResource()) {
			jedis.select(5); // 選擇 Redis 的第 5 個資料庫

			// 使用 id 作為鍵，將欄位資料存入 Redis
			jedis.rpush("chat:history:" + content.getId() ,toJson(content, false));
//	            jedis.del("chat:history:" + id);

			System.out.println("訊息已成功儲存到 Redis。");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Object sendMessageToSession(String sessionId, String chatContent) {
		// TODO Auto-generated method stub
		return null;
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.err.println("Error: " + e.getMessage());
		e.printStackTrace();
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
//		String[] url_list = userSession.getRequestURI().toString().split("/");
//		String userId = url_list[url_list.length-1];
		System.out.println("user end:" + userId);
		online_list.remove(userId);
//		try (Jedis jedis = jedisPool.getResource()) {
//			String userName = getUserNameBySession(userSession);
//			if (userName != null) {
//				jedis.del("session:" + userName);
//				System.out.println("User " + userName + " disconnected: " + reason.getCloseCode());
//			}
//		}
	}

	private String getUserNameBySession(Session userSession) {
		try (Jedis jedis = pool.getResource()) {
			for (String key : jedis.keys("session:*")) {
				if (jedis.get(key).equals(userSession.getId())) {
					return key.replace("session:", "");
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}
}
package com.chat.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import com.chat.model.ChatVO;
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@ServerEndpoint(value = "/ChatWS/{userId}") //
public class ChatWebSocket {

	private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
	private static final JedisPool jedisPool = new JedisPool("localhost", 6380);
	private final Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userId") String userId, Session userSession) throws IOException {
		try (Jedis jedis = jedisPool.getResource()) {
			userSession.getBasicRemote().sendText("歡迎 " + userId + " 加入聊天室！");
			// 保存新的 Session 到 Redis db(5)
			jedis.select(5);
			jedis.set("chat:member:" + userId, userSession.getId());
			System.out.println("用戶 " + userId + " 已連接");

			// 載入並發送未讀訊息
			List<String> unreadMessages = jedis.lrange("unread:" + userId, 0, -1);
			for (String message : unreadMessages) {
				userSession.getAsyncRemote().sendText(message);
			}

			// 清空未讀訊息標記
			jedis.del("unread:" + userId);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@OnMessage
	public void onMessage(String message, Session userSession) {
		ChatVO chatMessage = gson.fromJson(message, ChatVO.class);
		System.out.println("訊息" + message);
		System.out.println("session:"+userSession.getId());
//		userSession.getBasicRemote().sendText("歡迎加入聊天室！");
		
		
		
		try (Jedis jedis = jedisPool.getResource()) {
			// 根據 Redis 中的 session 對應來判斷發送者
			String senderName = getUserNameBySession(userSession); // 取得發送者的名稱
			String receiverId;
			int senderId;

			if (String.valueOf(chatMessage.getEmpoId()).equals(senderName)) {
				senderId = chatMessage.getEmpoId(); // 發送者是員工
				receiverId = String.valueOf(chatMessage.getMembId()); // 接收者是會員
			} else {
				senderId = chatMessage.getMembId(); // 發送者是會員
				receiverId = String.valueOf(chatMessage.getEmpoId()); // 接收者是員工
			}

			// 確定對話記錄的 key
			String conversationKey = "chat:" + senderId + "-" + receiverId;

			// 將訊息轉為 JSON 並存入 Redis 對話紀錄
			String chatContent = gson.toJson(chatMessage);
			jedis.rpush(conversationKey, chatContent);

			// 嘗試發送訊息給接收者
			Optional<String> receiverSessionId = Optional.ofNullable(jedis.get("session:" + receiverId));
			receiverSessionId.ifPresentOrElse(sessionId -> sendMessageToSession(sessionId, chatContent),
					() -> jedis.rpush("unread:" + receiverId, chatContent) // 若接收者不在線則保存為未讀
			);
		} catch (Exception exception) {
			exception.printStackTrace();
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
		try (Jedis jedis = jedisPool.getResource()) {
			String userName = getUserNameBySession(userSession);
			if (userName != null) {
				jedis.del("session:" + userName);
				System.out.println("User " + userName + " disconnected: " + reason.getCloseCode());
			}
		}
	}

	private String getUserNameBySession(Session userSession) {
		try (Jedis jedis = jedisPool.getResource()) {
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

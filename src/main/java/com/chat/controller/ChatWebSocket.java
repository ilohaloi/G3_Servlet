package com.chat.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.chat.model.ChatVO;
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@ServerEndpoint("/FriendWS/{userName}")
public class ChatWebSocket {

    private static final JedisPool jedisPool = new JedisPool("localhost", 6380);
    private final Gson gson = new Gson();

    @OnOpen
    public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            // �O�s�s�� Session �� Redis
            jedis.set("session:" + userName, userSession.getId());
            System.out.println("�Τ� " + userName + " �w�s��");

            // ���J�õo�e��Ū�T��
            List<String> unreadMessages = jedis.lrange("unread:" + userName, 0, -1);
            for (String message : unreadMessages) {
                userSession.getAsyncRemote().sendText(message);
            }

            // �M�ť�Ū�T���аO
            jedis.del("unread:" + userName);
        }
    }

    @OnMessage
    public void onMessage(String message, Session userSession) {
        ChatVO chatMessage = gson.fromJson(message, ChatVO.class);

        try (Jedis jedis = jedisPool.getResource()) {
            // �ھ� Redis ���� session �����ӧP�_�o�e��
            String senderName = getUserNameBySession(userSession);  // ���o�o�e�̪��W��
            String receiverId;
            int senderId;

            if (String.valueOf(chatMessage.getEmpoId()).equals(senderName)) {
                senderId = chatMessage.getEmpoId();  // �o�e�̬O���u
                receiverId = String.valueOf(chatMessage.getMembId());  // �����̬O�|��
            } else {
                senderId = chatMessage.getMembId();  // �o�e�̬O�|��
                receiverId = String.valueOf(chatMessage.getEmpoId());  // �����̬O���u
            }

            // �T�w��ܰO���� key
            String conversationKey = "chat:" + senderId + "-" + receiverId;

            // �N�T���ର JSON �æs�J Redis ��ܬ���
            String chatContent = gson.toJson(chatMessage);
            jedis.rpush(conversationKey, chatContent);

            // ���յo�e�T����������
            Optional<String> receiverSessionId = Optional.ofNullable(jedis.get("session:" + receiverId));
            receiverSessionId.ifPresentOrElse(
                sessionId -> sendMessageToSession(sessionId, chatContent),
                () -> jedis.rpush("unread:" + receiverId, chatContent)  // �Y�����̤��b�u�h�O�s����Ū
            );
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
        }
        return null;
    }
}

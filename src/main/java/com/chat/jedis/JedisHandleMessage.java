package com.chat.jedis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleMessage {

    // 使用 Jedis 連線池
    private static JedisPool pool = JedisPoolUtil.getJedisPool();

    // 根據發送者與接收者的 ID 取得歷史訊息
    public static List<String> getHistoryMsg(Integer senderId, Integer receiverId) {
        String key = buildConversationKey(senderId, receiverId); // 建立對話鍵

        try (Jedis jedis = pool.getResource()) {
            return jedis.lrange(key, 0, -1);  // 取得所有歷史訊息
        }
    }

    // 儲存雙方的聊天訊息
    public static void saveChatMessage(Integer senderId, Integer receiverId, String convContent) {
        String senderKey = buildConversationKey(senderId, receiverId);
        String receiverKey = buildConversationKey(receiverId, senderId);

        try (Jedis jedis = pool.getResource()) {
            jedis.rpush(senderKey, convContent);  // 存入發送者的聊天紀錄
            jedis.rpush(receiverKey, convContent);  // 存入接收者的聊天紀錄
        }
    }

    // 建立 Redis 對話鍵，格式為 chat:[senderId]-[receiverId]
    private static String buildConversationKey(Integer senderId, Integer receiverId) {
        return new StringBuilder("chat:")
                .append(senderId).append("-")
                .append(receiverId).toString();
    }
}

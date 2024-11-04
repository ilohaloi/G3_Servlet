package com.chat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@WebServlet("/api/chat/member")
public class GetChatMemberID extends HttpServlet {
    private static final JedisPool jedisPool = new JedisPool("localhost", 6380);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Définir le type de contenu en JSON
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(5);  // Sélectionner la base de données Redis 5

            // Récupérer toutes les clés correspondant à "chat:member:*"
            Set<String> keys = jedis.keys("chat:member:*");

            // Extraire uniquement les userId en supprimant le préfixe "chat:member:"
            Set<String> userIds = keys.stream()
                    .map(key -> key.replace("chat:member:", ""))
                    .collect(Collectors.toSet());

            // Construire une réponse JSON
            String json = "[" + String.join(",", userIds.stream().map(id -> "\"" + id + "\"").collect(Collectors.toList())) + "]";

            // Envoyer la réponse
            PrintWriter out = res.getWriter();
            out.write(json);
        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des userIds.");
        }
    }
}

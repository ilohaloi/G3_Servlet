package com.chat.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chat.model.ChatDAO;
import com.chat.model.ChatVO;

@WebServlet("/insertformchat")
public class AddChatServlet extends HttpServlet {

    private static final long serialVersionUID = 1079698694595143580L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 設定請求的編碼，避免中文亂碼
        req.setCharacterEncoding("UTF-8");

        try {
            // 從請求中取得參數
            String name = req.getParameter("name");
            int empo_id = Integer.parseInt(req.getParameter("empo_id"));
            int memb_id = Integer.parseInt(req.getParameter("memb_id"));
            String conv_content = req.getParameter("conv_content");

            // 取得當前的 Timestamp
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            
            // 建立 ChatVO 物件並設置屬性
            ChatVO chat = new ChatVO();
            chat.setEmpoId(empo_id);
            chat.setMembId(memb_id);
            chat.setConvSpeakingTime(currentTimestamp);
            chat.setConvContent(conv_content);
            
//            String key = "chat:" + memb_id + ":" + empo_id;
//            jedis.lpush(key, conv_content);  // 將訊息推入 Redis 的列表

            // 呼叫 DAO 插入資料
            ChatDAO cDao = new ChatDAO();
            cDao.insert(chat);

            // 回應成功訊息給客戶端
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<h3>聊天訊息新增成功！</h3>");
            
        } catch (NumberFormatException e) {
            // 如果參數轉換失敗，回應錯誤訊息
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("<h3>參數格式錯誤，請檢查輸入。</h3>");
        } catch (Exception e) {
            // 捕捉其他例外，並回應錯誤訊息
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("<h3>系統發生錯誤，請稍後再試。</h3>");
            e.printStackTrace();
        }
    }
}
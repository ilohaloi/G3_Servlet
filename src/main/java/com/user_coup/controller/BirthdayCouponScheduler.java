package com.user_coup.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(loadOnStartup = 1, urlPatterns = "/birthdayCouponScheduler")
public class BirthdayCouponScheduler extends HttpServlet {
    private static final long serialVersionUID = 5337326634197982679L;
    private ScheduledExecutorService scheduler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
       	System.out.println("6666");
       	
        // 創建一個排程執行器，每天執行一次
        scheduler = Executors.newScheduledThreadPool(1);
        // 定義一個任務來發送優惠券
        Runnable task = () -> {
            try {
                checkNewMembersAndSendCoupons();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        };
        		 //  程式啟動後延遲X秒後執行    數字   單位
//        scheduler.scheduleAtFixedRate(task,0,30,TimeUnit.DAYS);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
    // 獲取資料庫連接
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3307/CruiseShip", "root", "123456");
    }

    // 檢查未發放生日優惠券的會員並發送優惠券
    private void checkNewMembersAndSendCoupons() throws ClassNotFoundException {
    	System.out.println("123456");
    	
        LocalDate today = LocalDate.now(); // 今天的日期
        String currentMonth = String.format("%02d", today.getMonthValue()); // 當前月份
        LocalDate nextDay = today.withDayOfMonth(today.lengthOfMonth()).plusDays(1); // 到期日期設為下月1號

        try (Connection conn = getConnection()) {
            // 查詢當月生日且沒有優惠券的會員，並直接發送優惠券
            String sql = "SELECT memb_id FROM member_data WHERE DATE_FORMAT(memb_birthday, '%m') = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, currentMonth);

            ResultSet rs = ps.executeQuery();
            String couponInsertSql = "INSERT INTO user_coupon (memb_id, coup_id, coup_expiry_date, coup_is_used) VALUES (?, ?, ?, ?)";
            PreparedStatement insertPs = conn.prepareStatement(couponInsertSql);
            int rest = 0;
            while (rs.next()) {
                int memberId = rs.getInt("memb_id");
                insertPs.setInt(1, memberId);
                insertPs.setInt(2, 1); // 假設優惠券代碼為1
                insertPs.setDate(3, Date.valueOf(nextDay)); // 設置到期日期
                insertPs.setInt(4, 0); // 未使用
                rest += insertPs.executeUpdate();
            }
      
            System.out.println("插入數量:" + rest );
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

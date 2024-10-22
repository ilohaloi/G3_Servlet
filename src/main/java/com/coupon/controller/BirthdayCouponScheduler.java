package com.coupon.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(loadOnStartup = 1, urlPatterns = "/birthdayCouponScheduler")
public class BirthdayCouponScheduler extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5337326634197982679L;
	private ScheduledExecutorService scheduler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // 創建一個排程執行器，每天執行一次
       // scheduler = Executors.newScheduledThreadPool(1);

        // 定義一個任務來發送優惠券
      //  Runnable task = this::checkBirthdaysAndSendCoupons;

        // 設置每天執行一次，延遲 0 天後開始
       //scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.DAYS);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    // 檢查當月有生日的會員並發送優惠券
    private void checkBirthdaysAndSendCoupons() {
        LocalDate today = LocalDate.now(); // 當天日期
        String currentMonth = String.format("%02d", today.getMonthValue()); // 取得當前月份，格式化成兩位數字

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/CruiseShip", "root", "123456")) {
            String sql = "SELECT memb_id, memb_name, memb_birthday FROM member_data WHERE DATE_FORMAT(birthday, '%m') = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, currentMonth);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int memberId = rs.getInt("memb_id");
                String memberName = rs.getString("memb_name");
                String birthday = rs.getString("memb_birthday");

                // 發送優惠券給這個會員
                sendCoupon(memberId, memberName, birthday);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 發送優惠券的邏輯，可以根據業務需求自定義
    private void sendCoupon(int memberId, String memberName, String birthday) {
        System.out.println("發送優惠券給會員: " + memberName + "，生日：" + birthday);

        // 這裡可以加入向數據庫插入優惠券的邏輯，或者發送郵件通知
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/CruiseShip", "root", "123456")) {
            String couponInsertSql = "INSERT INTO coupons (member_id, coupon_name, issue_date, expiry_date) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(couponInsertSql);
            ps.setInt(1, memberId);
            ps.setString(2, generateCouponCode()); // 假設有一個生成優惠券代碼的方法
            ps.setString(3, LocalDate.now().format(DateTimeFormatter.ISO_DATE)); // 發放日期
            ps.setString(4, LocalDate.now().plusMonths(1).format(DateTimeFormatter.ISO_DATE)); // 有效期一個月

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 模擬生成優惠券代碼的方法
    private String generateCouponCode() {
        return "BDAY" + System.currentTimeMillis(); // 優惠券代碼示例
    }
}

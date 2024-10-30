package com.member.control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.member.model.MemberJDBC;
import com.member.model.MemberVO;
import com.member.util.MailService;
import com.outherutil.json.JsonDeserializerInterface;

@WebServlet("/MemberForgetPassword")
public class ForgetPasswordServlet extends HttpServlet implements JsonDeserializerInterface {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		Gson gson = new Gson();
		MemberVO membVo = gson.fromJson(req.getReader(), MemberVO.class);
		JsonObject jsonResponse = new JsonObject();

		try (PrintWriter out = resp.getWriter()) {
			// 使用 MemberJDBC 檢查電子郵件並更新密碼
			MemberJDBC memberJDBC = new MemberJDBC();
			String email = membVo != null ? membVo.getEmail() : null;

			if (email != null && memberJDBC.isEmailExists(email)) {
				String newPassword = generateRandomPassword();
				memberJDBC.updatePasswordByEmail(email, newPassword);

				// 呼叫 MailService 發送新密碼
				MailService mailService = new MailService();
				String subject = "密碼重設通知";
				String messageText = "您好！您的新密碼為：" + newPassword + "\n請立即使用此密碼登入並更新密碼。";
				mailService.sendMail(email, subject, messageText);

				System.out.print("確認為會員，並已送出新密碼!");
				
			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 隨機密碼生成方法
	private String generateRandomPassword() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder password = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			password.append(chars.charAt(random.nextInt(chars.length())));
		}

		return password.toString();
	}

	// 改進的 doGet 方法，不返回錯誤代碼
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().write("會員忘記密碼頁面。");
	}
}

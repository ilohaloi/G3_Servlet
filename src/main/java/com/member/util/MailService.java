package com.member.util;

import java.security.SecureRandom;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {

	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	public void sendMail(String to, String subject, String messageText) {

		try {
			// 設定使用SSL連線至 Gmail smtp Server
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			final String myGmail = "tibamecruise.send@gmail.com";
			final String myGmail_password = "xnvixkpfvqxrququ";
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(myGmail, myGmail_password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myGmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// 設定信中的主旨
			message.setSubject(subject);
			// 設定信中的內容
			message.setText(messageText);

			Transport.send(message);
			System.out.println("傳送成功!");
		} catch (MessagingException e) {
			System.out.println("傳送失敗!");
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		
	        SecureRandom random = new SecureRandom();
	        StringBuilder sb = new StringBuilder(8);

	        for (int i = 0; i < 8; i++) {
	            int digit = random.nextInt(10); // 生成 0-9 的隨機數字
	            sb.append(digit);
	        }

	        String randomNumber = sb.toString();
	        System.out.println("隨機生成的 8 位數字為: " + randomNumber);
	    

//		String to = "40000222E@gmail.com";
//
//		String subject = "密碼通知";
//
//		String ch_name = "peter1";
//		String passRandom = randomNumber;
//		String messageText = "Hello! " + ch_name + " 請謹記此密碼: " + passRandom + "\n" + " (已經啟用)";
//
//		MailService mailService = new MailService();
//		mailService.sendMail(to, subject, messageText);
	}

}

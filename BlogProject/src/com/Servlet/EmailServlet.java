package com.Servlet;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EmailServlet
 */
@WebServlet("/emailsend")
public class EmailServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String email = request.getParameter("email");
		String host = "smtp.naver.com";
		String user = "ug0516@naver.com";
		String password = "lastnightonearth";
		
		String to_email = email;
		
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		StringBuffer temp = new StringBuffer();
		
		Random rmd = new Random();
		
		for(int i=0; i<6; i++) {
			temp.append(rmd.nextInt(9));
		}
			String auth = temp.toString();
			System.out.println(auth);
			
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});
			
			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(user, "with"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
				
				msg.setSubject("［共々］：認証番号");
				msg.setText("認証番号： " + temp);
				
				Transport.send(msg);
				System.out.println("이메일 전송 성공");
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				response.getWriter().write("{\"result\":\"" + auth + "\"}");
			} catch (javax.mail.SendFailedException e) {
				System.out.println("유효하지 않은 이메일: " + to_email);
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"error\":\"有効なメールアドレスを入力してくだたい。\"}");
				
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("{\"error\":\"メールの送信中にエラーが発生しました。\"}");
			}
			HttpSession saveKey = request.getSession();
			saveKey.setAttribute("auth", auth);
	}
	
}

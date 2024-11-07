package com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.UserDAO;
import com.DB.DBConn;
import com.VO.UserDetails;

/**
 * Servlet implementation class InquiryproServlet
 */
@WebServlet("/inquirypro")
public class InquiryproServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UserDetails user = (UserDetails)request.getSession().getAttribute("userD");
		
		if(user == null) {
			response.sendRedirect("/");
			return;
		}
		
		String type = request.getParameter("inquiryType");
		String content = request.getParameter("message");
		System.out.println(type + "  " + content);
		if(isValid(type, content)) {
			int idx = user.getIdx();
			String email = user.getEmail();
			String nickname = user.getNickname();
			UserDAO dao = new UserDAO(DBConn.getConn());
			dao.setInquiry(idx, email, nickname, type, content);
			response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("alert('お問い合わせの内容が送信されました！');");
		} else {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<script type='text/javascript'>");
			response.getWriter().println("alert('エラーが発生しました！');");
		}
		response.getWriter().println("window.location.href='/inquiry';");
		response.getWriter().println("</script>");
		
	}
	private boolean isValid(String type, String content) {
		if(type == null || (!type.equals("advertising") && !type.equals("report") && !type.equals("general"))) {
			return false;
		}
		if(content == null || content.trim().isEmpty()) {
			return false;
		}
		return true;
	}
}

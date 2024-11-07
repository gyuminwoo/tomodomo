package com.Servlet;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.UserDAO;
import com.DB.DBConn;
import com.VO.UserDetails;

/**
 * Servlet implementation class ProfileChangeServlet
 */
@WebServlet("/change")
public class ProfileChangeServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UserDetails user = (UserDetails) request.getSession().getAttribute("userD");
		
		if(user == null) {
			response.sendRedirect("/login");
			return;
		}
		Integer idx = user.getIdx();
		if(request.getParameter("nickname") != null) {
		String nickname = request.getParameter("nickname");
		String checkNick = user.getNickname();
		
			
			if(!nickname.equals(checkNick) && isValidNickname(nickname)) {
				UserDAO dao = new UserDAO(DBConn.getConn());
				dao.nicknameChange(idx, nickname);
				user.setNickname(nickname);
				request.getSession().setAttribute("userD", user);
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("<script type='text/javascript'>");
				response.getWriter().println("alert('ニックネームが変更されました！');");
				response.getWriter().println("window.location.href='/';");
				response.getWriter().println("</script>");
				return;
			}
		}
		
		if(request.getParameter("password") != null && request.getParameter("passwordC") != null) {
		String newPassword = request.getParameter("password");
		String newPasswordC = request.getParameter("passwordC");
			if(newPassword.equals(newPasswordC) && isValidPassword(newPassword)) {
				UserDAO dao = new UserDAO(DBConn.getConn());
				dao.passwordChange(idx, newPassword);
				user.setPassword(newPassword);
				request.getSession().setAttribute("userD", user);
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("<script type='text/javascript'>");
				response.getWriter().println("alert('パスワードが変更されました！');");
				response.getWriter().println("window.location.href='/';");
				response.getWriter().println("</script>");
				return;
			} 
		}
		response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println("<script type='text/javascript'>");
        response.getWriter().println("alert('入力した情報を確認してください！');");
        response.getWriter().println("window.location.href='/'");
        response.getWriter().println("</script>");
	}
	
	private boolean isValidNickname(String nickname) {
        return nickname != null && nickname.length() <= 16 && !Pattern.compile("[!@#$%^&*(),.?\":{}|<> ]").matcher(nickname).find();
    }
	
	private boolean isValidPassword(String password) {
    	return password != null && !password.contains(" ") && password.length() <= 20;
    }

}

package com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.UserDAO;
import com.DB.DBConn;
import com.VO.UserDetails;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		UserDetails us = new UserDetails();
		
		us.setId(id);
		us.setPassword(password);
		
		UserDAO dao = new UserDAO(DBConn.getConn());
		UserDetails user = dao.loginUser(us);
		
		if(user != null) {
			HttpSession session = request.getSession(false);
			if(session != null) {
				session.invalidate();
			}
			session = request.getSession(true);
			session.setAttribute("userD", user);
			session.setMaxInactiveInterval(21600);
	        String redirect = (String) session.getAttribute("redirect");
	        
	        if(redirect != null && (redirect.contains("/login") || redirect.contains("/signup"))) {
	        	redirect = "/";
	        }
	        
	        response.sendRedirect(redirect != null ? redirect : "/");
	        session.removeAttribute("redirect");
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("login-failed", "IDまたはパスワードが正しくありません。");
			response.sendRedirect("/login");
		}
	}

}

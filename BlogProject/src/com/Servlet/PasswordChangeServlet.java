package com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.UserDAO;
import com.DB.DBConn;

/**
 * Servlet implementation class PasswordChangeServlet
 */
@WebServlet("/pwchange")
public class PasswordChangeServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("userId");
		String password = request.getParameter("newPassword");
		if(id != null && password != null && isValidPassword(password)) {
			UserDAO dao = new UserDAO(DBConn.getConn());
			dao.modifyPassword(id, password);
			request.getSession().removeAttribute("findid");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print("<script>alert('パスワードが変更されました！');window.location.href='/';</script>");
			return;
		}
		response.sendRedirect("/");
	}
	
	private boolean isValidPassword(String password) {
    	String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]*$";
    	return password != null && !password.contains(" ") && password.length() >= 8 && password.length() <= 20 && password.matches(regex);
    }

}

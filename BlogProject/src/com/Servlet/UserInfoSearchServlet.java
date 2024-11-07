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
 * Servlet implementation class UserInfoSearchServlet
 */
@WebServlet("/userfndinfo")
public class UserInfoSearchServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String email = request.getParameter("email");
		if(email == null) {
			response.sendRedirect("/");
			return;
		}
		UserDAO dao = new UserDAO(DBConn.getConn());
		
		String id = dao.getUserInfo(email);
		System.out.println(id);
		
		request.getSession().setAttribute("findid", id);
	}

}

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

@WebServlet("/deleteAccount")
public class DeleteAccountServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserDetails user = (UserDetails)request.getSession().getAttribute("userD");
		if(user == null) {
			response.sendRedirect("/");
			return;
		}
		int userIdx = user.getIdx();
		
		UserDAO dao = new UserDAO(DBConn.getConn());
		
		dao.deleteAccount(userIdx);
		
		request.getSession().invalidate();
		
		response.sendRedirect("/");
	}

}

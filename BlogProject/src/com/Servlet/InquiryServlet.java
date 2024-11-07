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
 * Servlet implementation class InquiryServlet
 */
@WebServlet("/inquiry")
public class InquiryServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UserDetails user = (UserDetails)request.getSession().getAttribute("userD");
		
		if(user == null) {
			response.sendRedirect("/");
			return;
		}
		
		request.getRequestDispatcher("/member/inquiry.jsp").forward(request, response);
	}

}

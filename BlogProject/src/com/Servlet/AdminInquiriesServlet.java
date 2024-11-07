package com.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.UserDAO;
import com.DB.DBConn;
import com.VO.InquiriesVO;
import com.VO.UserDetails;

/**
 * Servlet implementation class AdminInquiriesServlet
 */
@WebServlet("/admin/inquiries")
public class AdminInquiriesServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UserDetails user = (UserDetails)request.getSession().getAttribute("userD");
		System.out.println(1);
		if(user == null || user.getAuth() != 1) {
			response.sendRedirect("/");
			return;
		}
		
		UserDAO dao = new UserDAO(DBConn.getConn());
		if(request.getParameter("idx") != null) {
			int idx = Integer.parseInt(request.getParameter("idx"));
			dao.setSolve(idx);
		}
		
		
		List<InquiriesVO> ilist = dao.getInquiries();
		
		request.setAttribute("ilist", ilist);
		
		request.getRequestDispatcher("/admin/inquiries.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

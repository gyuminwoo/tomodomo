package com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.UserDAO;
import com.DB.DBConn;

@WebServlet("/checkDuplicate")
public class CheckDuplicateServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		String type = request.getParameter("type");
		String value = request.getParameter("value");
		
		UserDAO dao = new UserDAO(DBConn.getConn());
		
		if("id".equals(type)) {
			if(dao.isIdExists(value)) {
				response.getWriter().write("使用できないIDです");
			} else {
				response.getWriter().write("使用可能なIDです");
			}
		}
		if("nickname".equals(type)) {
			if(dao.isNicknameExists(value)) {
				response.getWriter().write("使用できないニックネームです");
			} else {
				response.getWriter().write("使用可能なニックネームです");
			}
		}
	}

}

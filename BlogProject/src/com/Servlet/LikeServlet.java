package com.Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.google.gson.Gson;

/**
 * Servlet implementation class LikeServlet
 */
@WebServlet("/likecount")
public class LikeServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String temp1 = request.getParameter("isLiked");
		String temp2 = request.getParameter("uidx");
		String temp3 = request.getParameter("pidx");
		
		if(temp1 == null || temp2 == null || temp3 == null) {
			response.sendRedirect("/");
			return;
		}
		
		int like = Integer.parseInt(temp1);
		int uidx = Integer.parseInt(temp2);
		int pidx = Integer.parseInt(temp3);
		
		BlogDAO dao = new BlogDAO(DBConn.getConn());
		dao.setLike(like, uidx, pidx);
		dao.updateSuggestCount(pidx);
	}
}

package com.service.blog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.BlogProfile;
import com.VO.UserDetails;
import com.service.Action;

public class BlogProfileEdit implements Action {

	@Override
	public void command(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UserDetails user = (UserDetails) request.getSession().getAttribute("userD");
		
		if(user == null) {
			response.sendRedirect("/login");
			return;
		}
		
		int user_idx = user.getIdx();
		BlogDAO dao = new BlogDAO(DBConn.getConn());
		
		
		BlogProfile bp = dao.getBlogProfile(user_idx);
		
		request.setAttribute("bp", bp);
		request.getRequestDispatcher("/blog/blogprofile.jsp").forward(request, response);
	}

}

package com.service.blog;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.Post;
import com.VO.UserDetails;
import com.service.Action;

public class BlogAddpost implements Action {

	@Override
	public void command(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UserDetails user = (UserDetails) request.getSession().getAttribute("userD");
		
		if(user == null) {
			response.sendRedirect("/");
			return;
		}
		
		int userIdx = user.getIdx();
		
		BlogDAO dao = new BlogDAO(DBConn.getConn());
		List<Post> themeList = dao.getTheme(userIdx);
		
		request.setAttribute("theme", themeList);
		request.getRequestDispatcher("/blog/addpost.jsp").forward(request, response);
	}

}

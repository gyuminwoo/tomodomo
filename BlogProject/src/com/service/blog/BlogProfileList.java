package com.service.blog;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.BlogProfile;
import com.VO.Page;
import com.service.Action;

public class BlogProfileList implements Action {

	@Override
	public void command(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		BlogDAO dao = new BlogDAO(DBConn.getConn());
		
		String pageParam = request.getParameter("page");
		int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
		
		int totalBlog = dao.getTotalBlogProfile();
		Page page = new Page(currentPage, 5, totalBlog);
		List<BlogProfile> blogList = dao.getMainBlogs(page.getStartIndex(), 5);
		request.setAttribute("blogList", blogList);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/blog/main.jsp").forward(request, response);
	}

}

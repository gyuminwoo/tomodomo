package com.service.blog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.Post;
import com.VO.UserDetails;
import com.service.Action;

public class BlogPostModify implements Action {

	@Override
	public void command(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UserDetails user = (UserDetails)request.getSession().getAttribute("userD");
		if(user == null) {
			response.sendRedirect("/");
			return;
		}
		
		int pidx = Integer.parseInt(request.getParameter("pidx"));
		
		BlogDAO dao = new BlogDAO(DBConn.getConn());
		
		Post pvo = dao.getPostModifyData(pidx);
		request.setAttribute("pvo", pvo);
		
		request.getRequestDispatcher("/blog/modifypost.jsp").forward(request, response);
		
	}

}

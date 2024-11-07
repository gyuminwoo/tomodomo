package com.service.blog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.UserDetails;
import com.service.Action;

public class BlogPostDelete implements Action {

	@Override
	public void command(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		UserDetails user = (UserDetails)request.getSession().getAttribute("userD");
		if(user == null) {
			response.sendRedirect("/");
			return;
		}
		int userIdx = user.getIdx();
		BlogDAO dao = new BlogDAO(DBConn.getConn());
		
		boolean check = dao.deletePost(idx, userIdx);
		
		if(check == true) {
			response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("window.location.href='/blog/home?nick=" + request.getParameter("nick") +"';");
            response.getWriter().println("</script>");
		}
	}

}

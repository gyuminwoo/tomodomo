package com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.Post;
import com.VO.UserDetails;

/**
 * Servlet implementation class ThemeServlet
 */
@WebServlet("/theme")
public class ThemeServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String addCate = request.getParameter("addcate");
		UserDetails user = (UserDetails) request.getSession().getAttribute("userD");
		if(user == null) {
			response.sendRedirect("/");
			return;
		}
		int userIdx = user.getIdx();
		System.out.println(userIdx);
		System.out.println(addCate);
		if(isValid(addCate)) {
			Post pvo = new Post(userIdx, addCate);
			BlogDAO dao = new BlogDAO(DBConn.getConn());
			dao.addTheme(pvo);
			
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<script>alert('新しいテーマが追加されました！'); window.location.href='/blog/addpost';</script>");
			return;
		} else {
			response.setContentType("text/html; charset=UTF-8");
		    response.getWriter().println("<script>alert('テーマ名が空です！'); history.back();</script>");
		    return;
		}
		
	}
	
	private boolean isValid(String addCate) {
		return addCate != null && !addCate.trim().isEmpty();
	}
	
}

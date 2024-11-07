package com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;

/**
 * Servlet implementation class ModifyPostproServlet
 */
@WebServlet("/blog/modifypro")
public class ModifyPostproServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String pidxTemp = request.getParameter("pidx");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		System.out.println(pidxTemp);
		System.out.println(title);
		System.out.println(content);
		if(pidxTemp != null && isValid(title, content)) {
			int pidx = Integer.parseInt(pidxTemp);
			BlogDAO dao = new BlogDAO(DBConn.getConn());
			dao.setModifyPost(pidx, title, content);
			int dummy = dao.getuidx(pidx);
			String nickname = dao.getNickname(dummy);
			response.setCharacterEncoding("UTF-8");
			response.sendRedirect("/blog/home?nick=" + nickname);
		} else {
			response.setContentType("text/html; charset=UTF-8");
		    response.getWriter().println("<script>alert('タイトルまたは内容が空です！'); history.back();</script>");
		    return;
		}
	}
	private boolean isValid (String title, String content) {
		return title != null && !title.trim().isEmpty() && content != null && !content.trim().isEmpty();
	}
}

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
 * Servlet implementation class CommentServlet
 */
@WebServlet("/comment")
public class CommentInsertServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int uidx = Integer.parseInt(request.getParameter("uidx"));
		int pidx = Integer.parseInt(request.getParameter("pidx"));
		String temp = request.getParameter("groupNum");
		String content = request.getParameter("comment");
		System.out.println("userIdx:" + uidx);
		System.out.println("postIdx:" + pidx);
		System.out.println("Comment content:" + content);
		System.out.println("groupNum:" + temp);
		if(isValid(content) == true) {
			BlogDAO dao = new BlogDAO(DBConn.getConn());
			if(temp == null) {
				dao.insertComment(content, uidx, pidx);
			} else {
				int groupNum = Integer.parseInt(temp);
				dao.insertReply(content, uidx, pidx, groupNum);
			}
		}
		
	}
	
	private boolean isValid(String content) {
		return content != null && content.length() <= 150 && content.trim().length() > 0;
	}

}

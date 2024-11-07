package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.CommentVO;
import com.VO.Page;
import com.google.gson.Gson;

/**
 * Servlet implementation class CommentViewServlet
 */
@WebServlet("/commentview")
public class CommentViewServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int pidx = Integer.parseInt(request.getParameter("pidx"));
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = 5;
		
		BlogDAO dao = new BlogDAO(DBConn.getConn());
		List<CommentVO> comments = dao.getCommentList(pidx, pageSize, (page - 1) * pageSize);
		int totalPost = dao.getCommentTotal(pidx);
		
		int totalPages = (int) Math.ceil((double) totalPost / pageSize);
		
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		out.print(gson.toJson(new CommentsResponse(comments, totalPages)));
		out.flush();
		
	}
}
	
	class CommentsResponse {
		List<CommentVO> comments;
		int totalPages;
		
		CommentsResponse(List<CommentVO> comments, int totalPages) {
			this.comments = comments;
			this.totalPages = totalPages;
		}
	}


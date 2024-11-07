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
 * Servlet implementation class CommentDeleteServlet
 */
@WebServlet("/commentdelete")
public class CommentDeleteServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int cidx = Integer.parseInt(request.getParameter("cidx"));
		int pidx = Integer.parseInt(request.getParameter("pidx"));
		int cclass = Integer.parseInt(request.getParameter("cclass"));
		int cgroupnum = Integer.parseInt(request.getParameter("cgroupnum"));
		
		BlogDAO dao = new BlogDAO(DBConn.getConn());
		
		if(cclass == 0) {
			dao.commentDeleteClass0(pidx, cgroupnum);
		}
		if(cclass == 1) {
			dao.commentDeleteClass1(cidx);
		}
	}

}

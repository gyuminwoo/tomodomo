package com.service.blog;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.UserDAO;
import com.DB.DBConn;
import com.VO.IndexVO;
import com.service.Action;

public class Index implements Action {

	@Override
	public void command(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserDAO dao = new UserDAO(DBConn.getConn());
		List<IndexVO> blogList = dao.getBlog();
		List<IndexVO> postList = dao.getPost();
		
		request.setAttribute("blogList", blogList);
		request.setAttribute("postList", postList);
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}

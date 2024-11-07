package com.service.blog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.PostDetailVO;
import com.VO.UserDetails;
import com.service.Action;

public class BlogPostDetail implements Action {

	@Override
	public void command(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String pIdx = request.getParameter("idx");
		System.out.println(pIdx);
		if(pIdx == null) {
			response.sendRedirect("/blog/main");
			return;
		}
		int pidx = (Integer.parseInt(pIdx));
		
		BlogDAO dao = new BlogDAO(DBConn.getConn());
		dao.modifyViewCount(pidx);
		PostDetailVO pvo = dao.getPostDetail(pidx);
		UserDetails user = (UserDetails)request.getSession().getAttribute("userD");
		Integer userIdx = -1;
		if(user != null) {
			userIdx = user.getIdx();
		}
		boolean myPageCheck = dao.postUserCheck(userIdx, pidx);
		int likeState = dao.checkLikeState(pidx, userIdx);
		request.setAttribute("likeState", likeState);
		request.setAttribute("pvo", pvo);
		request.setAttribute("myPageCheck", myPageCheck);
		request.getRequestDispatcher("/blog/postdetail.jsp").forward(request, response);
	}

}

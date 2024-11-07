package com.service.blog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Action;

public class BlogPostThemeList implements Action {

	@Override
	public void command(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String check = request.getParameter("idx");
		String tname = request.getParameter("tname");
		if(check == null || tname == null) {
			response.sendRedirect("/blog/main");
			return;
		}
		
		int idx = Integer.parseInt(check);
		
		
		
	}

}

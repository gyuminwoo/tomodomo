package com.Servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.BlogProfile;
import com.VO.UserDetails;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class BlogProfileEditServlet
 */
@WebServlet("/blog/editpro")
public class BlogProfileEditServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UserDetails user = (UserDetails) request.getSession().getAttribute("userD");
		
		if(user == null) {
			response.sendRedirect("/");
			return;
		}
		
		String savepath = "/blogprofileimg";
		int maxSize = 20 * 1024 * 1024;
		String enctype = "utf-8";
		ServletContext context = request.getServletContext();
		String path = context.getRealPath(savepath);
		System.out.println(path);
		MultipartRequest multi = new MultipartRequest(request, path, maxSize, enctype, new DefaultFileRenamePolicy());
		
		int user_idx = user.getIdx();
		String blog_intro = multi.getParameter("blog_intro").replace("\r\n", "<br>");
		String[] lines = blog_intro.split("<br>");
		if(lines.length > 3) {
			blog_intro = String.join("<br>", Arrays.copyOf(lines, 3));
		}
		String profile_image = multi.getFilesystemName("profile_image");
		System.out.println(profile_image);
		if(profile_image != null) {
			profile_image = request.getContextPath() + savepath + "/" + profile_image;
		}
		System.out.println(profile_image);
		BlogProfile bpvo = new BlogProfile(user_idx, blog_intro, profile_image);
		
		BlogDAO dao = new BlogDAO(DBConn.getConn());
		dao.blogProfile(bpvo);
		String nickname = dao.getNickname(user_idx);
		response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<script>alert('ブログのプロフィールが変更されました！'); window.location.href='/blog/home?nick=" + nickname + "';</script>");
	}

}

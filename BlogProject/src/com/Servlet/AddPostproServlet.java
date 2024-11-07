package com.Servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.Post;
import com.VO.UserDetails;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class AddPostServlet
 */
@WebServlet("/blog/addpostpro")
public class AddPostproServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UserDetails user = (UserDetails) request.getSession().getAttribute("userD");
		
		if(user == null) {
			response.sendRedirect("/login");
			return;
		}
		
		
		
		String savepath = "/uploads";
		int maxSize = 20 * 1024 * 1024;
		String enctype = "utf-8";
		ServletContext context = request.getServletContext();
		String path = context.getRealPath(savepath);
		System.out.println(path);
		MultipartRequest multi = new MultipartRequest(request, path, maxSize, enctype, new DefaultFileRenamePolicy());
		
		int useridx = user.getIdx();
		String title = multi.getParameter("title");
		String category = multi.getParameter("categoryName");
		int categoryIdx = Integer.parseInt(multi.getParameter("categoryIdx"));
		String content = multi.getParameter("content");
		String filename = multi.getFilesystemName("filename");
		String fileUrl = null;
		if(filename != null) {
			fileUrl = request.getContextPath() + savepath + "/" + filename;
		}
			
		String action = multi.getParameter("action");
		
		System.out.println(fileUrl);
		
//		System.out.println(useridx);
//		System.out.println(title);
//		System.out.println(category);
//		System.out.println(categoryIdx);
//		System.out.println(content);
//		System.out.println(filename);
//		System.out.println(action);
		
		if(isValid(title, content)) {
			Post pvo = new Post(useridx, title, category, categoryIdx, content, fileUrl);
			BlogDAO dao = new BlogDAO(DBConn.getConn());
			String nickname = dao.getNickname(useridx);
			String encodeNickname = URLEncoder.encode(nickname, "UTF-8");
			if(action.equals("save")) {
				dao.addPost(pvo);
				response.sendRedirect("/blog/home?nick=" + encodeNickname);
			}
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

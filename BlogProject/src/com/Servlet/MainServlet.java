package com.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.VO.UserDetails;
import com.service.blog.BlogAddpost;
import com.service.blog.BlogPostDelete;
import com.service.blog.BlogPostDetail;
import com.service.blog.BlogPostList;
import com.service.blog.BlogPostModify;
import com.service.blog.BlogPostThemeList;
import com.service.blog.BlogProfileEdit;
import com.service.blog.BlogProfileList;
import com.service.blog.Index;

@WebServlet(urlPatterns = {"/", ""})
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MainServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = request.getServletPath();
		System.out.println(path);
		String realPath = null;
		switch (path) {
		case "":
			new Index().command(request, response);
			return;
		case "/":
			new Index().command(request, response);
			return;
		case "/signup":
			realPath = "/member/register.jsp";
			break;
		case "/login":
			realPath = "/member/login.jsp";
			break;
		case "/blog/":
			realPath = "/blog/main.jsp";
			break;
		case "/help":
			request.getRequestDispatcher("/member/findaccount.jsp").forward(request, response);
			return;
		case "/userinfo":
			request.getRequestDispatcher("/member/userinfo.jsp").forward(request, response);
			return;
		case "/blog/home":
			new BlogPostList().command(request, response);
			return;
		case "/blog/addpost":
			new BlogAddpost().command(request, response);
			break;
		case "/blog/main":
			new BlogProfileList().command(request, response);
			return;
		case "/blog/edit":
			new BlogProfileEdit().command(request, response);
			return;
		case "/blog/theme":
			new BlogPostThemeList().command(request, response);
			return;
		case "/blog/post":
			new BlogPostDetail().command(request, response);
			return;
		case "/blog/postdelete":
			new BlogPostDelete().command(request, response);
			return;
		case "/blog/modify":
			new BlogPostModify().command(request, response);
			return;
		case "/inquiry":
			realPath = "/member/inquiry.jsp";
			break;
		default:
			new Index().command(request, response);
			return;
		}
		
		if (realPath != null) {
			request.getRequestDispatcher(realPath).forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

package com.service.blog;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.BlogProfile;
import com.VO.Page;
import com.VO.Post;
import com.VO.UserDetails;
import com.service.Action;

public class BlogPostList implements Action {

    @Override
    public void command(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String nick = request.getParameter("nick");
        BlogDAO dao = new BlogDAO(DBConn.getConn());
        boolean isValidUrl = dao.isValidUrl(nick);
        if(nick == null || isValidUrl == false) {
        	response.sendRedirect("/blog/main");
        	return;
        }
        
        UserDetails user = (UserDetails) request.getSession().getAttribute("userD");

        String tname = request.getParameter("tname") != null ? request.getParameter("tname") : null;
        String pageParam = request.getParameter("page");
        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;

        int userIdx = dao.getUserIdx(nick);
        String nickname = dao.getNickname(userIdx);
        System.out.println(userIdx);
        int totalPost = 0;
        if(tname == null) {
        	totalPost = dao.totalPost(userIdx);
        } else {
        	totalPost = dao.themeSelectTotalPost(userIdx, tname);
        	request.setAttribute("tname", tname);
        }
        Integer checkIdx = null;
        if(user != null) {
        	checkIdx = user.getIdx();
        }
        Page page = new Page(currentPage, 4, totalPost);
        System.out.println(currentPage);
        System.out.println(totalPost);
        List<Post> posts = dao.getPosts(userIdx, page.getStartIndex(), 4, tname); // 게시글 가져오기
        BlogProfile bp = dao.getBlogData(userIdx, checkIdx);
        List<Post> cateList = dao.getTheme(userIdx);
        dao.modifyVisitCount(userIdx);
        
        request.setAttribute("cateList", cateList);
        request.setAttribute("bp", bp);
        request.setAttribute("nick", nickname);
        request.setAttribute("posts", posts);
        request.setAttribute("page", page);
        request.getRequestDispatcher("/blog/home.jsp").forward(request, response);
    }
}

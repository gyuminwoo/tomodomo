package com.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.DAO.BlogDAO;
import com.DB.DBConn;
import com.VO.Page;
import com.VO.Post;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		
		if(type == null || keyword == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		BlogDAO dao = new BlogDAO(DBConn.getConn());
		
		int totalPost2 = dao.getSearchTotalCount(keyword, type);
		String pageParam2 = request.getParameter("page");
		int currentPage2 = pageParam2 != null ? Integer.parseInt(pageParam2) : 1;
		Page page2 = new Page(currentPage2, 5, totalPost2);
		List<Post> getSearch = dao.getSearchData(keyword, type, page2.getStartIndex(), 5);
		
		JSONArray jsonArray = new JSONArray();
		for (Post post : getSearch) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("idx", post.getIdx());
			jsonObj.put("title", post.getTitle());
			jsonObj.put("nickname", post.getTname());
			jsonArray.put(jsonObj);
		}
		
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("posts", jsonArray);
		jsonResponse.put("totalPages", page2.getTotalPage());
		jsonResponse.put("currentPage", currentPage2);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonResponse.toString());
	}

}

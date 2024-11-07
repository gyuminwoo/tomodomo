package com.Servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.UserDAO;
import com.DB.DBConn;
import com.VO.UserDetails;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String passwordC = request.getParameter("passwordC");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String gender = request.getParameter("gender");
        
        System.out.println(id);
        System.out.println(password);
        System.out.println(passwordC);
        System.out.println(nickname);
        System.out.println(email);
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(gender);
        
        if(id == null || password == null || passwordC == null || nickname == null || email == null || year == null || month == null || day == null || gender == null || !password.equals(passwordC)) {
        	response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script type='text/javascript'>");
            response.getWriter().println("alert('入力した情報を確認してください！');");
            response.getWriter().println("window.location.href='/signup';");
            response.getWriter().println("</script>");
        } else {
        	boolean f;
	        LocalDate birthdate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
	        
	        
	        if(!isValidId(id) || !isValidNickname(nickname) || !isValidEmail(email) || !isValidPassword(password)) {
	        	f = false;
	        } else {
		        UserDetails us = new UserDetails();
		        us.setId(id);
		        us.setPassword(password);
		        us.setNickname(nickname);
		        us.setEmail(email);
		        us.setBirthdate(birthdate);
		        us.setGender(gender);
		        
		        UserDAO dao = new UserDAO(DBConn.getConn());
		        f = dao.addUser(us);
	        }
	        if (f) {
	            response.setContentType("text/html; charset=UTF-8");
	            response.getWriter().println("<script type='text/javascript'>");
	            response.getWriter().println("alert('会員登録が完了しました！');");
	            response.getWriter().println("window.location.href='/';");
	            response.getWriter().println("</script>");
	        } else {
	            response.setContentType("text/html; charset=UTF-8");
	            response.getWriter().println("<script type='text/javascript'>");
	            response.getWriter().println("alert('入力した情報を確認してください！');");
	            response.getWriter().println("window.location.href='/signup';");
	            response.getWriter().println("</script>");
	        }
        }
	}
	
    private boolean isValidId(String id) {
        return id != null && id.length() >= 4 && id.length() <= 16 && !id.contains(" ") && !Pattern.compile("[!@#$%^&*(),.?\":{}|<> ]").matcher(id).find();
    }

    private boolean isValidNickname(String nickname) {
        return nickname != null && nickname.length() <= 16 && !nickname.contains(" ") && !Pattern.compile("[!@#$%^&*(),.?\":{}|<> ]").matcher(nickname).find();
    }

    private boolean isValidEmail(String email) {
        return email != null && !email.contains(" ") && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }
    
    private boolean isValidPassword(String password) {
    	String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]*$";
    	return password != null && !password.contains(" ") && password.length() >= 8 && password.length() <= 20 && password.matches(regex);
    }
}

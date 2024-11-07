<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
    String referer = request.getHeader("Referer");
    if (referer != null) {
        session.setAttribute("redirect", referer);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン | 共々</title>
    <%@ include file="/all_component/allcss.jsp"%>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="/all_component/header.jsp"%>
    <%
    if(user != null) {
    	response.sendRedirect("/");
    } 
    %>
    <section class="container-fluid div-color flex-fill">
        <div class="row">
            <div class="col-md-4 offset-md-4" style="margin-top: 110px">
                <div class="card mt-5 mb-4" style="margin: auto; max-width: 400px;">
                    <div class="card-header text-center">
                        <i class="fa fa-sign-in" aria-hidden="true"></i>
                        <h4>ログイン</h4>
                    </div>
                    
                    <%
                    String invalidMsg = (String) session.getAttribute("login-failed");
                    
                    if(invalidMsg != null) {
                    %>
                    
                    <div class="alert alert-danger" role="alert"><%=invalidMsg %></div>
                    
                    <%
                    session.removeAttribute("login-failed");
                    }
                    %>
                    
                    <div class="card-body">
                        <form onsubmit="return validateForm(event);" action="/loginServlet" method="post">
                            <div class="form-group">
                                <label for="id">共々 ID</label>
                                <input type="text" class="form-control" id="id" name="id">
                            </div>
                            <div class="form-group">
                                <label for="password">パスワード</label>
                                <input type="password" class="form-control" id="password" name="password">
                            </div>
                            <div class="text-center mb-3">
					            <a href="/member/register.jsp" class="text-decoration-none">新規登録</a> | 
					            <a href="/help" class="text-decoration-none">ID・パスワードを忘れた方</a>
					        </div>
                            <button type="submit" class="btn btn-primary btn-custom badge-pill">ログイン</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
    <script>
        function validateForm(event) {
            let id = document.getElementById('id');
            let password = document.getElementById('password');

            if (!id.value || !password.value) {
                alert("すべてのフィールドを入力してください。");
                if (!id.value) id.focus();
                else if (!password.value) password.focus();

                event.preventDefault();
                return false;
            }

            return true;
        }
    </script>
    
    <%@ include file="/all_component/footer.jsp" %>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>共々</title>
<%@ include file = "/all_component/allcss.jsp" %>
<style>
    .container {
        max-width: 400px;
        margin: 100px auto;
        padding: 20px;
        border: 1px solid #009970;
        border-radius: 10px;
        background-color: #f9f9f9;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    .btn-custom, .form-control {
        border-radius: 50px;
    }
    #passwordForm {
        margin-top: 15px;
    }
    footer {
        margin-top: auto;
        background-color: #f8f9fa;
        padding: 10px 0;
        text-align: center;
        border-top: 1px solid #ddd;
    }
</style>
</head>
<body>
<%@ include file = "/all_component/header.jsp" %>
<c:if test="${findid == null }"><%response.sendRedirect("/"); %></c:if>
<div class="container text-center" style="min-width: 460px;">
    <p><c:if test="${findid != 'そのメールアドレスで登録された情報はありません。' }">ユーザーID: </c:if><strong>${findid}</strong></p>
	<c:if test="${findid != 'そのメールアドレスで登録された情報はありません。' }">
    <button class="btn btn-primary btn-custom mt-3" id="showpassbtn" onclick="showPasswordForm()">パスワードを変更</button>
	</c:if>
    <form id="passwordForm" action="/pwchange" method="post" style="display: none;" onsubmit="return validatePassword()">
        <input type="hidden" name="userId" value="${findid}">
        <div class="form-group mt-3">
            <input type="password" id="newPasswordo" name="newPassword" class="form-control" placeholder="新しいパスワード">
            <div id="pwFeedback" class="text-danger"></div>
            <div id="pwCheck" class="text-danger"></div>
        </div>
        <button type="submit" class="btn btn-primary btn-custom">確認</button>
    </form>
</div>

<script>
    function showPasswordForm() {
        document.getElementById('showpassbtn').style.display = 'none';
        document.getElementById('passwordForm').style.display = 'block';
    }

    function validatePassword() {
        let passwordV = document.getElementById('newPasswordo').value;
        let pwFeedback = document.getElementById('pwFeedback');
        let pwCheck = document.getElementById('pwCheck');

        pwFeedback.textContent = "";
        pwCheck.textContent = "";

        if (passwordV.length < 8) {
            pwFeedback.textContent = "パスワードは8文字以上20文字以下の英字と数字を含めてください。";
            return false;
        }

        if (passwordV.length > 20) {
            pwCheck.textContent = "パスワードは8文字以上20文字以下の長さでください！";
            return false;
        }

        let regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*?&]*$/;
        if (!regex.test(passwordV)) {
            pwCheck.innerHTML = "パスワードは英字と数字を必ず含めてください。<br>使用できる特別な文字は @, $, !, %, *, ?, & です。";
            return false;
        }

        return true;
    }
    
    
</script>
<%@ include file = "/all_component/footer.jsp" %>
</body>
</html>

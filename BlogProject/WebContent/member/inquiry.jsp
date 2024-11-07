<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>お問い合わせ| 共々</title>
<%@ include file = "/all_component/allcss.jsp" %>
<style>
	.footer{
		margin-top: 8.2%;
	}
</style>
</head>
<body>
<%@ include file = "/all_component/header.jsp" %>
    <div class="container mt-5">
        <h2 class="text-center">お問い合わせ</h2>
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">&nbsp;</h5>
            </div>
            <div class="card-body">
                <form id="contactForm" action="/inquirypro" method="post">
                    <div class="form-group">
                        <label for="email">メールアドレス</label>
                        <input type="email" class="form-control" id="email" name="email" value="${userD.email }" disabled="disabled">
                    </div>
                    <div class="form-group">
                        <label for="name">ニックネーム</label>
                        <input type="text" class="form-control" id="name" name="name" value="${userD.nickname }" disabled="disabled">
                    </div>
                    <div class="form-group">
                        <label for="inquiryType">お問い合わせの種類</label>
                        <select class="form-control" id="inquiryType" name="inquiryType">
                            <option value="" disabled selected>選択してください</option>
                            <option value="advertising">広告問い合わせ</option>
                            <option value="report">ユーザー報告</option>
                            <option value="general">その他お問い合わせ</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="message">内容</label>
                        <textarea class="form-control" id="message" name="message" rows="8" style="resize: none;"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary mt-3">送信</button>
                </form>
            </div>
        </div>
    </div>
<%@ include file = "/all_component/footer.jsp" %>
<script>
    document.getElementById("contactForm").onsubmit = function(event) {
        const inquiryType = document.getElementById("inquiryType").value;
        const message = document.getElementById("message").value.trim();

        if (!inquiryType) {
            alert("お問い合わせの種類を選択してください。");
            event.preventDefault();
            return false;
        }

        if (!message) {
            alert("内容を入力してください。");
            event.preventDefault();
            return false;
        }

        return true;
    };
</script>
</body>
</html>

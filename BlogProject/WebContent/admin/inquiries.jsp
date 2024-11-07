<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>共々</title>
<%@ include file = "/all_component/allcss.jsp" %>
<style>
html, body {
    height: 100%;
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
}

body {
    flex: 1;
}

footer {
    margin-top: auto;
    background-color: #f8f9fa;
    padding: 10px 0;
    text-align: center;
    border-top: 1px solid #ddd;
}

.btn-small {
    font-size: 0.8rem;
    padding: 0.25rem 0.5rem;
}
</style>
</head>
<body>
<%@ include file = "/all_component/header.jsp" %>

<section class="container mt-5">
    <table class="table table-hover" style="border: 1px solid">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">問い合わせタイプ</th>
                <th scope="col">メール</th>
                <th scope="col">ニックネーム</th>
                <th scope="col">問い合わせ日</th>
                <th scope="col">状態</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="ilist" items="${ilist }">
	            <tr class="inquiry-row">
	                <td>${ilist.idx }</td>
	                <td>${ilist.type }</td>
	                <td>${ilist.email }</td>
	                <td>${ilist.nickname }</td>
	                <td>${ilist.idate }</td>
	                <td><c:if test="${ilist.state == 0 }"><span class="badge bg-danger">未回答</span></c:if><c:if test="${ilist.state == 1 }"><span class="badge bg-success">回答済み</span></c:if></td>
	            </tr>
	            <tr id="inquiry-content" style="display: none;">
	                <td colspan="6">
	                    <div>${ilist.content }</div>
	                    <div class="text-end mt-2">
	                    	<c:if test="${ilist.state == 0 }">
	                        <form action="/admin/inquiries" method="POST" style="display:inline;">
			                    <input type="hidden" name="idx" value="${ilist.idx}">
			                    <button type="submit" class="btn btn-success btn-small">回答完了</button>
			                </form>
	                        </c:if>
	                    </div>
	                </td>
	            </tr>
	        </c:forEach>
        </tbody>
    </table>
</section>

<%@ include file = "/all_component/footer.jsp" %>

<script>
    $(document).ready(function(){
        $(".inquiry-row").click(function() {
            var target = $(this).next(); // 다음 행을 선택
            target.toggle(); // 선택한 행의 내용만 토글
        });
    });
</script>
</body>
</html>

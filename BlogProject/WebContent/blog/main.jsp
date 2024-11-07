<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/all_component/allcss.jsp"%>
<title>共々</title>
<style>
.blog {
    width: 80%;
    margin: 15px auto;
    border-bottom: 1px solid;
    position: relative;
    display: flex;  /* Flexbox 사용 */
    flex-direction: column;  /* 세로 방향 정렬 */
    justify-content: space-between;  /* 요소 간의 간격 유지 */
}

.content-box {
    flex-grow: 1;  /* 남은 공간을 차지하도록 설정 */
}

.pagination {
    justify-content: center;
    text-align: center;
    margin: 20px 0 40px 0;
}

.pagination a {
    display: inline-block;
    margin: 0 5px;
    padding: 10px 15px;
    border: 1px solid #ccc;
    text-decoration: none;
    color: #333;
    border-radius: 5px;
}

.pagination a.active {
    background-color: #28a745;
    color: white;
    border: 1px solid #28a745;
}


.writer-box {
    display: flex;
    align-items: center;
}

.writer-box img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    border: 1px solid;
    object-fit: cover;
}

.nickname {
    margin-left: 10px;
    font-weight: bold;
    font-size: 1.2rem;
}

.visit-button {
    position: absolute;
    bottom: 10px;
    right: 10px;
    background-color: #009970;
    color: white;
    border: none;
    padding: 10px 15px;
    border-radius: 5px;
    cursor: pointer;
}

.visit-button:hover {
    background-color: #00b383;
}
</style>
</head>
<body>
<%@ include file="/all_component/header.jsp"%>
<div class="section" style="padding: 0; min-height: 0;">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="blog-card" style="width: 100%; height: 100%;">
				<c:forEach var="blogList" items="${blogList }">
					<div class="blog">
						<div class="wrap" style="padding-left: 20px;">
                        <div class="writer-box">
                            <img src="${blogList.profile_image != null ? blogList.profile_image : '/blogprofileimg/user.png'}">
                            <a href="/blog/home?nick=${blogList.nickname}" style="text-decoration: none; color: unset;"><span class="nickname">${blogList.nickname }のブログ</span></a>
                        </div>
                        <div class="content-box" style="width: 98%; height: 70%; padding: 20px 10px 10px 10px;">
                            <p>${blogList.blog_intro }</p>
                        </div>
                        <button class="visit-button" onclick="location.href='/blog/home?nick=${blogList.nickname}'">訪問する</button>
                        </div>
                    </div>
                </c:forEach>
			</div>
		</div>
		</div>
	</div>
</div>
		<div class="pagination">
			<c:if test="${page.hasPrevPage() }">
				<a href="?page=${page.getPrevPage() }">以前</a>
			</c:if>
			<c:forEach var="i" begin="${page.getStartPage() }" end="${page.getEndPage() }">
				<c:choose>
					<c:when test="${i == page.currentPage }">
						<a href="#" class="active">${i }</a>
					</c:when>
					<c:otherwise>
						<a href="?page=${i }">${i }</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${page.hasNextPage() }">
				<a href="?page=${page.getNextPage() }">次へ</a>
			</c:if>
		</div>

	<%@ include file="/all_component/footer.jsp"%>
</body>
</html>
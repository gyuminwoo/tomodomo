<%@page import="com.VO.BlogProfile"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.VO.UserDetails"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ブログのホーム｜共々</title>
<%@ include file = "/all_component/allcss.jsp" %>
<style>
    body {
        background-color: #f8f9fa;
    }
    
    .side-inner {
        background-color: #ffffff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        margin-bottom: 0; /* 푸터와 맞붙게 설정 */
        height: calc(100vh); /* 높이 조정 */
        overflow-y: auto; /* 스크롤 가능 */
    }
    .profile {
        text-align: center;
        margin-bottom: 30px;
    }
    .profile img {
        border-radius: 50%;
        width: 100px;
        height: 100px;
        object-fit: cover;
    }
    .name {
        font-size: 1.5rem;
        margin: 10px 0;
    }
    .country {
        color: #888;
    }
    .nav-menu ul {
        list-style: none;
        padding: 0;
    }
    .nav-menu li {
        margin: 10px 0;
    }
    .nav-menu a {
        text-decoration: none;
        color: #333;
        font-weight: normal;
    }
    .nav-menu a:hover {
        color: #007bff;
    }
    .active a {
        color: #007bff;
    }
    .nav-menu .submenu {
    	list-style: none;
    	padding-left: 20px;
    	margin-top: 5px;
    }
    .bold {
    	font-weight: bold;
    }
    .pagination {
    	justify-content: center;
        text-align: center;
        margin: 80px auto;
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
        background-color: #28a745; /* 활성 페이지 색상 */
        color: white;
        border: 1px solid #28a745;
    }
</style>
</head>
<body>
<%@ include file = "/all_component/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <div class="side-inner">
                <div class="profile">
                    <img src="${bp.profile_image != null ? bp.profile_image : '/blogprofileimg/user.png'}" alt="Image" class="img-fluid">
                    <h3 class="name">${nick }</h3>
                    <c:if test="${bp.myPageCheck }">
                    <a href="/blog/edit" class="country">ブログ管理</a>
                    </c:if>
                </div>

                <div class="nav-menu">
                    <ul>
                        <li>
                            <a href="/blog/home?nick=${bp.nickname }" style="font-weight: bold;"><span class="icon-posts mr-3"></span>全投稿</a>
                            <ul class="submenu">
                            <c:if test="${cateList != null}">
                            <c:forEach var="cateList" items="${cateList}">
                                <li><a href="/blog/home?nick=${bp.nickname }&tname=${cateList.category }"><i class="fa-regular fa-file-lines"></i>${cateList.category }</a></li>
                            </c:forEach>
                            </c:if>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="col-md-9" style="margin-left: 3.5%;">
    <div class="post-card" style="width: 100%; height: 100%;">
        <c:choose>
            <c:when test="${empty posts && bp.myPageCheck }">
                <div style="text-align: center; padding: 50px 0;">
                    <img src="/img/nopost.jpg" alt="No Posts" style="width: 50%; height: auto; border: 1px solid #ccc; border-radius: 8px;"/>
                    <h2 style="margin-top: 20px;">投稿がありません。<br>新しい投稿を作成してください！</h2>
                    <a href="/blog/addpost" class="btn btn-primary" style="padding: 10px 15px; text-decoration: none; color: white; background-color: #007bff; border-radius: 5px; margin-top: 20px;">書く</a>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="post" items="${posts}">
                    <div class="post" style="width: 80%; height: 20%; float: left; padding: 15px 0; border-bottom: 1px solid; overflow: hidden;">
                        <div class="title-box" style="width: 98%; height: 30%; padding: 5px;">
                            <h2><a href="javascript:void(0);" style="text-decoration: none; color: unset;" onclick="submitPost(${post.idx});">${post.title}</a></h2>
                        </div>
                        <div class="content-box" style="width: 98%; height: 70%; padding: 10px;">
                            <p><a href="javascript:void(0);" style="text-decoration: none; color: unset;" onclick="submitPost(${post.idx});">${post.content }</a></p>
                        </div>
                    </div>
                    <div class="img-box" style="width: 17%; height: 20%; float: left; border-bottom: 1px solid; padding: 15px 0;">
                        <c:if test="${post.fileName != null }">
                            <img src="${post.fileName}" style="width: 95%; height: 100%; padding: 1%; border: 1.5px ridge;">
                        </c:if>
                    </div>
                </c:forEach>
                <div style="position: relative; width: 100%; margin-top: 10px; clear: both;">
                <c:if test="${bp.myPageCheck}">
                    <a href="/blog/addpost" class="btn btn-primary" style="padding: 10px 15px; text-decoration: none; color: white; background-color: #007bff; border-radius: 5px; position: absolute; right: 42px; bottom: -60px;">書く</a>
                </c:if>
                </div>
                <div class="pagination" style="width: 100%; height: 5%;">
                <c:if test="${tname == null }">
				    <c:if test="${page.hasPrevPage()}">
				        <a href="?nick=${bp.nickname }&page=${page.getPrevPage()}">以前</a>
				    </c:if>
				    
				    <c:forEach var="i" begin="${page.getStartPage()}" end="${page.getEndPage()}">
				        <c:choose>
				            <c:when test="${i == page.currentPage}">
				                <a href="#" class="active">${i}</a>
				            </c:when>
				            <c:otherwise>
				                <a href="?nick=${bp.nickname }&page=${i}">${i}</a>
				            </c:otherwise>
				        </c:choose>
				    </c:forEach>
				    <c:if test="${page.hasNextPage()}">
				        <a href="?nick=${bp.nickname }&page=${page.getNextPage()}">次へ</a>
				    </c:if>
				</c:if>
				<c:if test="${tname != null }">
					<c:if test="${page.hasPrevPage()}">
				        <a href="?nick=${bp.nickname }&tname=${tname }&page=${page.getPrevPage()}">以前</a>
				    </c:if>
				    
				    <c:forEach var="i" begin="${page.getStartPage()}" end="${page.getEndPage()}">
				        <c:choose>
				            <c:when test="${i == page.currentPage}">
				                <a href="#" class="active">${i}</a>
				            </c:when>
				            <c:otherwise>
				                <a href="?nick=${bp.nickname }&tname=${tname }&page=${i}">${i}</a>
				            </c:otherwise>
				        </c:choose>
				    </c:forEach>
				    <c:if test="${page.hasNextPage()}">
				        <a href="?nick=${bp.nickname }&tname=${tname }&page=${page.getNextPage()}">次へ</a>
				    </c:if>
				</c:if>
				</div>
            </c:otherwise>
        </c:choose>
    </div>
</div>


    </div>
</div>
<%@ include file = "/all_component/footer.jsp" %>
</body>

<script>
	function submitPost(postIdx) {
		var form = document.createElement('form');
		form.method = 'POST';
		form.action = '/blog/post';
		
		var input = document.createElement('input');
		input.type = 'hidden';
		input.name = 'idx';
		input.value = postIdx;
		
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
	}
</script>

</html>

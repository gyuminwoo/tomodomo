<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>共々</title>
    <style>
        .split-section {
            display: flex;
            justify-content: space-between;
            padding: 40px 20px;
        }

        .split-section > div {
            width: 48%;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
        }

        .blog-item, .post-item {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }

        .blog-logo, .post-image {
            width: 50px;
            height: 50px;
            background-color: #ccc;
            border-radius: 50%;
            margin-right: 15px;
        }

        .blog-info, .post-info {
            flex-grow: 1;
        }

        .blog-name, .post-title {
            font-weight: bold;
            font-size: 18px;
            margin-bottom: 5px;
        }

        .blog-intro, .post-content {
            font-size: 14px;
            margin-bottom: 5px;
            color: #555;
        }

        .blogger-name, .post-theme {
            font-size: 12px;
            color: #777;
        }
        .indexa {
        	text-decoration: none;
        	color: unset;
        }
    </style>
    <%@ include file="/all_component/allcss.jsp" %>
</head>
<body>
<%@ include file="/all_component/header.jsp" %>

<section class="container-fluid back-img" style="padding-top: 80px; position: relative; height: calc(100vh - 80px);">
    <div class="blogstart">
        <h1 style="color: #333;">知識、<br>共に積み重ねましょう！</h1>
        <a href="<%= user != null ? "/blog/home?nick=" + user.getNickname() : "/signup" %>" class="btn btn-secondary" style="margin-top: 10px; margin-left: 120px; padding: 10px 20px; background-color: #007bff; border: none; border-radius: 5px; color: white; text-decoration: none;"><c:if test="${userD == null }">ブログをはじめる</c:if><c:if test="${userD != null }">マイブログへ</c:if></a>
    </div>
</section>

<section class="container split-section">
    <div>
        <h2 style="margin-bottom: 9%; text-align: center;">よく探しているブログ</h2>
        <c:forEach var="blogList" items="${blogList }">
        <a href="/blog/home?nick=${blogList.nickname }" class="indexa">
        <div class="blog-item" style="border: 1px solid; padding: 1%; border-radius: 10px;">
            <div class="blog-logo">
            	<img src="${blogList.image != null ? blogList.image : '/blogprofileimg/user.png' }" alt="Blog Logo" style="width: 100%; height: 100%; border-radius: 50%;">
            </div>
            <div class="blog-info">
                <div class="blog-name">${blogList.nickname }のブログ</div>
                <div class="blog-intro">${blogList.intro != null ? blogList.intro : '(ブログの紹介がありません)' }</div>
            </div>
        </div>
        </a>
        </c:forEach>
    </div>

    <div>
        <h2 style="margin-bottom: 6%; text-align: center;">人気ポスト</h2>
        <c:forEach var="postList" items="${postList }">
        <a href="#" class="indexa" onclick="submitPost(${postList.pidx}); return false;">
        <div class="post-item" style="border: 1px solid; padding: 1%; border-radius: 10px;">
            <div class="post-image">
            	<img src="${postList.filename != null ? postList.filename : '/blogprofileimg/noimage.jpg' }" alt="Post Image" style="width: 100%; height: 100%; border-radius: 50%;">
            </div>
            <div class="post-info">
                <div class="post-title">${postList.title }</div>
                <div class="post-theme">テーマ:   ${postList.theme }</div>
                <div class="blogger-name">ニックネーム：  ${postList.nickname }</div>
            </div>
            <div class="post-icons">
		        <i class="fa-regular fa-eye"></i>${postList.viewcount}
		        <i class="fa-solid fa-heart"></i>${postList.suggest}
		    </div>
        </div>
        </a>
        </c:forEach>
    </div>
</section>

<%@ include file="/all_component/footer.jsp" %>
<script>
    function submitPost(pidx) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '/blog/post';

        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'idx';
        input.value = pidx;

        form.appendChild(input);
        document.body.appendChild(form);
        form.submit();
    }
</script>
</body>
</html>
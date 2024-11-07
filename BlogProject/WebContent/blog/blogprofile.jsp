<%@page import="com.VO.BlogProfile"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>共々</title>
    <%@ include file="/all_component/allcss.jsp" %>
    <style>
        .btn-upload {
            display: block;
            width: auto;
            margin: 1rem auto;
        }
        .profile-image-container {
            width: 120px;
            height: 120px;
            overflow: hidden;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0 auto;
            background-color: #f0f0f0;
        }
        .img-account-profile {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .section {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .card {
            margin: 1rem auto;
        }
        .profile-card {
            width: 300px;
        }
        .details-card {
            width: 600px;
            height: 330px;
        }
        .profile-card .card-body {
            padding: 1rem;
        }
        .details-card .card-body {
            padding: 0;
        }
        .details-card form {
            padding: 1.5rem;
        }
        .details-card form .mb-3 {
            width: 100%;
            margin-bottom: 1rem;
        }
        .details-card form textarea, 
        .details-card form input {
            font-size: 0.9rem;
            padding: 0.6rem;
            width: 100%;
            border-radius: 0.25rem;
        }
        .details-card form label {
            font-size: 0.85rem;
            margin-bottom: 0.5rem;
            display: block;
        }
        .details-card form .btn {
            width: 150px;
            margin-top: 1rem;
            padding: 0.5rem;
            font-size: 0.9rem;
            display: block;
            margin-left: auto;
            margin-right: auto;
        }
        #inputBlogIntro {
            resize: none;
            height: 80px;
        }
    </style>
</head>
<body>
<%@ include file="/all_component/header.jsp" %>
<% if(user == null){ response.sendRedirect("/"); } %>
<%
	BlogProfile bp = (BlogProfile) request.getAttribute("bp");
	if(bp != null) {
		System.out.println(bp.getBlog_intro());
		System.out.println(bp.getProfile_image());
	}
%>
<div class="section">
    <div class="container-xl px-4 mt-4">
        <div class="card profile-card mb-4">
            <div class="card-header">ブログロゴ</div>
            <div class="card-body text-center">
                <div class="profile-image-container">
                    <img id="profileImage" class="img-account-profile rounded-circle" src="<%=bp.getProfile_image() %>" alt="">
                </div>
                <button class="btn btn-primary btn-upload" type="button" onclick="document.getElementById('profileImageInput').click();">イメージ変更</button>
            </div>
        </div>
        <div class="card details-card mb-4">
            <div class="card-header">ブログ紹介</div>
            <div class="card-body">
                <form method="post" action="/blog/editpro" enctype="multipart/form-data"> <!-- Specify the action URL -->
                    <div class="mb-3">
                        <label class="small mb-1" for="inputNickname">ニックネーム</label>
                        <input class="form-control" id="inputNickname" type="text" value="${userD.nickname}" disabled>
                    </div>
                    <div class="mb-3">
                        <label class="small mb-1" for="inputBlogIntro">ブログの紹介</label>
                        <textarea class="form-control" id="inputBlogIntro" name="blog_intro" placeholder="最大100文字まで入力できます。" maxlength="100" oninput="limitLineBreaks(this)"><%=bp.getBlog_intro() %></textarea>
                    </div>
                    <input type="file" name="profile_image" id="profileImageInput" style="display: none;" accept="image/*" onchange="previewImage(event)"> <!-- Hidden file input for profile image -->
                    <button class="btn btn-primary" type="submit">変更を保存</button>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="/all_component/footer.jsp" %>

<script>
function previewImage(event) {
    let fileo = event.target.files[0];
    let profileImage = document.getElementById('profileImage');
	console.log(fileo);
	console.log(profileImage);
    if (fileo) {
        let reader = new FileReader();
        reader.onload = function(e) {
            profileImage.src = e.target.result;
        }
        reader.readAsDataURL(fileo);
    }
}

function limitLineBreaks(textarea) {
    let lines = textarea.value.split('\n');
    if (lines.length > 3) {
        textarea.value = lines.slice(0, 3).join('\n');
    }
}
</script>

</body>
</html>

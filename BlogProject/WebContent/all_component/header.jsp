<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.VO.UserDetails" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	HttpSession cSession = request.getSession(false);
    UserDetails user = (cSession != null) ? (UserDetails)cSession.getAttribute("userD") : null;
	String blogUrl = "/signup";
	if(user != null) {blogUrl = "/blog/home?nick=" + user.getNickname();}
%>
<header>
    <nav class="navbar navbar-expand-lg common-margin">
        <div class="container-fluid">
            <a class="navbar-brand ms-5" href="/"><i class="fa-solid fa-users-rectangle"></i> 共々</a>
            <button class="navbar-toggler pe-0" type="button" 
                    data-bs-toggle="offcanvas" 
                    data-bs-target="#offcanvasNavbar" 
                    aria-controls="offcanvasNavbar" 
                    aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="offcanvasNavbarLabel"><i class="fa-solid fa-users-rectangle"></i> 共々</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <div class="offcanvas-body">
                    <ul class="navbar-nav justify-content-center flex-grow-1 pe-3">
                        <li class="nav-item">
                            <a class="nav-link mx-lg-2 active" aria-current="page" href="/"><i class="fa-solid fa-house"></i> ホーム</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link mx-lg-2" href="<%= blogUrl%>"><i class="fa-solid fa-child-reaching"></i>
                            <%= user != null ? user.getNickname() + "のブログ" : "ブログ" %></a>
                        </li>
                        <% if(user != null) { %>
                        <li class="nav-item">
                            <a class="nav-link mx-lg-2" href="/blog/addpost"><i class="fa-solid fa-square-pen"></i> ブログを書く</a>
                        </li>
                        <% } %>
                        <li class="nav-item">
						    <a class="nav-link mx-lg-2" href="/blog/main"><i class="fa-solid fa-earth-asia"></i> 他のブログ</a>
						</li>
                        <li class="nav-item">
						    <a class="nav-link mx-lg-2" href="#" data-bs-toggle="modal" data-bs-target="#searchModal">
						        <i class="fa-solid fa-magnifying-glass"></i> 検索
						    </a>
						</li>
						<c:if test="${userD != null }">
							<a class="nav-link mx-lg-2" href="/inquiry"><i class="fa-regular fa-envelope"></i> お問い合わせ</a>
						</c:if>
                    </ul>
                    <div class="d-lg-none">
                        <ul class="navbar-nav">
                            <% if (user != null) { %>
                            	<% if (user.getAuth() == 0){ %>
                                <li class="nav-item">
                                    <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#userInfoModal"><i class="fa fa-user-edit" aria-hidden="true"></i> プロフィール</a>
                                </li>
                                <%} else { %>
                                <li class="nav-item">
                                    <a class="nav-link" href="/admin/inquiries"><i class="fa-solid fa-user-tie"></i> 管理ページ</a>
                                </li>
                                <%} %>
                                <li class="nav-item">
                                    <a class="nav-link" href="/logout"><i class="fa fa-sign-out" aria-hidden="true"></i> ログアウト</a>
                                </li>
                            <% } else { %>
                                <li class="nav-item">
                                    <a class="nav-link" href="/signup"><i class="fa-solid fa-user-plus"></i> 新規登録</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="/login"><i class="fa fa-sign-in" aria-hidden="true"></i> ログイン</a>
                                </li>
                            <% } %>
                        </ul>
                    </div>
                </div>
            </div>
            <% if (user != null) { %>
            	<% if(user.getAuth() == 0) { %>
                <a href="#" class="btn btn-primary me-2 d-none d-lg-inline" data-bs-toggle="modal" data-bs-target="#userInfoModal"><i class="fa fa-user-edit" aria-hidden="true"></i> プロフィール</a>
                <%} else { %>
                <a href="/admin/inquiries" class="btn btn-primary me-2 d-none d-lg-inline"><i class="fa-solid fa-user-tie"></i> 管理ページ</a>
                <%} %>
                <a href="/logout" class="btn btn-danger me-3 d-none d-lg-inline"><i class="fa fa-sign-out" aria-hidden="true"></i> ログアウト</a>
            <% } else { %>
                <a href="/signup" class="login-button me-2 d-none d-lg-inline"><i class="fa-solid fa-user-plus"></i> 新規登録</a>
                <a href="/login" class="login-button login-button-alt me-3 d-none d-lg-inline"><i class="fa fa-sign-in" aria-hidden="true"></i> ログイン</a>
            <% } %>
        </div>
    </nav>
</header>

<div class="modal fade" id="userInfoModal" tabindex="-1" aria-labelledby="userInfoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="userInfoModalLabel"><%= user != null ? user.getNickname() + "のプロフィール" : "プロフィール" %></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            <div class="mb-2">
                <div class="d-flex justify-content-between align-items-center">
                    <label for="userId" class="form-label">ID</label>
                    <small class="text-danger mb-0" style="cursor: pointer;" data-bs-toggle="modal" data-bs-target="#deleteAccountModal">会員退会</small>
                </div>
                    <input type="text" class="form-control" id="userId" value="<%= user != null ? user.getId() : "" %>" readonly>
                </div>
                <div class="mb-2">
                    <label for="userPassword" class="form-label">パスワード</label>
                    <input type="text" class="form-control" id="userPassword" value="********" readonly>
                </div>
                <div class="mb-2">
                    <label for="userNickname" class="form-label">ニックネーム</label>
                    <input type="text" class="form-control" id="userNickname" value="<%= user != null ? user.getNickname() : "" %>" readonly>
                </div>
            </div>
            <div class="modal-footer justify-content-center">
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#changeModal">変更</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
            </div>
        </div>
    </div>
</div>

<!-- modal -->
<div class="modal fade" id="deleteAccountModal" tabindex="-1" aria-labelledby="deleteAccountModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteAccountModalLabel">会員退会</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body text-center">
                <p style="margin: 0 auto;">本当に退会しますか？</p>
            </div>
            <div class="modal-footer justify-content-center">
                <form action="/deleteAccount" method="post">
                    <button type="submit" class="btn btn-danger">退会する</button>
                </form>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">キャンセル</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="searchModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="searchModalLabel">検索</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
<div class="modal-body">
    <div class="d-flex">
        <select id="searchOption" class="form-select me-2" style="width: auto;">
            <option value="titlecontent">題目＋内容</option>
            <option value="author">作成者</option>
        </select>
        <input type="text" id="searchInput" class="form-control" placeholder="キーワードを入力" style="flex-grow: 1;" oninput="performSearch()">
    </div>
    <div id="searchResults" class="mt-3"></div>
    <div id="pagination" class="mt-3 d-flex justify-content-center" style="align-items: center;"></div>
</div>
            <div class="modal-footer justify-content-center">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="changeModal" tabindex="-1" aria-labelledby="changeModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changeModalLabel">プロフィール変更</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body text-center">
                <button class="btn btn-primary me-2" data-bs-toggle="modal" data-bs-target="#changePasswordModal">パスワード</button>
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#changeNicknameModal">ニックネーム</button>
            </div>
            <div class="modal-footer justify-content-center">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="changeNicknameModal" tabindex="-1" aria-labelledby="changeNicknameModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changeNicknameModalLabel">ニックネーム変更</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            	<form id="nicknameChangeForm" action="/change" method="post">
	                <div class="mb-3">
	                    <label for="newNickname" class="form-label">新しいニックネーム</label>
	                    <input type="text" class="form-control" id="newNickname" name="nickname" oninput="checkNickname()" maxlength="16">
	                    <small id="nicknameFeedback" class="text-danger"></small>
	                </div>
		            <div class="modal-footer justify-content-center">
		                <button type="submit" class="btn btn-primary" id="changeNicknameButton" data-bs-dismiss="modal" disabled>変更</button>
		                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
		            </div>
            	</form>
	        </div>
	    </div>
	</div>
</div>

<div class="modal fade" id="changePasswordModal" tabindex="-1" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changePasswordModalLabel">パスワード変更</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            	<form id="passwordChangeForm" action="/change" method="post">
	                <div class="mb-3">
	                    <label for="newPassword" class="form-label">新しいパスワード</label>
	                    <input type="password" class="form-control" id="newPassword" name="password" maxlength="20" onblur="checkPasswordMatch()">
	                </div>
	                <div class="mb-3">
	                    <label for="confirmPassword" class="form-label">パスワード確認</label>
	                    <input type="password" class="form-control" id="confirmPassword" name="passwordC" maxlength="20" oninput="checkPasswordMatch()">
	                    <small id="newPasswordFeedback" class="text-danger"></small>
	                </div>
		            <div class="modal-footer justify-content-center">
		                <button type="submit" class="btn btn-primary" id="changePasswordButton" data-bs-dismiss="modal" disabled>変更</button>
		                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
		            </div>
            	</form>
	        </div>
	    </div>
	</div>
</div>
<!-- modal end -->

<script>
function checkNickname() {
    var nickname = $('#newNickname').val();
    var feedback = $('#nicknameFeedback');
    var button = $('#changeNicknameButton');
    feedback.text('');

    if (nickname.length > 16) {
        feedback.text("ニックネームは16文字以内で使用してください");
        button.prop('disabled', true);
        return;
    }

    $.ajax({
        url: '/checkDuplicate',
        type: 'POST',
        data: { type: 'nickname', value: nickname },
        success: function(data) {
        	if (data == '使用できないニックネームです') {
                feedback.text(data);
                button.prop('disabled', true);
            } else {
                feedback.text('');
                button.prop('disabled', false);
            }
        },
        error: function() {
            feedback.text("エラーが発生しました。");
            button.prop('disabled', true);
        }
    });
}

function checkPasswordMatch() {
	var newPassword = $('#newPassword').val();
	var confirmPassword = $('#confirmPassword').val();
	var feedback = $('#newPasswordFeedback');
	var button = $('#changePasswordButton');
	
	feedback.text('');
	
	if(newPassword != confirmPassword) {
		feedback.text("パスワードが一致しません");
		button.prop('disabled', true);
	} else {
		feedback.text('');
		button.prop('disabled', false);
	}
}

function performSearch(page = 1) {
    let keyword = $('#searchInput').val();
    let type = $('#searchOption').val();

    if (keyword.length > 0) {
        $.ajax({
            type: 'POST',
            url: '/search',
            data: {
                keyword: keyword,
                type: type,
                page: page
            },
            dataType: 'json',
            success: function(response) {
                let resultHtml = '';
                let paginationHtml = '';

                if (response.posts.length > 0) {
                    response.posts.forEach(function(post) {
                        resultHtml +=
                            '<div class="d-flex justify-content-between align-items-center">' +
                                '<form action="/blog/post" method="POST">' +
                                    '<input type="hidden" name="idx" value="' + post.idx + '">' +
                                    '<button type="submit" class="btn btn-link p-0 text-start" style="text-decoration: none; color: unset;">' +
                                        '<strong>' + post.title + '</strong>' +
                                    '</button>' +
                                '</form>' +
                                '<span>' + post.nickname + '</span>' +
                            '</div>';
                    });

                    for (let i = 1; i <= response.totalPages; i++) {
                        if (i === response.currentPage) {
                        	paginationHtml += '<span class="current-page" style="color: blue; font-weight: bold;">' + i + '</span>'; // 현재 페이지를 파란색으로 표시
                        } else {
                            paginationHtml += '<button class="btn btn-link" style="text-decoration: none; color: black;" onclick="performSearch(' + i + ')">' + i + '</button>';
                        }
                    }
                } else {
                    resultHtml = '<p>結果が見つかりません。</p>';
                }
                $('#searchResults').html(resultHtml);
                $('#pagination').html(paginationHtml); 
            },
            error: function() {
                $('#searchResults').html('<p>検索結果が見つかりません。</p>');
                $('#pagination').html('');
            }
        });
    } else {
        $('#searchResults').html('');
        $('#pagination').html('');
    }
}

</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>投稿 | 共々</title>
<%@ include file = "/all_component/allcss.jsp" %>
<style>
small {
    font-style: normal;
}
.deco-none {
    text-decoration: none;
    color: black;
}
.writer-th {
    padding-right: 10px;
}
.comment-btn {
    padding: 10px 15px;
    cursor: pointer;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 16px;
}
.comment-btn:hover {
    background-color: #45a049;
}
.comment-section, .comment-list {
    display: none;
    margin-top: 20px;
}
.comment-box, .reply-box {
    padding: 10px;
    border: 1px solid #ccc;
    background-color: #f9f9f9;
}
.modify-btn, .delete-btn {
    background-color: #f44336;
    color: white;
    padding: 5px 10px;
    border: none;
    cursor: pointer;
}
.modify-btn:hover, .delete-btn:hover {
    background-color: #d32f2f;
}
.reply-btn {
    background-color: #4CAF50;
    color: white;
    padding: 5px 10px;
    cursor: pointer;
    margin-left: 20px;
}
.reply-btn:hover {
    background-color: #45a049;
}
.post-comment {
    margin-top: 410px;
}

hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #FFFFFF;
}
.blog-comment::before,
.blog-comment::after{
    content: "";
    display: table;
    clear: both;
}
.blog-comment {
    background-color: mistyrose;
    display: none;
    padding-left: 4%;
    padding-right: 12%;
}
.blog-comment ul{
    list-style-type: none;
    padding: 0;
}
.blog-comment img.avatar {
    position: relative;
    float: left;
    margin: 17px 12px 0 8px;
    width: 65px;
    height: 65px;
    -webkit-border-radius: 50%;
    -moz-border-radius: 50%;
    border-radius: 50%;
}
.blog-comment .post-comments {
    border: 1px solid #eee;
    margin-bottom: 20px;
    padding: 10px 20px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    background: #fff;
    color: #6b6e80;
}
.blog-comment .meta {
    font-size: 13px;
    color: #aaaaaa;
    padding-bottom: 8px;
    margin-bottom: 10px !important;
    border-bottom: 1px solid #eee;
}

.blog-comment .post-comments i.pull-right {
    float: right;
    margin-left: 10px;
}
.blog-comment .post-comments i.pull-right a {
    color: #6b6e80;
    font-size: 12px;
}
.blog-comment .post-comments i.pull-right a:hover {
    color: #82b440;
}

.reply-comments {
    margin-left: 30px;
    border-left: 1px dashed #ccc;
    padding-left: 10px;
}

.paginationn nav {
    display: flex;
    justify-content: center;
    align-items: center;
}

.paginationn .pagination {
    justify-content: center; /* 페이지네이션 중앙 정렬 */
}

</style>
</head>
<body>
<%@ include file = "/all_component/header.jsp" %>

<div class="detail-section" style="width:100%;background:#CADEDA;overflow-wrap: break-word; min-height: 100vh;">
    <div class="post-theme bg-white" style="height: 65px;margin: 3% 16.6% 1% 16.6%;padding: 1% 1% 1% 1%;display: flex;justify-content: flex-start;align-items: center;">
        <a href="/blog/home?nick=${pvo.nickname }&tname=${pvo.theme }" style="text-decoration: none; color: unset;"><span>テーマ:  ${pvo.theme }</span></a>
    </div>

    <div class="post-detail col-md-8 bg-white" style="margin: 0 16.6% 0 16.6%; min-height: 80vh;">
        <div class="post-header mb-3">
            <div class="title-box" style="padding-top: 60px;padding-left: 50px;">
                <h3 class="title">${pvo.title }</h3>
            </div>
            <div class="sub-box" style="display: flex;align-items: center;justify-content: space-between;width: 90%;border-bottom: solid 1px;margin-left: 50px;padding-top: 30px; padding-bottom: 10px;">
                <div class="writer-box">
                    <table>
                    <thead>
                    <th class="writer-th"><a class="deco-none" href="/blog/home?nick=${pvo.nickname }"><img src="${pvo.image != null ? pvo.image : '/blogprofileimg/user.png'}" alt="" style="width:30px;height:30px;border-radius:50%;"></a></th>
                    <th class="writer-th"><a class="deco-none" href="/blog/home?nick=${pvo.nickname }">${pvo.nickname }</a></th>
                    <th class="writer-th"><i class="fa-regular fa-calendar" style="margin-right: 8px;"></i>${pvo.postdate }</th>
                    <th class="writer-th"><i class="fa-regular fa-eye" style="margin-right: 5px;"></i>${pvo.viewcount }</th>
                    </thead>
                    </table>
                </div>
                <div class="option-box">
                    <a class="urlCopy deco-none" href="javascript:copyURL();"><i class="fa-solid fa-wand-magic-sparkles"></i>URL COPY</a>
                </div>
            </div>
        </div>

        <div class="post-body bg-white" style="padding: 15px 70px 70px 60px;">
        ${pvo.content }
        </div>

        <div class="post-comment bg-white">
            <div class="option-box" style="display: flex; justify-content: space-between; padding: 10px 20px;">
                <button class="comment-btn btn btn-outline-success" onclick="toggleCommentSection()">コメント</button>
                <c:if test="${userD != null }">
                <div style="margin: 0 auto; text-align: center;">
		            <button class="btn btn-outline-danger" id="heartButton" onclick="toggleHeartAndSend()">
					    <i class="${likeState == 0 ? 'fa-regular fa-heart' : 'fa-solid fa-heart' }" id="heartIcon"></i>
					</button>
		        </div>
		        </c:if>
                <div style="margin-top: 5px;">
                <c:if test="${myPageCheck == true }">
                    <button type="button" class="btn btn-outline-secondary" onclick="modifypost(${pvo.pidx})">修正</button>
                    <button type="button" class="btn btn-outline-secondary" onclick="deletepost()">削除</button>
                </c:if>
                </div>
            </div>
            <div id="commentSection" class="comment-section">
            <c:if test="${userD != null }">
                <div class="comment-box">
                    <textarea id="commentContent" name="commentContent" style="width: 100%; height: 80px; resize: none;" placeholder="（コメント）" maxlength="150"></textarea>
                    <button class="btn btn-secondary btn-sm" style="float: right; margin-top: 25px;" onclick="commentInsert()">コメント作成</button>
                </div>
            </c:if>
            </div>
            <!-- Comment List -->
            <div class="blog-comment">
                <hr/>
                <ul class="comments">
              	
                </ul>
            	<div id="paginationn" class="paginationn"></div>
            </div>
        </div>
    </div>
    <div class="bg-white col-md-8" id="btn-section" style="margin:1% 16.6%;display:flex;height: 45px;align-items: center;">
        	<div id="prev-div" class="col-md-6">
        		<c:if test="${pvo.prevTitle != null }"><a id="prev-link" href="javascript:void(0);" onclick="submitPost(${pvo.prevIdx})" style="text-decoration: none; color: dimgray;"><i class="fa-solid fa-arrow-left" style="margin:0 1% 0 4%;"></i><span>${pvo.prevTitle }</span></a></c:if>
        		<c:if test="${pvo.prevTitle == null }"><span style="margin:0 1% 0 4%;">以前の投稿がありません</span></c:if>
        	</div>
        	<div id="next-div" class="col-md-6">
        		<c:if test="${pvo.nextTitle != null }"><a id="next-link" href="javascript:void(0);" onclick="submitPost(${pvo.nextIdx})" style="float:right;text-decoration: none; color: dimgray;"><span>${pvo.nextTitle }</span><i class="fa-solid fa-arrow-right" style="margin:0 26px 0 6px;"></i></a></c:if>
        		<c:if test="${pvo.nextTitle == null }"><span style="margin:0 26px 0 6px; float: right;">次の投稿がありません</span></c:if>
        	</div>
        </div>
</div>

<%@ include file = "/all_component/footer.jsp" %>
<script>

$(document).ready(function() {
    loadComments(1);
});

function loadComments(page) {
    var pidx = '${pvo.pidx}';
    $.ajax({
        url: '/commentview',
        type: 'GET',
        data: {
            pidx: pidx,
            page: page
        },
        success: function(response) {
            renderComments(response.comments);
            setupPagination(response.totalPages, page);
        },
        error: function() {
            alert('コメントERROR！');
        }
    });
}

function renderComments(comments) {
    var commentsList = $('.comments');
    commentsList.empty();

    comments.forEach(function(comment) {
    	var uidx = '${userD.idx}';
    	var commentHtml = `
    	    <li class="clearfix" style="margin-left: ` + (comment.cclass === 1 ? '40px' : '0') + `;">
    	        <img src="` + (comment.image != null ? comment.image : '/blogprofileimg/user.png') + `" class="avatar" alt="">
    	        <div class="post-comments" data-cidx="` + comment.cidx + `" data-cclass="` + comment.cclass + `" data-cgroupnum="` + comment.cgroupnum + `">
    	            <p class="meta">
    	            <input type="hidden" class="commentGroupNum" value=` + comment.cgroupnum + `>
    	                <a href="/blog/home?nick=` + comment.nickname + `">` + comment.nickname + `</a> ` + comment.cdate + `: 
    	                <c:if test="${userD != null}">
    	                <i class="pull-right">
    	                ` + (comment.uidx != uidx ? '' : 
    	                    `<a href="javascript:;" onclick="deleteReply(this)"><small>削除</small></a>`) + `
    	                </i>
    	                ` + (comment.cclass === 1 ? '' : `<a href="javascript:;" class="pull-right" onclick="toggleReplySection(this)">Reply</a>`) + `
    	                </c:if>
    	            </p>
    	            <p>` + comment.comment + `</p>
    	            <div class="reply-box" style="display: none;"></div>
    	        </div>
    	    </li>`;
        commentsList.append(commentHtml);
    });
}

function setupPagination(totalPages, currentPage) {
    var paginationContainer = $('#paginationn');
    paginationContainer.empty();

    var nav = $('<nav aria-label="Page navigation"></nav>');
    var ul = $('<ul></ul>').addClass('pagination justify-content-center');

    var startPage = Math.floor((currentPage - 1) / 5) * 5 + 1;
    var endPage = Math.min(startPage + 4, totalPages);

    var prevPage = $('<li></li>').addClass('page-item' + (startPage === 1 ? ' disabled' : ''));
    var prevLink = $('<a></a>').addClass('page-link').attr('href', 'javascript:void(0);').text('以前');
    prevLink.on('click', function() {
        if (currentPage > 1) {
            var newPage = Math.max(startPage - 1, 1);
            loadComments(newPage);
        }
    });
    prevPage.append(prevLink);
    ul.append(prevPage);

    for (var i = startPage; i <= endPage; i++) {
        var pageLink = $('<li></li>').addClass('page-item' + (i === currentPage ? ' active' : ''));
        var pageAnchor = $('<a></a>').addClass('page-link').attr('href', 'javascript:void(0);').text(i);
        pageAnchor.on('click', function() {
            loadComments(parseInt($(this).text()));
        });
        pageLink.append(pageAnchor);
        ul.append(pageLink);
    }

    var nextPage = $('<li></li>').addClass('page-item' + (endPage === totalPages ? ' disabled' : ''));
    var nextLink = $('<a></a>').addClass('page-link').attr('href', 'javascript:void(0);').text('次へ');
    nextLink.on('click', function() {
        if (currentPage < totalPages) {
            var newPage = Math.min(endPage + 1, totalPages);
            loadComments(newPage);
        }
    });
    nextPage.append(nextLink);
    ul.append(nextPage);

    if (totalPages <= 5) {
        prevPage.hide();
        nextPage.hide();
    }

    nav.append(ul);
    paginationContainer.append(nav);
}

function modifypost(pidx) {
	var form = document.createElement('form');
	form.method = 'POST';
	form.action = '/blog/modify';
	
	var input = document.createElement('input');
	input.type = 'hidden';
	input.name = 'pidx';
	input.value = pidx;
	
	form.appendChild(input);
	document.body.appendChild(form);
	form.submit();
}

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

function deletepost() {
	var pidx = '${pvo.pidx}';
	var nickname = '${pvo.nickname}';
	if(confirm("本当に削除しますか？") == true) {
		location.href='/blog/postdelete?idx=' + pidx + '&nick=' + nickname;
	} else {
		return false;
	}
}

function toggleCommentSection() {
    var commentSection = document.getElementById('commentSection');
    var commentList = document.querySelector('.blog-comment');
    if (commentSection.style.display === 'none' || commentSection.style.display === '') {
        commentSection.style.display = 'block';
        commentList.style.display = 'block';
    } else {
        commentSection.style.display = 'none';
        commentList.style.display = 'none';
    }
}

function copyURL() {
    var dummy = document.createElement('textarea');
    document.body.appendChild(dummy);
    dummy.value = window.location.href;
    dummy.select();
    document.execCommand('copy');
    document.body.removeChild(dummy);
    alert('URLがコピーされました!');
}
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

function commentInsert() {
	var comment = $('#commentContent').val();
	
	if(comment.length > 150) {
		alert('コメントは１５０文字以内でください。');
		return false;
	}
	if(comment.trim().length == 0) {
		alert('内容を入力してください。');
		return false;
	}
	var pidx = '${pvo.pidx}';
	var uidx = '${userD.idx}';
	
	$.ajax({
		url: '/comment',
		type: 'POST',
		data: {
			comment: comment,
			pidx: pidx,
			uidx: uidx
		},
		success: function() {
			$('#commentContent').val('');
			loadComments(1);
		}
	});
}



function toggleReplySection(replyButton) {
    var allReplySections = document.querySelectorAll('.reply-box');
    for (var i = 0; i < allReplySections.length; i++) {
        allReplySections[i].style.display = 'none';
    }

    var replyBox = replyButton.closest('.post-comments').querySelector('.reply-box');

    if (replyBox) {
        replyBox.style.display = (replyBox.style.display === 'none' || replyBox.style.display === '') ? 'block' : 'none';
    }

    if (replyBox.style.display === 'block') {
        replyBox.innerHTML = `
            <textarea style="width: 100%; height: 60px; resize: none;" id="replyContent" placeholder="（コメ返し）" maxlength="150"></textarea>
            <button class="btn btn-secondary btn-sm" onclick="insertReply(this)">コメ返し</button>
        `;
    }
}

function insertReply(replyButton) {
	var replyContent = $(replyButton).closest('.post-comments').find('#replyContent').val();
	
    if (replyContent.length > 150) {
        alert('大コメントは150文字以内でください。');
        return false;
    }
    if (replyContent.trim().length == 0) {
        alert('内容を入力してください。');
        return false;
    }
	var pidx = '${pvo.pidx}';
	var uidx = '${userD.idx}';
	var cgr = $(replyButton).closest('.post-comments').find('.commentGroupNum').val();
	$.ajax({
		url: '/comment',
		type: 'POST',
		data: {
			comment: replyContent,
			pidx: pidx,
			uidx: uidx,
			groupNum: cgr
		},
		success: function() {
			$('#replyContent').val('');
			loadComments(1);
		}
	});

}

function deleteReply(deleteButton) {

	if(confirm("本当に削除しますか？") == true) {
	    var commentElement = $(deleteButton).closest('.post-comments');
	    var cclass = commentElement.data('cclass');
	    var cidx = commentElement.data('cidx');
	    var cgroupnum = commentElement.data('cgroupnum');
	    var pidx = '${pvo.pidx}';
	    
	    $.ajax({
	    	url: '/commentdelete',
	    	type: 'POST',
	    	data: {
	    		cclass: cclass,
	    		cidx: cidx,
	    		cgroupnum: cgroupnum,
	    		pidx: pidx
	    	},
	    	success: function() {
	    		loadComments(1);
	    	}
	    });
	} 
}

function toggleHeartAndSend() {
    let heartIcon = $('#heartIcon');
    let isLiked = heartIcon.hasClass('fa-solid') ? 0 : 1;
    if (!isLiked) {
        heartIcon.removeClass('fa-solid').addClass('fa-regular');
    } else {
        heartIcon.removeClass('fa-regular').addClass('fa-solid');
    }
	let pidx = '${pvo.pidx}';
	let uidx = '${userD.idx}';
	
    $.ajax({
        url: '/likecount',
        type: 'POST',
        data: {
        	isLiked: isLiked,
        	uidx: uidx,
        	pidx: pidx
        },
        success: function() {
        	console.log(1234);
        }
    });
}
</script>
</body>
</html>

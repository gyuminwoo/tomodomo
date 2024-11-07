<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.VO.UserDetails" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>書く｜共々</title>
<%@ include file = "/all_component/allcss.jsp" %>
<style>
    .note-icon-caret {
        display: none;
    }
    .note-editor.note-airframe .note-statusbar .note-resizebar, .note-editor.note-frame .note-statusbar .note-resizebar {
    	resize: none;
    	display: none;
    }
    .button-container {
    	display: flex;
    	justify-content: center;
    }
    .button-container button {
    	margin: 0 10px;
    	margin-bottom: 10px;
    }
    .note-editing-area {
    	border-top: 1px solid;
    }
    .is-hidden {
    	display: none;
    }
</style>
</head>
<body>
<%@ include file = "/all_component/header.jsp" %>
<% if(user == null) {response.sendRedirect("/"); return;} %>
    <div class="container">
        <form id="myForm" action="/blog/modifypro" method="post" onsubmit="return handleSubmit()">
            <div class="mb-3" style="margin-top: 5%;">
            	<div class="d-flex justify-content-between">
	                <label for="title" class="form-label">タイトル</label>
	            </div>
                <input type="text" class="form-control" id="title" name="title" value="${pvo.title }" placeholder="タイトルを入力してください">
            </div>
            <div class="mb-3">
                <div id="summernote"></div>
                <input type="hidden" name="content" id="content">
            </div>
            <div class="button-container mb-1">
                <button type="submit" class="btn btn-primary">編集する</button>
            </div>
        </form>
    </div>
    
<%@ include file = "/all_component/footer.jsp" %>
<script>
$(document).ready(function() {
    $('#summernote').summernote({
        height: 500,
        lang: 'ja-JP',
        toolbar: [
            ['font', ['fontname', 'fontsize', 'forecolor', 'bold', 'italic', 'underline', 'strikethrough']],
            ['para', ['paragraph', 'ul', 'ol']],
            ['insert', ['link', 'picture']]
        ],
        callbacks: {
            onChange: function(contents, $editable) {
                $('#content').val(contents);
            },
            onImageUpload: function(files) {
            	let editor = $(this);
            	for(let file of files) {
            		let reader = new FileReader();
            		reader.onload = function(e) {
            			editor.summernote('insertImage', e.target.result, function($image){
            				$image.css('max-width', '640px');
            				$image.css('height', 'auto');
            				$image.css('display', 'block');
            				$image.css('margin-bottom', '10px');
            			});
            		};
            		reader.readAsDataURL(file);
            	}
            }
        }
    });

    $('#summernote').summernote('code', '${pvo.content}');
});

function handleSubmit() {
    var title = document.getElementById('title').value;
    var content = document.getElementById('content').value;
    
    if (title == null || title.trim() === '') {
        alert('タイトルを入力してください');
        return false;
    }
    
    if (content == null || content.trim() === '') {
        alert('内容を入力してください');
        return false;
    }
    
    var pidx = '${pvo.idx}';
    console.log(pidx);
    var input = document.createElement('input');
	input.type = 'hidden';
	input.name = 'pidx';
	input.value = pidx;
	
	document.querySelector('#myForm').appendChild(input);
	
	
    return true;
}
</script>
</body>
</html>
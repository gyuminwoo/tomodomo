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
        <form action="/blog/addpostpro" method="post" enctype="multipart/form-data" onsubmit="return handleSubmit()">
            <div class="mt-1">
            	<div class="d-flex justify-content-between">
	                <label for="title" class="form-label">タイトル</label>
	            </div>
                <input type="text" class="form-control" id="title" name="title" placeholder="タイトルを入力してください">
            </div>
            <div class="mb-2">
            <div class="d-flex justify-content-between">
                <label for="category" class="form-label">テーマ</label>
                <a href="#" class="text-decoration-none" style="color: #868e96;" data-bs-toggle="modal" data-bs-target="#newThemeModal"><i class="fa-solid fa-plus"></i>新規テーマ追加</a>
            </div>
                <select class="form-select" id="category" name="categoryIdx">
                    <option value="">テーマを選んでください</option>
                    <c:forEach var="theme" items="${theme}">
                        <option value="${theme.categoryIdx}" data-category="${theme.category }">${theme.category}</option>
                    </c:forEach>
                </select>
                <input type="hidden" name="categoryName" id="categoryName">
            </div>
            <div class="mb-3">
                <div id="summernote"></div>
                <input type="hidden" name="content" id="content">
                <input type="file" name="filename" id="filename" style="display: none;">
            </div>
            <div class="button-container mb-1">
                <button type="submit" class="btn btn-primary" name="action" value="save">投稿する</button>
            </div>
        </form>
    </div>
    
    <div class="modal fade" id="newThemeModal" tabindex="-1" aria-labelledby="newThemeModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="newThemeModalLabel">テーマの作成</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id="addThemeForm" action="/theme" method="post">
				    <div class="modal-body">
				        <input type="text" maxlength="20" name="addcate" placeholder="テーマ名を入力してください" aria-label="テーマ名を入力してください。入力後エンターキーで決定します" class="form-control" id="js-themeTextBox" oninput="checkInput()">
				        <small class="text-danger is-hidden" id="js-themeModalError"></small>
				    </div>
				    <div class="modal-footer justify-content-center">
				        <button type="submit" class="btn btn-primary" id="js-themeModalAdd" disabled>追加</button>
				    </div>
				</form>
            </div>
        </div>
    </div>
    
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
                	for (let file of files) {
                		let reader = new FileReader();
                		reader.onload = function(e) {
                			editor.summernote('insertImage', e.target.result, function($image) {
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
        
        $('#loadDraftModal').on('click', '.loadDraftButton', function() {
            $('#title').val($(this).data('title'));
            $('#categoryName').val($(this).data('category'));
            $('#category').val($(this).data('category-idx'));

            $('#summernote').summernote('code', $(this).data('content'));
			
            var imageFileName = $(this).data('image');
            
            $('#loadDraftModal').modal('hide');
        });

        $('#js-themeTextBox').on('input', function() {
            checkInput();
        });

        $('#addThemeForm').on('submit', function(event) {
            var themeName = $('#js-themeTextBox').val().trim();
            var $error = $('#js-themeModalError');

            if (themeName === '') {
                event.preventDefault();
                $error.text('入力されたテーマ名がありません。').removeClass('is-hidden');
                $('#js-themeModalAdd').prop('disabled', true);
            } else {
                $error.addClass('is-hidden');
                $('#js-themeModalAdd').prop('disabled', false);
            }
        });
        
        $('#category').change(function() {
            var selectedOption = $(this).find('option:selected');
            $('#categoryName').val(selectedOption.data('category'));
        });
    });

    function checkInput() {
        var themeName = $('#js-themeTextBox').val().trim();
        var $error = $('#js-themeModalError');

        if (themeName === '') {
            $error.text('入力されたテーマ名がありません。').removeClass('is-hidden');
            $('#js-themeModalAdd').prop('disabled', true);
        } else {
            $error.addClass('is-hidden');
            $('#js-themeModalAdd').prop('disabled', false);
        }
    }
    
    function handleSubmit() {
    	if($('#category').val() == '') {
    		alert('テーマを選択してください！');
    		return false;
    	}
    	return true;
    }
    </script>
    
<%@ include file = "/all_component/footer.jsp" %>
</body>
</html>
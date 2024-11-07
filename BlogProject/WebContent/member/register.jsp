<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>新規登録 | 共々</title>
    <%@ include file="/all_component/allcss.jsp"%>
</head>
<body class="d-flex flex-column min-vh-100">
    <%@ include file="/all_component/header.jsp"%>
    <%
    if(user != null) {
        response.sendRedirect("/");
    } 
    %>
    <section class="container-fluid div-color flex-fill">
        <div class="row">
            <div class="col-md-4 offset-md-4">
                <div class="card mt-5 mb-4 regis">
                    <div class="card-header text-center">
                        <i class="fa fa-user-plus" aria-hidden="true"></i>
                        <h4>新規登録</h4>
                    </div>
                    <div class="card-body">
                        <form onsubmit="return validateForm(event);" action="/UserServlet" method="post">
                            <div class="form-group">
                                <label for="id">共々 ID</label>
                                <input type="text" class="form-control" id="id" name="id" oninput="checkId()" maxlength="16">
                                <small id="idFeedback" class="text-danger"></small>
                            </div>
                            <div class="form-group">
                                <label for="password">パスワード</label>
                                <input type="password" class="form-control" id="password" name="password" oninput="validatePass()" maxlength="20">
                                <small id="pwFeedback" class="text-success">パスワードは8文字以上20文字以下の英字と数字を含めてください。</small>
                                <small id="pwCheck" class="text-danger"></small>
                            </div>
                            <div class="form-group">
                                <label for="passwordConfirm">パスワード（確認）</label>
                                <input type="password" class="form-control" id="passwordC" name="passwordC" onblur="passwordCheck()" maxlength="20">
                                <small id="pwMatch" class="text-danger"></small>
                            </div>
                            <div class="form-group">
                                <label for="nickname">ニックネーム</label>
                                <input type="text" class="form-control" id="nickname" name="nickname" oninput="checkNickname()" maxlength="16">
                                <small id="nicknameMsg" class="text-danger"></small>
                            </div>
                            <div class="form-group">
							    <label for="email">メールアドレス</label>
							    <div class="d-flex">
							        <input type="text" class="form-control" id="email" name="email" maxlength="50" style="flex: 1;">
							        <button type="button" class="btn btn-secondary" id="verifyEmailButton" style="margin-left: 10px;">認証する</button>
							    </div>
							</div>
							
							<div id="emailVerificationSection" class="form-group" style="display: none;">
							    <label for="verificationCode">認証番号</label>
							    <div class="d-flex">
							        <input type="password" class="form-control" id="verificationCode" style="width: 150px">
							        <button type="button" class="btn btn-primary" id="checkVerificationCode" style="margin-left: 10px;">確認</button>
							    </div>
							</div>
                            <div class="form-group">
                                <label>生年月日</label>
                                <div class="d-flex">
                                    <input type="number" class="form-control input-spacing" id="dobYear" name="year" placeholder="YYYY" min="1900" max="2024">
                                    <input type="number" class="form-control input-spacing" id="dobMonth" name="month" placeholder="MM" min="1" max="12">
                                    <input type="number" class="form-control" id="dobDay" placeholder="DD" name="day" min="1" max="31">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>性別</label><br>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" id="male" value="M">
                                    <label class="form-check-label" for="male">男性</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" id="female" value="F">
                                    <label class="form-check-label" for="female">女性</label>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary btn-custom badge-pill">登録する</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
    

    <%@ include file="/all_component/footer.jsp"%>
<script>
    let emailVerification = false;
    document.getElementById('password').oninput = function() {
        validatePass();
    };
    
    document.getElementById('passwordC').onblur = function() {
    	passwordCheck();
    };

    function validateForm(event) {
        let id = document.getElementById('id');
        let password = document.getElementById('password');
        let passwordConfirm = document.getElementById('passwordC');
        let nickname = document.getElementById('nickname');
        let email = document.getElementById('email');
        let dobYear = document.getElementById('dobYear');
        let dobMonth = document.getElementById('dobMonth');
        let dobDay = document.getElementById('dobDay');
        let gender = document.querySelector('input[name="gender"]:checked');

        if (!id.value || !password.value || !passwordConfirm.value || !nickname.value || !email.value || !dobYear.value || !dobMonth.value || !dobDay.value || !gender) {
            alert("すべてのフィールドを入力してください。");
            if (!id.value) id.focus();
            else if (!password.value) password.focus();
            else if (!passwordConfirm.value) passwordConfirm.focus();
            else if (!nickname.value) nickname.focus();
            else if (!email.value) email.focus();
            else if (!dobYear.value) dobYear.focus();
            else if (!dobMonth.value) dobMonth.focus();
            else if (!dobDay.value) dobDay.focus();
            else if (!gender);
            
            return false;
        }

        if (password.value !== passwordConfirm.value) {
            alert("パスワードが一致しません。");
            passwordConfirm.focus();
            return false;
        }

        let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(email.value)) {
            alert("有効なメールアドレスを入力してください。");
            email.focus();
            return false;
        }

        let year = parseInt(dobYear.value);
        let month = parseInt(dobMonth.value);
        let day = parseInt(dobDay.value);

        if (year < 1900 || year > 2024 || month < 1 || month > 12 || day < 1 || day > 31) {
            alert("正しい生年月日を入力してください。");
            dobDay.focus();
            return false;
        }

        let daysInMonth = [31, (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        if (day > daysInMonth[month - 1]) {
            alert("その月にはその日が存在しません。");
            dobDay.focus();
            return false;
        }
		if(emailVerification === false) {
			alert("メールの認証をしてくだたい。");
			return false;
		}
		console.log("됐다");
        return true;
    }
    
    function passwordCheck() {
    	let password1 = document.getElementById('password').value;
    	let password2 = document.getElementById('passwordC').value;
    	let pwMatch = document.getElementById('pwMatch');
    	
    	if(password1 != password2) {
    		pwMatch.textContent = "パスワードが不一致です。";
    		return false;
    	} else {
    		pwMatch.textContent = "";
    	}
    	return true;
    }

    function validatePass() {
        let passwordV = document.getElementById('password').value;
        let pwCheck = document.getElementById('pwCheck');
        let pwFeedback = document.getElementById('pwFeedback');

        if (passwordV.length < 8) {
            pwFeedback.textContent = "パスワードは8文字以上20文字以下の英字と数字を含めてください。";
        }

        if (passwordV.length >= 8 && passwordV.length <= 20) {
            pwFeedback.textContent = "";
        }
        let regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*?&]*$/;

        if (passwordV.length >= 8 && !regex.test(passwordV)) {
        	pwCheck.innerHTML = "パスワードは英字と数字を必ず含めてください。<br>使用できる特別な文字は @, $, !, %, *, ?, & です。";
            return false;
        }

        if (passwordV.length > 20) {
            pwCheck.textContent = "パスワードは8文字以上20文字以下の長さでください！";
            return false;
        }

        pwCheck.textContent = "";
        return true;
    }

    function checkId() {
        let id = $('#id').val();
        let feedback = $('#idFeedback');
        feedback.text('');

        // 특수문자 및 공백 체크
        let invalidPattern = /[!@#$%^&*(),.?":{}|<> ]/;

        if (invalidPattern.test(id)) {
            feedback.text("使用できないIDです");
            return;
        }

        if (id.length < 4 || id.length > 16) {
            feedback.text("4~16文字のIDを使用してください");
            return;
        }

        $.ajax({
            url: '/checkDuplicate',
            type: 'POST',
            data: { type: 'id', value: id },
            success: function(data) {
                if (data == '使用できないIDです') {
                    feedback.text(data);
                } else {
                    feedback.text('');
                }
            },
            error: function() {
                feedback.text("エラーが発生しました");
            }
        });
    }

    function checkNickname() {
        let nickname = $('#nickname').val();
        let feedback = $('#nicknameMsg');
        feedback.text('');

        let invalidPattern = /[!@#$%^&*(),.?":{}|<> ]/;

        if (invalidPattern.test(nickname)) {
            feedback.text("使用できないニックネームです");
            return;
        }

        if (nickname.length > 16) {
            feedback.text("ニックネームは16文字以内で使用してください");
            return;
        }

        $.ajax({
            url: '/checkDuplicate',
            type: 'POST',
            data: { type: 'nickname', value: nickname },
            success: function(data) {
                if (data == '使用できないニックネームです') {
                    feedback.text(data);
                } else {
                    feedback.text('');
                }
            },
            error: function() {
                feedback.text("エラーが発生しました。");
            }
        });
    }
    
    document.getElementById('verifyEmailButton').addEventListener('click', function () {
        let emaill = document.getElementById('email').value;

        if (emaill === '') {
            alert('メールアドレスを入力してください。');
            return;
        }

        $.ajax({
            url: '/emailsend',
            type: 'POST',
            data: { email: emaill },
            success: function (response) {
                document.getElementById('emailVerificationSection').style.display = 'block';
                sessionStorage.setItem('authCode', response.result);
                document.getElementById('email').setAttribute('readonly', 'true');
                alert('認証番号が送信されました。');
            },
            error: function (xhr) {
            	if(xhr.status === 400) {
            		alert('有効なメールアドレスを入力してくだたい。');
            	} else {
            		alert('認証番号の送信に失敗しました。');
            	}
            }
        });
    });

    document.getElementById('checkVerificationCode').addEventListener('click', function () {
        let verificationCode = document.getElementById('verificationCode').value;
        let savedAuthCode = sessionStorage.getItem('authCode');

        if (verificationCode === '') {
            alert('認証番号を入力してください。');
            return;
        }

        if (verificationCode === savedAuthCode) {
        	document.getElementById('emailVerificationSection').style.display = 'none';
            alert('確認されました。');
            emailVerification = true;
            
        } else {
            alert('認証番号が一致しません。');
        }
    });
</script>
</body>
</html>
                
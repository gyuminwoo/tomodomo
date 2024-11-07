<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>共々</title>
    <%@ include file="/all_component/allcss.jsp"%>
</head>
<body class="d-flex flex-column min-vh-100">

    <%@ include file="/all_component/header.jsp"%>
    
    <section class="container-fluid div-color flex-fill">
        <div class="row">
            <div class="col-md-4 offset-md-4" style="margin-top: 110px;">
                <div class="card mt-5 mb-4" style="margin: auto; max-width: 400px;">
                    <div class="card-header text-center">
                        <i class="fa fa-envelope" aria-hidden="true"></i>
                        <h4>ID・パスワードを探す</h4>
                    </div>
                    <div class="card-body">
                        <div class="form-group">
                            <label for="email">登録したメールアドレス</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="メールアドレスを入力">
                        </div>
                        <div id="emailVerificationSection" class="form-group" style="display: none;">
                            <label for="verificationCode">認証番号</label>
                            <input type="password" class="form-control" id="verificationCode" placeholder="認証番号を入力">
                            <button type="button" class="btn btn-secondary" id="checkVerificationCode" style="margin-top: 10px;">確認</button>
                        </div>
                        <button type="button" class="btn btn-primary btn-custom badge-pill" id="verifyEmailButton">認証メール送信</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
    <%@ include file="/all_component/footer.jsp"%>
    
    <script>
    	let email;
        let emailVerification = false;

        document.getElementById('verifyEmailButton').addEventListener('click', function () {
            email = document.getElementById('email').value;
			
            console.log(email);
            
            if (email === '') {
                alert('メールアドレスを入力してください。');
                return;
            }
			
            $.ajax({
                url: '/emailsend',
                type: 'POST',
                data: { email: email },
                success: function (response) {
                    document.getElementById('emailVerificationSection').style.display = 'block';
                    sessionStorage.setItem('authCode', response.result);
                    document.getElementById('email').setAttribute('readonly', 'true');
                    alert('認証番号が送信されました。');
                },
                error: function (xhr) {
                    if (xhr.status === 400) {
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
            	$.ajax({
            		url: '/userfndinfo',
            		type: 'POST',
            		data: {email: email},
            		success: function() {
            			window.location.href = '/userinfo';
            		}
            	});
            } else {
                alert('認証番号が一致しません。');
            }
        });
    </script>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인</title>
	<link href="/assets/css/loginStyle.css" rel="stylesheet"/>
</head>
<body>
	<div class="all">
		<form action="" method="post">
			<input type="text" id="ID" placeholder="아이디" />
			<div class="text2">
				<input type="password" id="password" placeholder="비밀번호" />
			</div>
			<div class="button">
				<button type='submit'>로 그 인</button>
			</div>
		</form>
		<div class="link">
			<a href="/register">회원가입</a>
		</div>
	</div>
</body>
</html>
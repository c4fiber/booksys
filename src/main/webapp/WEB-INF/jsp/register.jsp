<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<form action="/register.do" method="post">
		<input id="id" placeholder="아이디" type="text"></input>
		<input id="name" placeholder="이름" type="text"></input>
		<input id="phoneNumber" placeholder="전화번호" type="text"></input>
		<input id="password" placeholder="비밀번호" type="password"></input>
		<button type='submit'>확인</button>
	</form>
</body>
</html>
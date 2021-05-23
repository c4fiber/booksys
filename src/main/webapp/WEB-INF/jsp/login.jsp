<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="/css/NewFile.css"
	rel="stylesheet" type="text/css" />
<meta charset="utf-8">
<title>로그인</title>
</head>
<body>
	<form action="/login.do" method="post">
		<input name="id" placeholder="아이디" type="text"></input>
		<input name="password" placeholder="비밀번호" type="password"></input>
		<button type='submit'>로그인</button>
	</form>
	
	<br>
	<form action="./register" method="post">
		<button type='submit'>회원가입</button>
	</form>
</body>
</html>





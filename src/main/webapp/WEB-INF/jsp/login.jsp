<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link href="/assets/css/loginStyle.css" rel="stylesheet" />
</head>
<body>
	<c:if test="${alert eq 'yes'}">
		<script language="javascript">alert("login FAIL!!")</script>
	</c:if>

	<div class="all">
		<form action="/login.do" method="post">
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

		<div class="link">
			<a href="/">홈으로</a>
		</div>
	</div>
</body>
</html>
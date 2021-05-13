<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/css/NewFile.css"
	rel="stylesheet" type="text/css" />
<meta charset="EUC-KR">
<title>로그인</title>
</head>
<body>
	<form action="" method="post">
		<input placeholder="아이디" type="text"></input> <input
			placeholder="비밀번호" type="password"></input>
		<button type='submit'>로그인</button>
	</form>
	<form action="./회원가입.jsp" method="post">
		<button type='submit'>회원가입</button>
	</form>
</body>
</html>





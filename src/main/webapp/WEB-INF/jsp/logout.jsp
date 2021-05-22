<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<link href="/css/NewFile.css"
	rel="stylesheet" type="text/css" />
<meta charset="EUC-KR">
<title>로그아웃</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/logout" method="post">
    <input type="submit" value="Logout" />
</form>
</body>
</html>





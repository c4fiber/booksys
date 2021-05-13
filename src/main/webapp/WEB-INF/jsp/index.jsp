<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Jsp page sample</title>
</head>
<body>
 
  <%-- <%= request.getAttribute("id") --%>
 
<h2> 반갑습니다! <c:out value="${name}" default="anonymous"/> 님</h2>
<c:out value="${session.}"
<br><br>

<a href="/timeTable">GO TO TIMETABLE</a>
<br><br>

<a href="login">GO TO LOGIN</a>
<br><br>

<a href="register">GO TO REGISTER</a>
 
 
 
</body>
</html>


<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<table border="20">
		<tr>
			<td>oid</td>
			<td>number</td>
			<td>places</td>
		</tr>
		<c:forEach var="result" items="${results}" varStatus="Status">
			<tr>
				<td>${result.oid}</td>
				<td>${result.number}</td>
				<td>${result.places}</td>
			</tr>
		</c:forEach>
	</table>
	
	
	<h2>${result}</h2>

</body>
</html>
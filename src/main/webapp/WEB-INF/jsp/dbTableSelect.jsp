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
	<h3>Table 상태</h3>
	<table border="3">
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
	<br>
	<br>
	<h3>예약 상태</h3>
		<table border="3">
		<tr>
			<td>covers</td>
			<td>date</td>
			<td>time</td>
			<td>table_id</td>
			<td>customer_id</td>
			<td>arrivalTime</td>
		</tr>
		<c:forEach var="r" items="${reservations}" varStatus="Status">
			<tr>
				<td>${r.covers}</td>
				<td>${r.date}</td>
				<td>${r.time}</td>
				<td>${r.table_id}</td>
				<td>${r.customer_id}</td>
				<td>${r.arrivalTime}</td>
			</tr>
		</c:forEach>
	</table>
	

</body>
</html>
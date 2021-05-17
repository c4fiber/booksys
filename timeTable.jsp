<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Time Table</title>
</head>
<body>
	<%
		for (int i = 1; i <= 10; i++) {
	%>
	<div style="width: 90px; float: left;">
		<%=i%>번 테이블
	</div>
	<%
		for (float j = 1; j <= 12; j++) {
	%>
	<form name="myform" action="./예약.jsp">
		<input type="hidden" name="tableNumber" value="<%=i%>" /> <input
			type="hidden" name="time" value="<%=17.5 + j / 2%>" />
		<button type="submit" style="width: 75px; float: left;"><%=(int) (17.5 + j / 2)%>:<%=(int) ((j - 1) % 2) * 3%>0
			~
			<%=(int) (18 + j / 2)%>:<%=(int) (j % 2) * 3%>0
		</button>
	</form>
	<%
		}
	%>
	<br>
	<br>
	<br>
	<%
		}
	%>
</body>
</html>
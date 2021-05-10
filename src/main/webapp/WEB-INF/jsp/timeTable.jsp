<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
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
	<form action="./checker.jsp" method="post"
		style="width: 95px; float: left;">
		<input type="hidden" name="tableNumber" value="<%=i%>" /> <input
			type="hidden" name="time" value="<%=17.5 + j / 2%>" /> <input
			type="submit" name="button"
			value="<%=(int) (17.5 + j / 2)%>:<%=(int) ((j - 1) % 2) * 3%>0 ~ <%=(int) (18 + j / 2)%>:<%=(int) (j % 2) * 3%>0" />
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
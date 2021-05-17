<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약확인</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
		String tableNumber = request.getParameter("tableNumber");
		String time = request.getParameter("time");
		float t = Float.parseFloat(time);
	%>
	<div style="width: 100px; margin: auto;">
		<%=tableNumber%>번 테이블
	</div>
	<div style="width: 120px; margin: auto;">
		예약시간
		<%=(int) (t)%>:<%=(int) (t % 2) * 3%>0
	</div>
	<br>
	<br>
	<form action="./checker.jsp">
		<input type="hidden" name="tableNumber" value="<%=tableNumber%>" /> <input
			type="hidden" name="time" value="<%=time%>" />
		<div style="width: 200px; margin: auto;">
			인원 수 : <input type="text" name="covers" style="width: 100px;" />
		</div>
		<br> <br> <br>
		<div style="width: 50px; margin: auto;">
			<button type="submit">확인</button>
		</div>
	</form>
</body>
</html>
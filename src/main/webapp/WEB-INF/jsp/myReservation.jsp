
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<script>
var state = null;
var title = '나의 예약';
var url = '/myReservation';
history.pushState(state, title, url);
</script>
<meta charset="UTF-8">
<title>나의 예약</title>

</head>
<body>
	<%
	 String Message =  (String)request.getAttribute("Message");
     out.print(Message);

	%>
	<form action="/" method="post">
		<input type="submit" value="확인" />
	</form>
</body>
</html>
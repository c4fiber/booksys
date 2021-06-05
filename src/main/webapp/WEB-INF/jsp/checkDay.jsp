<%@page
	import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.*"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>reservation</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/timeTableStyle.css" />
<noscript>
	<link rel="stylesheet" href="assets/css/noscript.css" />
</noscript>

<script>
	var state = null;
	var title = '나의 예약';
	var url = '/checkDay';
	history.pushState(state, title, url);
</script>
<meta charset="UTF-8">
<title>나의 예약</title>
<style>
.centered {
	display: table;
	margin-left: auto;
	margin-right: auto;
}
</style>
</head>
<body class="is-preload">
	<!-- Wrapper -->

	<div id="wrapper">
		<!-- Main -->
		<div id="main">
			<div class="item">
				<%
				ArrayList<String> temp = (ArrayList<String>) request.getAttribute("Message");

				if (temp == null) {
					out.print("충돌되는 예약이 없습니다!");
				} else {
					for (String st : temp) {
						out.print(st + "<br>");
					}
				}
				%>
				<form action="/" method="post">
					<input type="submit" value="확인" />
				</form>

			</div>
		</div>
	</div>


	</div>
</body>
<body>

</body>
</html>





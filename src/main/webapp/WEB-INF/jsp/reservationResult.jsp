<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="assets/css/timeTableStyle.css" />
<noscript>
	<link rel="stylesheet" href="assets/css/noscript.css" />
</noscript>
</head>
<body class="is-preload">
	<!-- Wrapper -->
	<div id="wrapper">
		<!-- Header -->
		<header id="header">
			<h1>Restaurant</h1>
			<p>예약하신 내용을 확인해주세요.</p>
		</header>
		<!-- Main -->
		<div id="main">
			<%
			String Message = (String) request.getAttribute("Message");
			out.print(Message);
			%>
			<form action="/" method="post">
				<input type="submit" value="확인" />
			</form>
		</div>
	</div>
</body>
</html>
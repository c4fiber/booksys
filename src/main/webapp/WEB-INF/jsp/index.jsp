<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>


<html>
	<head>
		<title>restaurant page</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="assets/css/main.css" />
	</head>
	<body class="landing">
		<% 
		String log = "/login";
		String user_id = (String)session.getAttribute("id");
		String buttonLog = "로그인";
		if(user_id==null)
		{
		
		}
		else{
			log = "/logout";
			buttonLog = "로그아웃";
			System.out.println((String)session.getAttribute("id"));
		}%>

		<!-- Header -->
			<header id="header" class="alt">
				<nav id="nav">
					<ul>
						<li><a href="checkdate">홈</a></li>
						<li><a href="#">후기 확인</a></li>
					</ul>
				</nav>
			</header>
		<!-- Banner -->
			<section id="banner">
				<h2>restaurant</h2>
				<p>레스토랑 페이지 입니다.</p>
				<ul class="actions">
					<li><a href=<%=log %> class="button special big"><%=buttonLog %></a></li>
					<li><a href="register" class="button special big">회원가입</a></li>
				</ul>
			</section>
	</body>
</html>
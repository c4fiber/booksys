<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link href="/assets/css/loginStyle.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function onlyNumber(){

    if((event.keyCode<48)||(event.keyCode>57))

       event.returnValue=false;

}
</script>
</head>
<body>
	<div class="all">
		<form action="/register.do" method="post">
			<input type="text" placeholder="아이디" name="id"/>
			<div class="text2">
				<input type="text" placeholder="이름" name="name"/>
			</div>
			<div class="text2">
				<input type="text" maxlength="11" placeholder="전화번호" name="phoneNumber" onkeypress="onlyNumber()"/>
			</div>
			<div class="text2">
				<input type="password" placeholder="비밀번호" name="password"/>
			</div>
			<div class="button">
				<button type='submit'>회원 가입</button>
			</div>
		</form>
		<div class="link">
			<a href="/">홈으로</a>
		</div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>reservation</title>

<!-- jQuery UI CSS파일  -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css"
	type="text/css" />
<!-- jQuery 기본 js파일 -->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<!-- jQuery UI 라이브러리 js파일 -->
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>


<!-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" type="text/css" />  
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script> -->

</head>
<body>
	<h1>예약하실 날짜를 선택해주세요.</h1>
	<form action="timeTable" method="post">
		<p>
			선택일 : <input type="text" id="date">
		</p>
		날짜:
		<p id="total"></p>
		요일:
		<p id="mydate"></p>

		<div class="button">
			<button type='submit'>시간 선택 하기</button>
		</div>
		
	</form>

	<script type="text/javascript">
		$(function() {
			$("#date")
					.datepicker(
							{
								dateFormat : "yy-mm-dd",
								dayNamesMin : [ "일", "월", "화", "수", "목", "금",
										"토" ],
								monthNames : [ "1월", "2월", "3월", "4월", "5월",
										"6월", "7월", "8월", "9월", "10월", "11월",
										"12월" ],
								minDate : 0,
								maxDate : 14,
								onSelect : function(d) {
									var total = d;
									$("#total").text(total);

									//요일 가져오기
									//데이터를 먼저 가져오고 (숫자로 넘어옴)
									var date = new Date($("#date").datepicker({
										dateFormat : "yy-mm-dd"
									}).val());

									week = new Array("일", "월", "화", "수", "목",
											"금", "토");
									$("#mydate").text(week[date.getDay()]);

									test();
									<%! Boolean check = false; %>
									function test() {
										if (!confirm(d + " 희망하시는 날짜가 맞나요?")) {
											alert("날짜를 다시 정해주세요.");
										} else {
											alert("날짜가 선택되었습니다.");
											<% check = true; %>
										}
									}
								}
							});
		});
	</script>
</body>
</html>

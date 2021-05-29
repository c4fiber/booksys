<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<<meta charset="UTF-8">
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
	<!--Time table로 접속을 할 경우 check.do로 request를 보내서 예약이 가능한지 불가능한지에 따른 model을 가져오게 됩니다.-->
	<form action="check.do" method="post" name="run">
		date <input type="text" name="date"/>
		<input type="submit" value="submit"/>
	</form>
	<%
		//테이블의 개수로 5를 변수로 만들면 테이블이 증가되어도 괜찮을 것 같습니다. 단 변수2개를 post할때 더 넘겨줘야 할 것 같습니다.
		for (int i = 1; i <= 5; i++) {
	%>
	<div>
		<p style="text-align:left;"><%=i%>번 테이블</p>
		<!-- true면 중복된 예약이 있다는뜻 기존 타임테이블은 OrigintimeTable로 보존했습니다.(혹시몰라서) -->
		<%
		//6은 시간대 입니다. check.do의 메소드와 동일하게 시간대도 변수로 만들어서 사용하면 가능할 것 같습니다.
		for (float j = 0; j <= 6; j=j+2) {
			//116 아래에 attribute를 가져오는 키는 116이면 (1번테이블)16시를 의미합니다. 다른시간은 필요없으므로
			String temp = (String)request.getAttribute((int)((i*100)+16+j)+"");
			if(temp==null)
			{
				//그냥 timeTable로 접속할경우 나타나는 화면입니다.
				out.println("날짜를 선택해주시면 나타납니다");
				break;
			}
			else if(temp.equals("true"))
			{
				//날짜를 입력받아서 submit을 했을경우 예약이 가능하면 흰색으로 나타납니다.
				out.print("<button style=\"background-color:white; type=\"submit\" style=\"width: 75px; float: left;\">");
				out.print((int)(16+j)+":00:00"+ "\n~"+ (int)(18+j)+":00:00");
				out.print("</button>");
				
				
			}
			else if(temp.equals("false"))
			{
				//불가능하면 빨간색을 가져옵니다.
				out.print("<button style=\"background-color:red; type=\"submit\" style=\"width: 75px; float: left;\">");
				out.print((int)(16+j)+":00:00"+ "\n~"+ (int)(18+j)+":00:00");
				out.print("</button>");
				
			}
		
		%>
	
		
		
	<%
		}
	%>
	</div>

	<br>
	<br>
	<br>
	<%
		}
	%>
		<div>

	<script src ="http://code.jquery.com/jquery-latest.min.js"></script>
	<form action="" method="POST">
		<div class='addInput'>
		</div>
		<button type='button' class='btnAdd'>예약추가</button>
		<input type="submit">
	</form>
	<script type="text/javascript">
	$(document).ready(function(){
		$('.btnAdd').click (function(){
			$('.addInput').append(
					'<input tye="type" name="test" value="">\<button type="button" class="btnRemove">예약버튼삭제하기</button><br>');
		$('.btnRemove').on('click',function(){
			$(this).prev().remove();
			$(this).next().remove();
			$(this).remove();
			
		});		
		
		
		});
	});
	</script>
		</div>
	
	
	<div>
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
	</div>
</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.*" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>reservation</title>
  
<script>
var state = null;
var title = 'reservation';
var url = '/timeTable';
history.pushState(state, title, url);
</script>

<% 
	int numOfTables = ((Integer)request.getAttribute("numOfTables")).intValue();
	int startTime = ((Integer)request.getAttribute("startTime")).intValue();
	int endTime = ((Integer)request.getAttribute("endTime")).intValue();
%>
</head>
<body>
	<!--Time table로 접속을 할 경우 check.do로 request를 보내서 예약이 가능한지 불가능한지에 따른 model을 가져오게 됩니다.-->
	<form action="check.do" method="post" name="run">		
		예약확인
    <%
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateStr = dtf.format(LocalDateTime.now());
		Date date = Date.valueOf(dateStr); 
		out.print("<input type=\"date\" name=\"date\" value='"+date+"'/>");
    %> 
		
		<input type="submit" value="검색" />
	</form>
	
	<%
		if(request.getAttribute("superDate")!=null&&request.getAttribute("id")!=null)
		{
			out.print(request.getAttribute("id")+"님! 현재 테이블 표는"+request.getAttribute("superDate")+"일 기준 입니다.");
		}
		//테이블의 개수로 5를 변수로 만들면 테이블이 증가되어도 괜찮을 것 같습니다. 단 변수2개를 post할때 더 넘겨줘야 할 것 같습니다.
		for (int i = 1; i <= numOfTables; i++) {
	%>
	<div>
		<p style="text-align:left;"><%=i%>번 테이블</p>
		<!-- true면 중복된 예약이 있다는뜻 기존 타임테이블은 OrigintimeTable로 보존했습니다.(혹시몰라서) -->
		<%

		//6은 시간대 입니다. check.do의 메소드와 동일하게 시간대도 변수로 만들어서 사용하면 가능할 것 같습니다.
		for (int j = startTime; j < endTime; j=j+2) {
			//116 아래에 attribute를 가져오는 키는 116이면 (1번테이블)16시를 의미합니다. 다른시간은 필요없으므로
			String temp = (String)request.getAttribute((i * 100 + j) + "");
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
				out.print((int)(j)+":00:00"+ "\n~"+ (int)(j+2)+":00:00");
				out.print("</button>");
				
				
			}
			else
			{
				//불가능하면 빨간색을 가져옵니다.
				out.print("<button style=\"background-color:red; type=\"submit\" style=\"width: 75px; float: left;\">");
				out.print((int)(j)+":00:00"+ "\n~"+ (int)(j+2)+":00:00");
				out.print("</button>");
				
			}
		}
		%>
	<br>
	<br>
	</div>
	<%
	}
	%>
		
	<div>
	<script src ="http://code.jquery.com/jquery-latest.min.js"></script>
	<form action="reservation.do" method="POST">
		<div class='temp'>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;예약일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				예약시간&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				테이블명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				인원
				
	 			<br>
		</div>
		<div class='addInput'>
		</div>
		<button type='button' class='btnAdd'>예약추가</button>
		<input type="hidden" name="id" value=<%=(String)request.getAttribute("id") %>>
		<input id='submitReservation' value="등록" type="submit" disabled="disabled"/>
	</form>
    
	<script type="text/javascript">
	var line =0;
	$(document).ready(function(){
		$('.btnAdd').click (function(){
			$('.addInput').append(
			'<input type="date" name="date" value=""><select name="time"><option value=""></option><option value="16:00:00">16:00:00</option><option value="18:00:00">18:00:00</option><option value="20:00:00">20:00:00</option><option value="22:00:00">22:00:00</option></select><input type="number" name="table_id" value="" min="0"><input type="number" name="covers" value=""min="0">\<button id ="deleteButton" type="button" class="btnRemove">예약삭제</button><br>'
      );
      
      $(this).remove();
			if( $("button[id=deleteButton]:button").length == 0)
			{
				$("input[id=submitReservation]:submit").attr("disabled",false); //보이기
			}

		$('.btnRemove').on('click',function(){
			$(this).prev().remove();
			$(this).prev().remove();
			$(this).prev().remove();
			$(this).prev().remove();
			$(this).prev().remove();
			$(this).next().remove();
		});		
		
		
		});
	});
	</script>	
	</div>
	
</body>
</html>


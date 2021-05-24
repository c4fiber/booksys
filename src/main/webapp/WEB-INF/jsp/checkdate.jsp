<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>reservation</title>

<!-- jQuery UI CSS����  -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css"
	type="text/css" />
<!-- jQuery �⺻ js���� -->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<!-- jQuery UI ���̺귯�� js���� -->
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>


<!-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" type="text/css" />  
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script> -->

</head>
<body>
	<h1>�����Ͻ� ��¥�� �������ּ���.</h1>
	<form action="timeTable" method="post">
		<p>
			������ : <input type="text" id="date">
		</p>
		��¥:
		<p id="total"></p>
		����:
		<p id="mydate"></p>

		<div class="button">
			<button type='submit'>�ð� ���� �ϱ�</button>
		</div>
		
	</form>

	<script type="text/javascript">
		$(function() {
			$("#date")
					.datepicker(
							{
								dateFormat : "yy-mm-dd",
								dayNamesMin : [ "��", "��", "ȭ", "��", "��", "��",
										"��" ],
								monthNames : [ "1��", "2��", "3��", "4��", "5��",
										"6��", "7��", "8��", "9��", "10��", "11��",
										"12��" ],
								minDate : 0,
								maxDate : 14,
								onSelect : function(d) {
									var total = d;
									$("#total").text(total);

									//���� ��������
									//�����͸� ���� �������� (���ڷ� �Ѿ��)
									var date = new Date($("#date").datepicker({
										dateFormat : "yy-mm-dd"
									}).val());

									week = new Array("��", "��", "ȭ", "��", "��",
											"��", "��");
									$("#mydate").text(week[date.getDay()]);

									test();
									<%! Boolean check = false; %>
									function test() {
										if (!confirm(d + " ����Ͻô� ��¥�� �³���?")) {
											alert("��¥�� �ٽ� �����ּ���.");
										} else {
											alert("��¥�� ���õǾ����ϴ�.");
											<% check = true; %>
										}
									}
								}
							});
		});
	</script>
</body>
</html>

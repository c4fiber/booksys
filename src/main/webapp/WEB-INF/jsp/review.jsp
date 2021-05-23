<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Review</title>
<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>

<body>
	<div class="container">
		<div class="jumbotron">
			<h2>Restaurant Review</h2>
			<p>후기를 남겨주세요!</p>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>Title</th>
					<th>작성일</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="review_Bean" items="${requestScope.list}">
					<tr class="info">
						<td>${review_Bean.review_num}</td>
						<td>${review_Bean.user_id}</td>
						<td><a data-toggle="modal" data-target="#myModal2"
							onclick="review_read(${review_Bean.review_num})">${review_Bean.review_title}</a></td>
						<td>${review_Bean.review_goods_name}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-4 text-success" style="text-align: right;">
				<button type="button" class="btn btn-success btn-lg"
					data-toggle="modal" data-target="#myModal">후기 작성하기</button>
			</div>
		</div>



		<!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<div class="panel-group">
							<div class="panel panel-success" style="margin-top: 10px;">
								<div class="panel-heading">Review 작성</div>
								<div class="panel-body">
									<%-- form --%>
									<form class="form-horizontal" role="form"
										action="${review_write}" method="post">
										<div class="form-group">
											<label class="control-label col-sm-2">작성자(ID):</label>
											<div class="col-sm-10">
												<input type="text" class="form-control" id="user_id"
													name="user_id" placeholder="ID">
											</div>
										</div>
										<div class="form-group">
											<label class="control-label col-sm-2" for="pwd">제목:</label>
											<div class="col-sm-10">
												<input type="text" class="form-control" id="review_title"
													name="review_title" placeholder="Title">
											</div>
										</div>
										<div class="form-group">
											<label class="control-label col-sm-2" for="pwd">내용:</label>
											<div class="col-sm-10">
												<textarea class="form-control" rows="5"
													placeholder="review_content" name="review_content"
													id="review_content"></textarea>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">
												<button type="submit" class="btn btn-success">작 성</button>
												<button type="reset" class="btn btn-danger">초기화</button>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
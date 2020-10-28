<%@page import="table.BanJi"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<title>批量导入学员</title>
</head>
<body>
	<div class="row">
		<div class="col-md-offset-3 col-md-8">
			<label>模板下载：</label><a href="${pageContext.request.contextPath}/teacher/ImportStudentDownloadServlet">批量导入模板下载</a>
		</div>
	</div>
	<div class="row">
		<div class="col-md-offset-3 col-md-8">
			<form class="form form-horizontal" action="${pageContext.request.contextPath}/teacher/ImportStudentServlet" id="form-article-add" enctype="multipart/form-data" method="post">
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">批量导入：</label>
					<div class="formControls col-xs-8 col-sm-7"> 
					<span class="btn-upload form-group">
							<input type="file"  name="file" class="input-file" id="file">
					</span> 
					</div>
					<div class="col-sm-2">
						<input type="submit" class="btn btn-primary" value="导入数据"/>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="col-md-offset-3 col-md-8">
		<label class="text-danger">${error}</label>
		<label class="text-danger">${msg}</label>
		<c:if test="${not empty errornames}">
			<hr>
			<c:forEach items="${errornames}" var="n">
				${n}<br>
			</c:forEach>
		</c:if>
		</div>
	</div>
	<div class="row">
		<div class="col-md-offset-1 col-md-8">
			<img alt="模板填写说明"   src="${pageContext.request.contextPath}/images/muban.png">
		</div>
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
			<form action="${pageContext.request.contextPath}/teacher/ScoreAddServlet" method="post">
				
				<div class="form-group">
					<label for="sname">请输入学生姓名:</label> <input
						type="text" class="form-control" name="sname" id="sname" value="${st.name}" readonly="readonly" maxlength="20" required="required">
						<input type="hidden" name="sid" id="sid" value="${st.sid }">
				</div>
				<div class="form-group">
					<label for="exampleInputEmail1">请选择课程:</label> <select
						class="form-control" name="cid" id="cid">
							<c:forEach items="${clist }" var="c">
								<option value="${c.id }">${c.cname}</option>
							</c:forEach>
						</select>
				</div>
				
				<div class="form-group">
					<label for="exampleInputFile">分数:</label> <input type="number"
						class="form-control" name="score" required="required" max="100" min="0" value="0"/>
				</div>
				<button type="submit" class="btn btn-danger">确定并提交</button>
			</form>
		</div>
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    
</body>
</html>
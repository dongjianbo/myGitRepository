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
		<div class="col-md-4 col-md-offset-4">
			<form action="${pageContext.request.contextPath}/teacher/AccountCreateServlet" method="post">
				<div class="form-group">
					<label for="exampleInputPassword1">登录名:</label> <input
						type="text" class="form-control" name="uname" maxlength="20" required="required">
				</div>
				<div class="form-group">
					<label for="exampleInputFile">登录密码:</label> <input type="password"
						class="form-control" name="password" value="123456"/>
				</div>
				<div class="form-group">
					<label for="exampleInputFile">真实姓名:</label> <input type="text"
						class="form-control" name="truename"/>
						<input type="hidden" name="role" value="3"/>
				</div>
				<div class="form-group">
					<label for="exampleInputFile">账号角色:</label>
						<select class="form-control" name="role">
							<option  value="1">管理员
							<option  value="3">教员
						</select>
				</div>
				<button type="submit" class="btn btn-danger">确定并提交</button>
			</form>
		</div>
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</body>
</html>
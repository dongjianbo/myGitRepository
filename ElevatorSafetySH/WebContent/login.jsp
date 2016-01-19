<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.servletContext.contextPath}/css/system.css" rel="stylesheet" type="text/css">
<link href="${pageContext.servletContext.contextPath}/css/table.css" rel="stylesheet" type="text/css">
<style type="text/css">
.formDiv{
	width: 260px;
	height: 200px;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-left: -130px;
	margin-top: -100px;
}
</style>
</head>
<body bgcolor="#accccc">

	<div class="formDiv">
		<h1 align="center">河南省电梯安全监督管理信息平台</h1>${massage3 }
		<form action="${pageContext.servletContext.contextPath }/login/check.do"
		method="post">
		登录名：<input type="text" name="loginname" value="${op.loginname }" style="height: 24px;width: 260px;"/>${massage1 }<br> 
		密    码：<input type="password" value="${op.password }" style="height: 24px;width: 260px;"
			name="password" /> ${massage2 }<br> 
			<div style="text-align: right;">
				<input type="submit" value="登       录"/>
			</div>
			
	</form>
	</div>
	
</body>
</html>
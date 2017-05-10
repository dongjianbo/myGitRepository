<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>河南省电梯安全监督管理信息平台</title>
<style type="text/css">
.title1 {
	font-family: "华文行楷";
	font-size: 50px;
	color: #444456;
	margin-top: 100px;
	margin-bottom: 50px;
}

.label1 {
	font-family: "仿宋";
	font-size: 16px;
	font-weight: bold;
	color: #444456;
}
.border1{
	border-left-width: 0px;
	border-right-width: 0px;
	border-top-width: 0px;
	background-color: #efffff;
}
</style>
</head>
<body bgcolor="#efffff">
	<h1 align="center" class="title1">河南省电梯安全监督管理信息平台</h1>
	<table align="center" width="100%">
		<tr>
			<td width="50%" style="text-align: right"><img
				src="${pageContext.servletContext.contextPath}/images/14060338.jpg"
				width="400px" /></td>
			<td style="text-align: left">
				<div style="margin-left: 120px;margin-top: 100px;">
					<form
						action="${pageContext.servletContext.contextPath}/login/check.do"
						method="post">
						<span class="label1">登录帐号：</span> <input type="text"
							class="border1"
							name="loginname" value="${op.loginname }"
							style="height: 24px; width: 160px;" />${massage1 }<br> <br>
						<br>
						<br>
						<br> <span class="label1">登录密码：</span> <input type="password"
							class="border1"
							value="${op.password }" style="height: 24px; width: 160px;"
							name="password" /> ${massage2 }<br>
						<br> <br> <br> <input style="margin-left: 150px;height: 30px;width: 100px;"
								type="submit" class="label1" value="登   录" />

					</form>
				</div>
			</td>
		</tr>
	</table>


</body>
</html>
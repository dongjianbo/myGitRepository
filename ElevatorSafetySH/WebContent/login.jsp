<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="." target="main_name">
<title>洲智电梯安全监督管理信息平台</title>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/bootstrap337/css/bootstrap.min.css">

<!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/bootstrap337/css/bootstrap-theme.min.css">
<script
	src="${pageContext.servletContext.contextPath}/jquery/jquery-1.10.2.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="${pageContext.servletContext.contextPath}/bootstrap337/js/bootstrap.min.js"></script>
<style type="text/css">
/* 登录页样式 */
.login {
	width: 360px;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -220px;
	margin-left: -190px;
	padding: 0 0px 20px;
	border-radius: 3px;
}

.logo {
	width: 100%;
	padding-bottom: 10px;
	background-image:
		url("${pageContext.servletContext.contextPath}/images/logo-bg.png");
	background-repeat: no-repeat;
	background-size: cover;
	overflow: hidden;
}

.logo img {
	display: block;
	width: 80px;
	height: 80px;
	margin: 20px auto 10px;
}

.logo h4 {
	text-align: center;
	color: #fff;
	font-size: 32px;
	font-weight: bold;
	text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
}

.login-content {
	width: 100%;
	background: rgba(255, 255, 255, 0.5);
	box-shadow: inset 0px 2px 2px 2px rgba(255, 255, 255, 0.5), inset 0px
		2px 4px rgba(0, 0, 0, 0.1);
	overflow: hidden;
}

.tabbutton {
	margin-top: 20px;
	line-height: 38px;
}

.tabbutton button {
	width: 50%;
	font-size: 18px;
	color: #666;
	border: none;
	background: none;
	border-bottom: 1px solid rgba(255, 255, 255, 0.5);
}

.tabbutton button:hover, .button-active {
	color: #ff9b00 !important;
	border-bottom-color: #ff8800 !important;
}

#user-form {
	padding: 20px;
	margin: 0;
}

.input-width {
	width: auto !important;
	padding-right: 12px !important;
	margin-right: 5px;
}

.loginfont {
	text-align: center;
	color: orange;
}

#saveData {
	margin-bottom: 10px;
	background: #4bcdff;
	border: none;
	height: 36px;
	padding: 0px;
}

#saveData:hover {
	background: #14beff;
}

body {
	width: 100%;
	height: 100%;
	margin: 0;
	padding: 0;
	background-color: #fff;
	background-image:
		url('${pageContext.servletContext.contextPath}/images/login_bg1.jpg');
	background-repeat: no-repeat;
	background-position: top; background-size : 100% auto;
	overflow: hidden;
	background-size: 100% auto;
}
</style>
</head>

<body>
	<div class="container">
		<div class="login">
			<div class="logo">
				<img src="${pageContext.servletContext.contextPath}/images/logo.png"
					alt="logo">
				<h4>
					洲智电梯安全<br>监督管理信息平台
				</h4>
			</div>
			<div class="login-content">
				<h3 class="loginfont">用户登录</h3>
				<form id="user-form" method="post" novalidate="novalidate" class="bv-form" action="${pageContext.servletContext.contextPath}/login/check.do">
					<button type="submit" class="bv-hidden-submit"
						style="display: none; width: 0px; height: 0px;"></button>
					<div class="form-group has-feedback">
						<div class="input-group">
							<span class="input-group-addon"> <span
								class="glyphicon glyphicon-credit-card"></span>
							</span> <input type="text" class="form-control" placeholder="登录名/手机号"
								autofocus="autofocus" id="userLoginname" name="loginname"
								data-bv-field="userLoginname" value="${op.loginname }"/>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon "> <span
								class="glyphicon glyphicon-lock"></span>
							</span> <input type="password" class="form-control" placeholder="登录密码"
								name="password" value="${op.password }" id="userPassword">
						</div>
					</div>
					<button class="btn btn-lg btn-block btn-info" id="saveDatas">
						登录</button>
					<div style="width: 100%; text-align: center;">
						<span class="label label-warning" id="infotitle">${massage1}${massage2}</span>
					</div>
				</form>
			</div>
		</div>
		<div style="text-align: center;"></div>
	</div>


</body>
</html>
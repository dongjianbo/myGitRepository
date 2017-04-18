<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes"/>
<title>您所乘坐电梯</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link href="${path}/css/table.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${path}/jquery/themes/base/jquery.ui.all.css">
		<script src="${path}/jquery/jquery-1.10.2.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.core.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.widget.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.mouse.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.button.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.draggable.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.position.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.resizable.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.button.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.dialog.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.effect.js"></script>
	<style type="text/css">
		body{
			font-family: "微软雅黑";
		}
	</style>	
</head>

<body>
	<p>电梯名称:&nbsp;&nbsp;<b>${e.desc }</b></p>
	<hr>
	<p>半月维保:&nbsp;&nbsp;${service_15}</p>
	<p>季度维保:&nbsp;&nbsp;${service_90}</p>
	<p>半年维保:&nbsp;&nbsp;${service_180}</p>
	<p>全年维保:&nbsp;&nbsp;${service_360}</p>
	<p>下次年检时间:&nbsp;&nbsp;${next_rounds }</p>
	<p>最后巡视时间:&nbsp;&nbsp;${last_rounds}</p>
	<hr>
	<p>维保单位:&nbsp;&nbsp;${e.service.name }&nbsp;&nbsp;&nbsp;&nbsp;</p>
	<p>使用单位:&nbsp;&nbsp;${e.user.name }&nbsp;&nbsp;&nbsp;&nbsp;</p>
	<hr>
	<div align="center">
		<p style="vertical-align: middle;"><img style="width: 20px;height: 20px;" src="/ElevatorSafetySH/images/CSEI.jpg"/>&nbsp;&nbsp;<a href="http://www.csei.org.cn/">中国特种设备检测研究院</a></p>
		<p>技术提供:&nbsp;&nbsp;河南金云信息技术有限公司 </p>
	  	<p>服务电话:&nbsp;&nbsp;<a href="tel:0395-6169399">0395-6169399</a></p>
	</div>
	
	
	
</body>
</html>
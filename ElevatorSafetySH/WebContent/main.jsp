<%@page import="util.DateUtils"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String basePath=pageContext.getServletContext().getContextPath();
	session.setAttribute("path", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base target="_root">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${path}/jquery/themes/base/jquery.ui.all.css">
<style type="text/css">
#top {
	border-color: #aacccc;
	border-width: 1px;
	border-style: solid;
	width: 100%;
	height: 100%;
	background-color: #ddefff;
	font-family: 微软雅黑;
	font-size: 40px;
	font-style: normal;
	text-align: center;
	vertical-align: middle;
	color: #797999;
}

.ui-widget-header {
	padding: 0.2em 0.5em;
	margin: 0;
}

.ui-widget-content {
	padding: 1em;
	margin-bottom: 1em;
}

p {
	margin: 0;
}

ul {
	margin: 0;
	list-style: none;
}

li {
	line-height: 2em;
}
</style>
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
<script type="text/javascript">
/*
$(function() {

	$( "#main" )
	.addClass( "ui-widget" )
	.find( "h2, h3" )
	.addClass( "ui-widget-header ui-corner-top" )
	.next()
	.addClass( "ui-widget-content ui-corner-bottom" );

});
*/
</script>

</head>
<body>
	<table height="100%" width="100%">
		<tr height="6%">
			<td colspan="2">
				<div id="top">电梯安全管理平台</div>
			</td>
		</tr>
		<tr>
			<td width="10%" valign="top" id="main_td">
				<div id="main" class="div1" style="height: 90%">
					<ul>
						<li><a href="${path}/system/system.jsp">单位信息维护</a></li>
						<li><a href="${path}/system/person.jsp">人员信息维护</a></li>
						<li><a href="#">人员权限维护</a></li>
						<li><a href="#">电梯类型维护</a></li>
					</ul>
					<ul>
						<li><a href="${path}/system/insertElevatorDeclaration.jsp">电梯资料申报</a></li>
						<li><a href="#">电梯注册手续</a></li>
						<li><a href="#">分类汇总</a></li>
						<li><a href="#">电梯详细查询</a></li>
					</ul>
					<ul>
						<li><a href="">分类汇总</a></li>
						<li><a href="${path}/system/elevatorTongji.jsp">任务提醒</a></li>
						<li><a href="#">任务量统计</a></li>
						<li><a href="#">应急任务流程</a></li>
					</ul>
					<ul>
						<li><a href="#">年检提醒</a></li>
						<li><a href="#">巡检情况统计</a></li>
						<li><a href="#">安全任务量统计</a></li>
					</ul>
					<ul>
						<li><a href="#">年检任务提醒</a></li>
						<li><a href="#">检测报告录入</a></li>
					</ul>
				</div>
				<div class="div1" style="height: 9%;margin-top: 1px;">
					<ul style="margin-left: -20px;">
						<li>登录人：${login.name} </li>
						<li><%=DateUtils.now() %></li>
					</ul>
					
					
				</div>
			</td>
			<td><div class="div1" id="right">
					<iframe width="100%" height="100%" frameborder="0" marginheight="0" scrolling="no"
						marginwidth="0" id="_root" name="_root"></iframe>
				</div></td>
		</tr>
	</table>
	<div title="打开或关闭侧边栏" onclick="$('#main_td').toggle();"
		style="width: 20px; height: 20px; display: block; position: absolute; left: 4px; top: 35px; border: 1px solid #aacccc; background-color: #ddefff; text-align: center; cursor: pointer;">
		&lt;&gt;</div>
</body>
</html>
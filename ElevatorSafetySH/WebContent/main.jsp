<%@page import="util.DateUtils"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	height: 51px;
	background-color: #ddefff;
	font-family: 微软雅黑;
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
	margin-left: -30px;
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

$().ready(function(){
	//取login界面 传过来的角色 值 
	var role= '<%=session.getAttribute("role")%>';
	$.getJSON("${path }/role/toUpdate.do?idrole="+role,"rand="+Math.random(),function(d){
		var a=d.menus;
		//alert(a);
		 $("#menu").html("");
		for(var i=0;i<a.length;i++){
			 //var id_system_menu=a[i].id_system_menu;
			 $("#menu").append("<li><a href='../"+a[i].url+"'>"+a[i].name_item+"</a>");
	}
	});
	
});
</script>

</head>
<body>
	<table height="100%" width="100%">
		<tr height="6%">
			<td colspan="2" id="top">
				<ul>
				<li><div style="font-size: 30px;margin-top: 10px;">
				 <c:if test="${operator_type.id_operator_type=='00'||operator_type.id_operator_type=='01'}">
				   河南省电梯安全监督管理中心
				   </c:if>
				   <c:if test="${operator_type.id_operator_type=='10'||operator_type.id_operator_type=='11'}">
				   河南省电梯安全监督维保中心
				   </c:if>
				   <c:if test="${operator_type.id_operator_type=='20'||operator_type.id_operator_type=='21'}">
				   河南省电梯安全监督用户中心
				   </c:if>
				   <c:if test="${operator_type.id_operator_type=='30'||operator_type.id_operator_type=='31'}">
				   河南省电梯安全监督检验检测中心
				   </c:if>
				</div>
				<li><div style="font-size:14px;text-align: right;margin-right: 30px;">
				${login.city.name_city}${login.distict.name_district}${login.subdistict.name_subdistrict}&nbsp;&nbsp;
				${deptName}&nbsp;&nbsp;
				${operator_type.name}
				&nbsp;&nbsp;
				${login.role.name_role }
				</div>
				</ul>
			</td>
		</tr>
		<tr>
			<td width="10%" valign="top" id="main_td">
				<div id="main" class="div1" style="height: 90%">
					<ul id="menu">
						
				

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
		style="width: 20px; height: 20px; display: block; position: absolute; left: 4px; top: 40px; border: 1px solid #aacccc; background-color: #ddefff; text-align: center; cursor: pointer;">
		&lt;&gt;</div>
</body>
</html>

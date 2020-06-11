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
<title>
<c:if test="${operator_type.id_operator_type=='00'||operator_type.id_operator_type=='01'}">
湖北省电梯安全监管中心
</c:if>
<c:if test="${operator_type.id_operator_type=='10'||operator_type.id_operator_type=='11'}">
湖北省电梯安全监督维保中心
</c:if>
<c:if test="${operator_type.id_operator_type=='20'||operator_type.id_operator_type=='21'||operator_type.id_operator_type=='40'}">
湖北省电梯安全监督用户中心
</c:if>
<c:if test="${operator_type.id_operator_type=='30'||operator_type.id_operator_type=='31'}">
湖北省电梯安全监督检验检测中心
</c:if>
<c:if test="${operator_type.id_operator_type=='50'}">
湖北省电梯安全监管房管中心
</c:if>
</title>
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

	$.getJSON("${path }/role/getMenus.do?roleid=${role}","rand="+Math.random(),function(a){
		 $("#menu").html("");
		for(var i=0;i<a.length;i++){

			 $("#menu").append("<li><a href='${path }/"+a[i][1]+"'>"+a[i][0]+"</a>");
		}
		
		
	});
});
function closeme(){ 
	var browserName=navigator.appName; 
	if (browserName=="Netscape") { 
		window.open('','_parent',''); 
		window.close(); 
	} 
	else if (browserName=="Microsoft Internet Explorer") { 
		window.opener = "whocares"; 
		window.close(); 
	} 
} 
</script>

</head>
<body>
	<table height="100%" width="100%">
		<tr height="6%">
			<td colspan="2" id="top">
				<ul>
				<li><div style="font-size: 30px;margin-top: 10px;">
				 <c:if test="${operator_type.id_operator_type=='00'||operator_type.id_operator_type=='01'}">
				   湖北省电梯安全监管中心
			     </c:if>
			     <c:if test="${operator_type.id_operator_type=='10'||operator_type.id_operator_type=='11'}">
				   湖北省电梯安全监督维保中心
				   </c:if>
				   <c:if test="${operator_type.id_operator_type=='20'||operator_type.id_operator_type=='21'||operator_type.id_operator_type=='40'}">
				   湖北省电梯安全监督用户中心
				   </c:if>
				   <c:if test="${operator_type.id_operator_type=='30'||operator_type.id_operator_type=='31'}">
				   湖北省电梯安全监督检验检测中心
				   </c:if>
				   <c:if test="${operator_type.id_operator_type=='50'}">
				   湖北省电梯安全监管房管中心
				   </c:if>
				</div>
				<li><div style="font-size:14px;text-align: left;margin-left: 100px;">
				<div style="width: 40%;display: inline-block;">
<%-- 				${operator_type.name} --%>
				</div>
				<div style="width: 55%;display: inline-block;text-align: right">
<%-- 				${login.city.name_city}${login.distict.name_district}${login.subdistict.name_subdistrict}&nbsp;&nbsp;&nbsp;&nbsp; --%>
				<c:if test="${operator_type.id_operator_type=='00'||operator_type.id_operator_type=='01'}">
				  ${login.city.name_city}</c:if>${deptName}<c:if test="${(operator_type.id_operator_type=='00'||operator_type.id_operator_type=='50')&&login.distict.id_district ne '00'}">${login.distict.name_district}${login.subdistict.name_subdistrict}分局&nbsp;&nbsp;
				  </c:if>
<%-- 				${login.role.name_role } --%>
				</div>
				</div>
				</ul>
			</td>
		</tr>
		<tr>
			<td width="10%" valign="top" id="main_td" height="90%">
				<div id="main" class="div1" style="height: 84%">
					<ul id="menu">
<%-- 						<li><a href='${path}/system/system.jsp'>单位信息维护</a> --%>
<%-- 						<li><a href='${path}/system/person.jsp'>人员信息维护</a> --%>
<%-- 						<li><a href='${path}/role/list.do'>角色定义与配置</a> --%>
<%-- 						<li><a href='${path}/system/insertElevatorDeclaration.jsp'>电梯资料申报</a> --%>
<%-- 						<li><a href='${path}/elevator/list.do'>电梯资料注册</a> --%>
<%-- 						<li><a href='${path}/system/yuanElevatorDeclaration.jsp'>原有电梯资料录入</a> --%>
<%-- 						<li><a href='${path}/elevator/search.do'>技术监督部门统计查询</a> --%>
<%-- 						<li><a href='${path}/service/search.do'>维保单位任务提醒</a> --%>
<%-- 						<li><a href='${path}/service/task.do'>维保人员任务量统计</a> --%>
<%-- 						<li><a href='${path}/user/search.do'>使用单位年检提醒</a> --%>
<!-- 						<li><a href='#'>安全人员任务量统计</a> -->
<%-- 						<li><a href='${path}/test/search.do'>检测单位年检提醒</a> --%>
<!-- 						<li><a href='#'>检测人员任务量统计</a> -->
<%-- 						<li><a href='${path }/system/insertTestReport.jsp'>检测报告录入</a> --%>
<%-- 						<li><a href='${path}/system/operatorZhanghao.jsp'>账号信息设置</a> --%>
								
					</ul>
					
				</div>
				<div class="div1" style="height: 5%;margin-top: -1px;background-color: #ddefff;" onclick="closeme();">
					<input type="button"  style="width:135px;background-color: #ddefff;border: none;cursor: pointer;" value="退出系统"/>
				</div>
				<div class="div1" style="height: 10%;margin-top: -1px;background-color: #ddefff;">
					<ul style="margin-left: -30px;">
						<li>登录人：${login.name} </li>
						<li><%=DateUtils.now() %></li>
					</ul>
				</div>
				
			</td>
			<td height="90%"><div class="div1" id="right" style="height: 100%">
					<iframe width="100%" height="100%" frameborder="0" marginheight="0" scrolling="auto"
						marginwidth="0" id="_root" name="_root" src="
						<c:if test="${operator_type.id_operator_type=='00'||operator_type.id_operator_type=='01'}">
<%-- 				   ${path }/elevator/search.do?id_service=0&id_user=0&id_test=0&key=first&keyType=1 --%>
				    ${path }/system/testBMap.jsp
				   </c:if>
				   <c:if test="${operator_type.id_operator_type=='10'||operator_type.id_operator_type=='11'}">
<%-- 				    ${path }/service/search.do --%>
				    ${path }/system/testBMap.jsp
				   </c:if>
				   <c:if test="${operator_type.id_operator_type=='20'||operator_type.id_operator_type=='21'||operator_type.id_operator_type=='40'}">
				    ${path }/user/search.do?iduser=0
				   </c:if>
				   <c:if test="${operator_type.id_operator_type=='30'||operator_type.id_operator_type=='31'}">
				    ${path }/test/search.do
				   </c:if>
				   <c:if test="${operator_type.id_operator_type=='50'}">
<%-- 				    ${path }/citymanager/search.do?id_service=0&id_user=0&id_test=0&key=first&keyType=1 --%>
				  	 ${path }/system/testBMap.jsp
				   </c:if>
						"></iframe>
				</div></td>
		</tr>
	</table>
	<div title="打开或关闭侧边栏" onclick="$('#main_td').toggle();"
		style="width: 20px; height: 20px; display: block; position: absolute; left: 4px; top: 0px;margin-top:30px; border: 1px solid #aacccc; background-color: #ddefff; text-align: center; cursor: pointer;">
		&lt;&gt;</div>
	
</body>
</html>

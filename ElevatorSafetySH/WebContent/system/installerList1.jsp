<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
	
</head>
<body>
	<form action="${path}/installer/list1.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 单位名称/代码 <input type="text" name="key" size="50"
					value="${param.key}" /> <input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>编号</th>
			<th>安装单位代码</th>
			<th>单位名称</th>
			<th>安装许可证编号</th>
<!-- 			<th>安装许可证名称</th> -->
			<th>单位负责人</th>
			<th>联系电话</th>
			<th>单位地址</th>
			<th>注册区域</th>
		</tr>
		<c:forEach items="${installerList}" var="d">
			<tr>
				<td style="text-align: right">${d.idinstaller}</td>
				<td style="text-align: left">${d.code }</td>
				<td style="text-align: left">${d.name}</td>
				<td style="text-align: left">${d.licence }</td>
<%-- 				<td style="text-align: left">${d.licename }</td> --%>
				<td style="text-align: left">${d.manager }</td>
				<td style="text-align: left">${d.tel }</td>
				<td style="text-align: left">${d.addr }</td>
				<td style="text-align: left">${d.registCity.name_city }</td>
				
			</tr>
		</c:forEach>
		<tr>
			<td colspan="9" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	
</body>
</html>
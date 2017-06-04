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
<c:if test="${requestMapping ne 'elevator'}">
	<form action="${path}/safer/list1.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 姓名/编码 <input type="text" name="key" size="50"
					value="${param.key}" /> <input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
</c:if>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>安全员顺序号</th>
			<th>安全员姓名</th>
			<th>联系方式</th>
			<th>所属用户单位</th>
			<th>从业资格证书编号</th>
			<th>安全人员状态</th>
			<th>上岗卡标识</th>
			
		</tr>
		<c:forEach items="${saferList}" var="d">
			<tr>
				<td>${d.idsafer}</td>
				<td>${d.name }</td>
				<td>${d.tel }</td>
				<td>${d.user.name}</td>
				<td>${d.licencecode}</td>
				<td>${d.status_def.name }</td>
				<td>${d.idMifare}</td>
				
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	
</body>
</html>
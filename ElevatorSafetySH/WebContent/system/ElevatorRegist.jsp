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
	<script src="${path}/jquery/ui/jquery.ui.datepicker.js"></script><!-- 日期控件的js -->
	<script type="text/javascript">
	var dia;
	$().ready(function(){
	});
</script>
</head>
<body>
	<form action="${path}/elevator/list1.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 电梯位置 <input type="text" name="key" size="50"
					value="${param.key}" /> <input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>电梯编号</th>
			<th>电梯型号</th>
			<th>出厂编号</th>
			<th>设计单位</th>
			<th>生产单位</th>
			<th>电梯所在位置</th>
			<th>安装项目负责人</th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${elevListRegist}" var="d">
			<tr>
				<td style="text-align: right">${d.id_elevator}</td>
				<td style="text-align: left">${d.elevatorType.name }</td>
				<td style="text-align: left">${d.code_manufer}</td>
				<td style="text-align: left">${d.designer.name }</td>
				<td style="text-align: left">${d.manufer.name }</td>
				<td style="text-align: left">${d.address }</td>
				<td style="text-align: left">${d.project_duty }</td>
				<td>
				<a href="${path }/system/Elevatorzhuce.jsp?id_elevator=${d.id_elevator}">注册</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="8" style="text-align: left;">${pagination}</td>
		</tr>
	</table>

</body>
</html>
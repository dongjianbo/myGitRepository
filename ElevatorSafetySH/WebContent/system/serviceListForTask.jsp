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
		
	</script>
</head>
<body>

<ul>
	
	<li><h3>${deptName}&nbsp;&nbsp;${typeName }&nbsp;&nbsp;维保记录列表</h3>
	<li>
	<table cellpadding="0" cellspacing="1">
	<tr>
		<th>记录编号</th>
		<th>电梯简称</th>
		<th>操作员1</th>
		<th>操作员2</th>
		<th>操作员3</th>
		<th>维保时间</th>
		<th>记录上传时间</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${list }" var="l">
		<tr>
			<td>${l.maint_id }</td>
			<td>${l.elevator.desc }</td>
			<td>${l.servicer1.name }</td>
			<td>${l.servicer2.name }</td>
			<td>${l.servicer3.name }</td>
			<td>${l.maint_date }</td>
			<td>${l.maint_upload }</td>
			<td><a href="#">查看记录明细</a></td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="8" style="text-align: left;">${pagination}</td>
	</tr>
	</table>
</ul>

</body>
</html>
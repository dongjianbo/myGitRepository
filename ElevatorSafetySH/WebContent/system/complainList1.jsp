<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>repair_query.jsp</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link href="${path}/css/table.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="${path}/jquery/themes/base/jquery.ui.all.css">
<style type="text/css">
a{
	text-decoration: underline;
	
}
span{
	font-size: 12px;
	font-weight: bold;
	padding-left: 50px;
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

</script>
</head>
<body>

<ul>
	<li><table cellpadding="0" cellspacing="1">
		<tr>
			<th>投诉编号</th>
			<th>被投诉对象类型</th>
			<th>被投诉对象</th>
			<th>投诉级别</th>
			<th>投诉来源</th>
			<th>联系方式 </th>
			<th>投诉内容</th>
			<th>处理状态</th>
			<th>处理结果说明</th>
			<th>投诉录入人员姓名</th>
			<th>投诉录入时间 </th>
			<th>处理录入人员姓名</th>
			<th>处理录入时间</th>
			
		</tr>
		<c:forEach items="${list}" var="n">
		<tr>
			<td> ${n.cid}</td>
			<td>${n.complainObject.type_name}</td>
			<td>${n.objectName}</td>
			<td>${n.complainLevel.level_name}</td>
			<td>${n.complianSource.source_name}</td>
			<td>${n.contact}</td>
			<td>${n.content}</td>
			<td>${n.dealStatus.deal_name}</td>
			<td>${n.result}</td>
			<td>${n.input1}</td>
			<td>${n.date1}</td>
			<td>${n.iput2}</td>
			<td>${n.date2}</td>
		</tr>
		</c:forEach>
		<tr>
		  <td colspan="13" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
</ul>



</body>
</html>
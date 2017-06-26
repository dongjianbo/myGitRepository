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
	<script src="${path}/jquery/ui/jquery.ui.dialog.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.effect.js"></script>
<script type="text/javascript">
function showContentDialog(content){
	$("#content_show").val(content);
	$("#contentDialog").dialog("open");
}
</script>
</head>
<body>

<ul>
	<li><h3>使用单位投诉查询列表：</h3>
	<li><table cellpadding="0" cellspacing="1">
		<tr>
			<th>投诉编号</th>
			<th>投诉级别</th>
			<th>投诉类别</th>
			<th>投诉对象</th>
			<th>投诉来源</th>
			<th>投诉内容</th>
			<th>投诉时间 </th>
			<th>联系方式 </th>
			
		</tr>
		<c:forEach items="${list}" var="n">
		<tr>
			<td> ${n.cid}</td>
			<td>${n.complainLevel.level_name}</td>
			<td>${n.complainObject.type_name}</td>
			<td>${n.objectName}</td>
			<td>${n.complianSource.source_name}</td>
			<td>${n.content}</td>
			<td>${n.date1}</td>
			<td>${n.contact}</td>
		</tr>
		</c:forEach>
		<tr>
		  <td colspan="13" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
</ul>


<div id="contentDialog" style="display: none" title="投诉内容">
	<form action="${path }/complain/dealComplain.do" method="post" id="complainForm">
		<ul>
			<li>投诉内容:
			<li><textarea rows="5" cols="30" name="content_show" id="content_show"></textarea><br>
		</ul>
	</form>
</div>
</body>
</html>
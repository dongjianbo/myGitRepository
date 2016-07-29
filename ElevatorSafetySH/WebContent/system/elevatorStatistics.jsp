<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>elevatorTongji.jsp</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link href="${path}/css/table.css" rel="stylesheet" type="text/css">
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
<script type="text/javascript" src="${path }/jquery/jquery-1.9.1.js"></script>

</head>
<body>
<h3>监管部门相关统计数字</h3>
<hr style="margin-top: 10px">
<ul>
	<li><h3>出厂日期超过15年电梯统计：${countFor15Years}</h3>
	<li><h3>电梯按类型统计：</h3>
	<li><table cellpadding="0" cellspacing="1">
	<tr>
		<c:forEach items="${keylist}" var="key">
			<th>${key}</th>
		</c:forEach>
	</tr>
	<tr>
		<c:forEach items="${values }" var="v">
			<td>${v }</td>
		</c:forEach>
	</tr>
	</table>
	<li><h3>单位和人员统计:</h3>
	<li><table cellpadding="0" cellspacing="1">
		<tr>
			<th>使用单位数量</th>
			<th>维保单位数量</th>
			<th>维保人员数量</th>
			<th>安全人员数量</th>
		</tr>
		<tr>
			<td>${userCountForCity}</td>
			<td>${serviceCountForCity}</td>
			<td>${servicerCountForCity}</td>
			<td>${saferCountForCity}</td>
			
		</tr>
		
	</table>
</ul>


</body>
</html>
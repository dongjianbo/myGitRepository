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
</head>
<body>
	<div>
		<input type="button" value="返回上一页" onclick="history.back();" />
	</div>
	<div>
		<c:choose>
			<c:when test="${d eq null}">
				<c:set var="form_action" value="${path }/safer/insert.do"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="form_action" value="${path }/safer/update.do"></c:set>
			</c:otherwise>
		</c:choose>
		<form action="${form_action}" method="post">

			<ul>
				<li>安全员姓名:
				<li><input type="text" name="name" size="50" value="${d.name }" />
				<li>身份证号码:
				<li><input type="text" name="idcard" size="50" value="${d.idcard }" />
				<li>所属用户单位:
				<li><input type="text" name="iduser" size="50"
					value="${d.iduser}" />
				<li>从业资格证书编号:
				<li><input type="text" name="licencecode" size="50"
					value="${d.licencecode }" />
				<li>安全人员状态:
				<li><input type="text" name="status" size="100"
					value="${d.status }" />
				<li>上岗卡标识号:
				<li><input type="text" name="idMifare" size="100"
					value="${d.idMifare }" />
				<li>&nbsp; <c:if test="${d ne null}">
						<input type="hidden" name="idsafer" value="${d.idsafer}" />
					</c:if>
				<li><input type="submit" value="确定" />
			</ul>
		</form>
	</div>

</body>
</html>
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
				<c:set var="form_action" value="${path }/test/insert.do"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="form_action" value="${path }/test/update.do"></c:set>
			</c:otherwise>
		</c:choose>
		<form action="${form_action}" method="post">

			<ul>
				<li>检测单位代码:
				<li><input type="text" name="code" size="50" value="${d.code }" />
				<li>单位名称:
				<li><input type="text" name="name" size="50" value="${d.name }" />
				<li>检测许可证编号:
				<li><input type="text" name="licencecode" size="50"
					value="${d.licencecode }" />
				<li>检测许可证:
				<li><input type="text" name="licename" size="50"
					value="${d.licename }" />
				<li>联系电话:
				<li><input type="text" name="telephone" size="50" value="${d.telephone }" />
				<li>检测单位地址:
				<li><input type="text" name="addr" size="100"
					value="${d.addr }" />
				<li>&nbsp; <c:if test="${d ne null}">
						<input type="hidden" name="idtest" value="${d.idtest }" />
					</c:if>
				<li><input type="submit" value="确定" />
			</ul>
		</form>
	</div>

</body>
</html>
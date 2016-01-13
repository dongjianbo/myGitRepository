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
				<c:set var="form_action" value="${path }/service/insert.do"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="form_action" value="${path }/service/update.do"></c:set>
			</c:otherwise>
		</c:choose>
		<form action="${form_action}" method="post">

			<ul>
				<li>维保单位代码:
				<li><input type="text" name="code" size="50" value="${d.code }" />
				<li>单位名称:
				<li><input type="text" name="name" size="50" value="${d.name }" />
				<li>维保许可证编号:
				<li><input type="text" name="licence" size="50"
					value="${d.licence }" />
				<li>维保许可证:
				<li><input type="text" name="licename" size="50"
					value="${d.licename }" />
				<li>管理负责人:
				<li><input type="text" name="manager" size="50"
					value="${d.manager }" />
				<li>联系电话:
				<li><input type="text" name="tel" size="50" value="${d.tel }" />
				<li>单位地址:
				<li><input type="text" name="addr" size="100"
					value="${d.addr }" />
				<li>&nbsp; <c:if test="${d ne null}">
						<input type="hidden" name="idservice" value="${d.idservice }" />
					</c:if>
				<li><input type="submit" value="确定" />
			</ul>
		</form>
	</div>

</body>
</html>
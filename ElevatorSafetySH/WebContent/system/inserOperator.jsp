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
				<c:set var="form_action" value="${path }/operator/insert.do"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="form_action" value="${path }/operator/update.do"></c:set>
			</c:otherwise>
		</c:choose>
		<form action="${form_action}" method="post">

			<ul>
				<li>权限类别流水号:
				<li><input type="text" name="idprivilege" size="50" value="${d.idprivilege }" />
				<li>操作员姓名:
				<li><input type="text" name="name" size="50" value="${d.name}" />
				<li>身份证号码:
				<li><input type="text" name="idcard" size="50"
					value="${d.idcard}" />
				<li>所属乡镇街道编码:
				<li><input type="text" name="idsubdistrict" size="50"
					value="${d.idsubdistrict }" />
				<li>所属区县编号:
				<li><input type="text" name="iddistrict" size="100"
					value="${d.iddistrict }" />
				<li>所属城市编号:
				<li><input type="text" name="idcity" size="100"
					value="${d.idcity }" />
				<li>&nbsp; <c:if test="${d ne null}">
						<input type="hidden" name="idoperator" value="${d.idoperator}" />
					</c:if>
				<li><input type="submit" value="确定" />
			</ul>
		</form>
	</div>

</body>
</html>
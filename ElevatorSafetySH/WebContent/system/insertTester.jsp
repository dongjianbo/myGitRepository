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
				<c:set var="form_action" value="${path }/tester/insert.do"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="form_action" value="${path }/tester/update.do"></c:set>
			</c:otherwise>
		</c:choose>
		<form action="${form_action}" method="post">

			<ul>
				<li>检验检测人员姓名:
				<li><input type="text" name="name" size="50" value="${d.name }" />
				<li>身份证号码:
				<li><input type="text" name="idcard" size="50" value="${d.idcard }" />
				<li>联系方式:
				<li><input type="text" name="tel" size="50" value="${d.tel }" />
				<li>所属检验检测机构:
				<li><input type="text" name="idtest" size="50"
					value="${d.idtest}" />
				<li>从业资格证书编号:
				<li><input type="text" name="licencecode" size="50"
					value="${d.licencecode }" />
				<li>检验检测人员状态:
				<li><input type="text" name="status" size="100"
					value="${d.status }" />
				<li>上岗卡标识号:
				<li><input type="text" name="idMifare" size="100"
					value="${d.idMifare }" />
				<li>&nbsp; <c:if test="${d ne null}">
						<input type="hidden" name="idtester" value="${d.idtester}" />
					</c:if>
				<li><input type="submit" value="确定" />
			</ul>
		</form>
	</div>

</body>
</html>
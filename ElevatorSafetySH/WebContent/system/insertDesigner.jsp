<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link href="${path}/css/table.css" rel="stylesheet" type="text/css">
<link href="${path}/css/ui-dialog.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${path }/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript">
	function openAddress(o){
		var rv = window.showModalDialog("${path}/system/CitylistList.jsp","请输入地址","dialogWidth=700px;dialogHeight=400px;center=yes;resizable=no");
		o.value=rv;
	}
</script>
</head>
<body>
	<div>
		<input type="button" value="返回上一页" onclick="window.close();" />
	</div>
	<div>
		<c:choose>
			<c:when test="${d eq null}">
				<c:set var="form_action" value="${path }/designer/insert.do"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="form_action" value="${path }/designer/update.do"></c:set>
			</c:otherwise>
		</c:choose>
		<form action="${form_action}" method="post" target="">

			<ul>
				<li>设计单位代码:
				<li><input type="text" name="code" size="50" value="${d.code }" />
				<li>单位名称:
				<li><input type="text" name="name" size="50" value="${d.name }" />
				<li>设计许可证编号:
				<li><input type="text" name="licence" size="50"
					value="${d.licence }" />
				<li>设计许可证:
				<li><input type="text" name="licename" size="50"
					value="${d.licename }" />
				<li>联系电话:
				<li><input type="text" name="tel" size="50" value="${d.tel }" />
				<li>单位地址:
				<li><input type="text" name="addr" size="100"
					value="${d.addr }" onfocus="openAddress(this)" readonly="readonly"/>
				<li>&nbsp; <c:if test="${d ne null}">
						<input type="hidden" name="iddesigner" value="${d.iddesigner }" />
					</c:if>
				<li><input type="submit" value="确定" />
			</ul>
		</form>
	</div>

</body>
</html>
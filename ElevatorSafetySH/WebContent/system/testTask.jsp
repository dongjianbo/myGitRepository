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
<style type="text/css">
a{
	text-decoration: underline;
}
</style>
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
		$().ready(function(){
			$("#start").datepicker({dateFormat:'yy-mm-dd'});
			$("#end").datepicker({dateFormat:'yy-mm-dd'});
		});
	</script>
</head>
<body>

<ul>
	<li><form action="${path}/test/task.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>
				检测人员:
				<select name="idtester">
					<option value="0">-请选择</option>
					<c:forEach items="${testerList }" var="t">
						<option value="${t.idtester}"
							<c:if test="${t.idtester==param.idtester}">
								selected="selected"
							</c:if>
						>${t.name }</option>
					</c:forEach>
				</select>
				从<input readonly="readonly" type="text" id="start" name="start" size="35" value="${start }"/>
				到
				<input readonly="readonly" type="text" id="end" name="end" size="35" value="${end }"/>
				<input type="submit" value="搜索">
				</td>
			</tr>
		</table>
	</form>
	
	<li><h3>${deptName}&nbsp;&nbsp;检测人员完成工作量</h3>
	<li>
	<table cellpadding="0" cellspacing="1">
	<tr>
		<th>巡检次数</th>
		<th>配合维保次数</th>
	</tr>
	<tr>
		<td><a href="${path }/test/listForTask.do?type=0&start=${start }&end=${end}&idtester=${param.idtester}">${countType0 }</a></td>
		<td><a href="${path }/test/listForTask.do?type=-1&start=${start }&end=${end}&idtester=${param.idtester}">${countType}</a></td>
		
	</tr>
	</table>
</ul>

</body>
</html>
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
	<li><form action="${path}/service/task.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>
				维保人员:
				<select name="idservicer">
					<option value="0">-请选择</option>
					<c:forEach items="${slist }" var="s">
						<option value="${s.idservicer }"
						<c:if test="${s.idservicer==param.idservicer }">
							selected="selected"
						</c:if>
						>${s.name }</option>
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
	
	<li><h3>${deptName}&nbsp;&nbsp;维保人员完成工作量</h3>
	<li>
	<table cellpadding="0" cellspacing="1">
	<tr>
		<th>半月维保</th>
		<th>季度维保</th>
		<th>半年维保</th>
		<th>年度维保</th>
	</tr>
	<tr>
		<td><a href="${path }/service/listForTask.do?type=1&start=${start }&end=${end}&idservicer=${param.idservicer}">${data["半月"] eq null?0:data["半月"]}</a></td>
		<td><a href="${path }/service/listForTask.do?type=2&start=${start }&end=${end}&idservicer=${param.idservicer}">${data["季度"] eq null?0:data["季度"]}</a></td>
		<td><a href="${path }/service/listForTask.do?type=3&start=${start }&end=${end}&idservicer=${param.idservicer}">${data["半年"] eq null?0:data["半年"]}</a></td>
		<td><a href="${path }/service/listForTask.do?type=4&start=${start }&end=${end}&idservicer=${param.idservicer}">${data["年检"] eq null?0:data["年检"]}</a></td>
	</tr>
	</table>
</ul>

</body>
</html>
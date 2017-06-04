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
	<script src="${path}/jquery/ui/jquery.ui.button.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.dialog.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.effect.js"></script>
<script type="text/javascript">

function form_submit(ck){
	if(ck){
		$("#form1").submit();
	}
}
</script>
</head>
<body>
<form action="${path }/repair_query/${listUrl}.do" method="get" id="form1">
<input type="hidden" name="iduser" id="iduser" value="${iduser }"/>
<ul>
	
	<li><h3>列表</h3>
		<input type="radio" name="approve_ark" value="-1" onclick="form_submit(this.checked)"
			<c:if test="${noticeType eq -1}">
				checked="checked"
			</c:if>
		/>全部&nbsp;&nbsp;${count }条
		<input type="radio" name="approve_ark" value="0" onclick="form_submit(this.checked)"
			<c:if test="${noticeType eq 0}">
				checked="checked"
			</c:if>
		/>待批复&nbsp;&nbsp;${count_approve }条
		<input type="radio" name="approve_ark" value="1" onclick="form_submit(this.checked)"
			<c:if test="${noticeType eq 1}">
				checked="checked"
			</c:if>
		/>已批复&nbsp;&nbsp;${count_approved }条
		<input type="radio" name="approve_ark" value="3" onclick="form_submit(this.checked)"
			<c:if test="${noticeType eq 3}">
				checked="checked"
			</c:if>
		/>已维修&nbsp;&nbsp;${count_repaired }条
		</form>
	<li><table cellpadding="0" cellspacing="1">
		<tr>
			<th>维修编号</th>
			<th>电梯简称</th>
			<th>申报日期</th>
			<th>业务状态</th>
			<th>批复时间</th>
			<th>申请信息</th>
			<th>批复信息</th>
			<th>维修信息</th>
			
		</tr>
		<c:forEach items="${list}" var="n">
		<tr>
			<td> ${n.rid}</td>
			<td>${n.elevator.desc }</td>
			<td>${n.upload }</td>
			<td>
			<c:choose>
			    <c:when test="${empty n.repairapprove}">
			  		  待批复
			    </c:when>
			    <c:otherwise>
			        ${n.repairapprove.approveType.approve_name}
			    </c:otherwise>
			</c:choose></td>
			<td>${n.repairapprove.approve_date }</td>
			<td>${n.note}</td>
			<td>${n.repairapprove.note }</td>
			<td>${n.repairmaint.note }</td>
		</tr>
		</c:forEach>
		<tr>
		  <td colspan="8" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
</ul>



</body>
</html>
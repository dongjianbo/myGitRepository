<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link href="${path}/css/table.css" rel="stylesheet" type="text/css">
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
	<script type="text/javascript">
	$(function(){
		$("#addNote").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:450,
			buttons:{
				"确定":function(){
					$("#updateForm").submit();		 
				},
				"关闭":function(){
					$(this).dialog("close");
				}
			},
			close:function(){
				$(this).dialog("close");
			}
		});
	})
	
	function updateNote(mi,mii,tit){
		$("#mi").val(mi);
		$("#mispan").html(mi);
		$("#mii").val(mii);
		$("#titspan").html(tit);
		$("#addNote").dialog("open");
	}
	</script>
</head>
<body>
<ul>
	<li><h3>不合格维保项目</h3>
	<li>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>电梯号</th>
			<th>电梯简称</th>
			<th>维保记录号</th>
			<th>维保项目</th>
			<th>备注信息</th>
			<th>扫描时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${list}" var="d">
			<tr>
				<td style="text-align: right">${d.mri.elevator.id_elevator}</td>
				<td style="text-align: left">${d.mri.elevator.desc}</td>
				<td style="text-align: right">${d.maint_id}</td>
				<td style="text-align: left">${d.mid.title}</td>
				<td style="text-align: left">${d.maint_note}</td>
				<td style="text-align: center">${fn:substring(d.maint_date,0,19)}</td>
				<td style="text-align: center"><button onclick="updateNote(${d.maint_id},${d.maint_item_id },'${d.mid.title}')">处理</button></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="9" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div id="addNote" style="display: none" title="不合格维保记录处理">
		<form action="${path }/maint_report_id/updateMaint_Note.do" method="post" id="updateForm">
			<ul>
				<li>维保记录号:<span id="mispan"></span>
				<input type="hidden" id="mi" name="maint_id" size="50" maxlength="9" readonly="readonly"/>
				<li>维保项目:<span id="titspan"></span>
					<input type="hidden" id="mii" name="maint_item_id"/>
				<li>录入说明（不超过140字）:
				<li><textarea rows="15" cols="80" id="mn" name="maint_note" maxlength="140"></textarea>
			</ul>
		</form>
	</div>
</ul>
	
	
</body>
</html>
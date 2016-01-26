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
		$().ready(function(){
			$("#ElevatorDetail").dialog({
				modal:true,
				autoOpen:false,
				width:1050,
				height:600,
				buttons:{
					"确定":function(){
						$(this).dialog("close");
					}
				},
				close:function(){
					$(this).dialog("close");
				}
			});
		});
		function toDetail(id_elevator){
			
			$.post("${path }/elevator/selectElevatorByID.do?id_elevator="+id_elevator,"",function(h){
				$("#ElevatorDetail").html(h);
				$("#ElevatorDetail").dialog("open");
			});
		}
	</script>
</head>
<body>
	<form action="${path}/${requestMapping }/listForSearch.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 电梯出厂编号 <input type="text" name="search" size="50"
					value="${search}" /> 
					<input type="hidden" name="key" value="${key }"/>
					<input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>电梯编号</th>
			<th>电梯型号</th>
			<th>出厂编号</th>
			<th>设计单位</th>
			<th>生产单位</th>
			<th>电梯所在位置</th>
			<th>安装项目负责人</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${list }" var="e">
			<tr>
				<td>${e.id_elevator}</td>
				<td>${e.model.modelname}</td>
				<td>${e.code_manufer }</td>
				<td>${e.designer.iddesigner}</td>
				<td>${e.manufer.idmanufer }</td>
				<td>${e.address}</td>
				<td>${e.project_duty }</td>
				<td><a href="javascript:toDetail(${e.id_elevator})">查看详细</a></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="10" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div title="电梯详细内容" id="ElevatorDetail"></div>
</body>
</html>
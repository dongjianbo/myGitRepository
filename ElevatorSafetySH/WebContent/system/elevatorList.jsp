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
		
		function toService1(id_elevator){
			location.href="${path }/maint_report_id/listForTask.do?maint_type=123&elevator_id="+id_elevator;
// 			$.post("${path }/maint_report_id/listForTask.do?maint_type=123&elevator_id="+id_elevator,"",function(h){
// 				$("#ElevatorDetail").html(h);
// 				$("#ElevatorDetail").dialog("open");
// 			});
		}
		function toService2(id_elevator){
				location.href="${path }/maint_report_id/listForTask.do?maint_type=0&elevator_id="+id_elevator;
// 				$.post("${path }/maint_report_id/listForTask.do?maint_type=0&elevator_id="+id_elevator,"",function(h){
// 					$("#ElevatorDetail").html(h);
// 					$("#ElevatorDetail").dialog("open");
// 				});
		}
		function toService3(id_elevator){
			location.href="${path }/maint_report_id/listForTask.do?maint_type=4&elevator_id="+id_elevator;
// 			$.post("${path }/maint_report_id/listForTask.do?maint_type=4&elevator_id="+id_elevator,"",function(h){
// 				$("#ElevatorDetail").html(h);
// 				$("#ElevatorDetail").dialog("open");
// 			});
		}
		function toRescue_exercise(id_elevator){
			location.href="${path}/rescue_exercise/rescue_exerciseList.do?eid="+id_elevator;
		}
		
	</script>
</head>
<body>
<!-- 	技术监督人员电梯列表不要搜索，与上一个页面重复 -->
	<c:if test="${requestMapping ne 'elevator'}">
		<form action="${path}/${requestMapping }/listForSearch.do" method="post">
			<table cellpadding="0" cellspacing="1">
				<tr>
					<td>按 关键字 <input type="text" name="search" size="50"
						value="${search}" /> 
						<input type="hidden" name="key" value="${key}"/>
						<input type="submit" value="搜索" />
					</td>
				</tr>
			</table>
		</form>
	</c:if>
	
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>电梯编号</th>
			<th>特种设备编码</th>
			<th>电梯简称</th>
			<th>使用单位</th>
			<th>维保单位</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${list }" var="e">
			<tr>
				<td>${e.id_elevator}</td>
				<td>${e.device_code}</td>
				<td>${e.desc}</td>
				<td>${e.user.name }</td>
				<td>${e.service.name}</td>
				<td><a href="javascript:toDetail(${e.id_elevator})">基本信息</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:toService1(${e.id_elevator})">维保记录</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:toService2(${e.id_elevator})">巡视记录</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:toRescue_exercise(${e.id_elevator})">急修演习</a>&nbsp;&nbsp;&nbsp;
<%-- 				<a href="javascript:toService3(${e.id_elevator})">年检记录</a> --%>
				<a href="#">年检记录</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="10" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div title="电梯详细内容" id="ElevatorDetail"></div>
</body>
</html>
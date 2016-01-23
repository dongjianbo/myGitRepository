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
	<script src="${path}/jquery/ui/jquery.ui.datepicker.js"></script><!-- 日期控件的js -->
	<script type="text/javascript">
	$().ready(function(){
		$("#date1").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date2").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date3").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date4").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date5").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date6").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date7").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		
	});
	function shijian(){
		var banyue=$("#date1").val();
		var date2=$("#date2").val();
		var date3=$("#date3").val();
		var date4=$("#date4").val();
		var date5=$("#date5").val();
		var date6=$("#date6").val();
		if(banyue==null||banyue==""){
			return;
		}
		if(date2==null||date2==""){
			return;
		}
		if(date3==null||date3==""){
			return;
		}
		if(date4==null||date4==""){
			return;
		}
		if(date5==null||date5==""){
			return;
		}
		if(date6==null||date6==""){
			return;
		}
		myform.submit();						
		
	}
</script>
</head>
<body>
		<form action="${path }/elevator_state/insertElevator_state.do" method="post" name="myform">
			     <ul>
			        <li>上次半月维保日期:
					<li><input type="text" name="last_15_service" size="50" id="date1" placeholder="请选择上次半月维保日期">
					<li>上次季度维保日期:
					<li><input type="text" name="last_90_service" size="50" id="date2" placeholder="请选择上次季度维保日期">
					<li>上次半年维保日期:
					<li><input type="text" name="last_180_service" size="50" id="date3" placeholder="请选择上次半年维保日期">
				    <li>上次年度维保日期:
				    <li><input type="text" name="last_360_service" size="50" id="date4" placeholder="请选择上次年度维保日期">
				    <li>上次安全员巡检日期:
				    <li><input type="text" name="lastrounds" size="50" id="date5" placeholder="请选择上次安全员巡检日期">
				    <li>上次检验检测日期:
				    <li><input type="text" name="lasttest" size="50" id="date6" placeholder="请选择上次检验检测日期">
				    
				    <input type="button" value="&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;" onclick="shijian()">
				  </ul>
			    
		</form>
</body>
</html>
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
	var dia;
	$().ready(function(){
		$("#date1").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		 $.getJSON("${path}/service/selectId_service.do","rand="+Math.random(),function(d){
	    	  //对维保单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_service").append("<option size='"+50+"' value='"+d[i].idservice+"'>"+d[i].name+"</option>");
	    	  }
	       });	
		 $.getJSON("${path}/test/selectId_test.do","rand="+Math.random(),function(d){
	    	  //对检验检测单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_test").append("<option size='"+50+"' value='"+d[i].idtest+"'>"+d[i].name+"</option>");
	    	  }
	       });	
		
	});
</script>
</head>
<body>
		<form action="${path }/elevator/insert.do" method="post">
			     <ul>
			        <li>开始使用时间:
					<li><input type="text" name="date_enable" size="50" id="date1">
					<li>安装项目责任人:
					<li><input type="text" name="project_duty" size="50">
					<li>维保单位名称:
					<li><select name="id_service" id="id_service"></select>
					<li>检验检测单位名称:
					<li><select name="id_test" id="id_test"></select>
				    <li>电梯层数:
				    <li><input type="text" name="num_floor_elevator" size="50">
				    <li>电梯型号顺序:
				    <li><input type="text" name="id_elevator_model" size="50">
				    <input type="hidden" name="register_status" value="0">
				    <li>电梯描述:
				    <li><input type="text" name="desc" size="50">
				    <li>x轴坐标:
				    <li><input type="text" name="gis_x" size="50">
				    <li>y轴坐标:
				    <li><input type="text" name="gis_y" size="50">
				    <li>坐标类型:
				    <li><input type="text" name="gis_type" size="50"> <input type="submit" value="电梯申报"/>
				   
			     </ul>
			    
		</form>
</body>
</html>
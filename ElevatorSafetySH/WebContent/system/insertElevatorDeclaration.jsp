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
		$("#date2").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date3").datepicker({dateFormat:'yy-mm-dd'});//日期控件
       $.getJSON("${path}/designer/selectId_designer.do","rand="+Math.random(),function(d){
    	  //对设计单位进行循环
    	  for(var i=0;i<d.length;i++){
    		  $("#id_designer").append("<option size='"+50+"' value='"+d[i].iddesigner+"'>"+d[i].name+"</option>");
    	  }
       });
       $.getJSON("${path}/manufer/selectId_manufer.do","rand="+Math.random(),function(d){
     	  //对生产单位进行循环
     	  for(var i=0;i<d.length;i++){
     		  $("#id_manufer").append("<option size='"+50+"' value='"+d[i].idmanufer+"'>"+d[i].name+"</option>");
     	  }
        });
     
		
	});
</script>
</head>
<body>
		<form action="${path }/elevator/insertTo1.do" method="post">
			      <ul>
					<li>登记机构:
					<li><input type="text" placeholder="请填入" name="register_org" size="50"/>
					<li>特种设备登记代码:
					<li><input type="text" name="register_code" size="50"/>
					<li>特种设备代码:
					<li><input type="text" name="device_code" size="50"/>
					<li>设计单位名称:
					<li><select id="id_designer" name="id_designer"></select>
					<li>生产单位名称:
					<li><select id="id_manufer" name="id_manufer"></select>
					<li>生产日期:
					<li><input type="text" name="date_manufer" size="50" id="date1"/>
					<li>出厂编号:
					<li><input type="text" name="code_manufer" size="50"/>
					<li>土建施工单位:
					<li><input type="text" name="constucter" size="50"/>
					<li>土建施工开始时间:
					<li><input type="text" name="startdate_construct" size="50" id="date2"/>
					<li>土建施工竣工时间:
					<li><input type="text" name="enddate_construct" size="50" id="date3"/>
					<li>土建验收单位名称:
					<li><input type="text" name="accepter_construct" size="50"/> <input type="submit" value="下一页"/>
				</ul>
			   
		</form>
</body>
</html>
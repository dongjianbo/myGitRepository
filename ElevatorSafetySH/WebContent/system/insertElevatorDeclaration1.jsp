<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<%-- <link href="${path}/css/table.css" rel="stylesheet" type="text/css"> --%>
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
		 $.getJSON("${path}/installer/selectId_installer.do","rand="+Math.random(),function(d){
	    	  //对安装单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_installer").append("<option size='"+50+"' value='"+d[i].idinstaller+"'>"+d[i].name+"</option>");
	    	  }
	    	 });	
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
	function ifnan(){
		var censhu=$("#censhu").val();
		var xuhao=$("#xuhao").val();
		var date1=$("#date1").val();
		var date2=$("#date2").val();
		var date3=$("#date3").val();
		if(censhu==null||censhu==""){
			alert("电梯层数不能为空!!!");
		}else{
			if(xuhao==null||xuhao==""){
				alert("电梯序号不能为空!!!");
			}else{
				if(isNaN(censhu)){
					alert("电梯层数必须是数字!!!");
				}else{
					if(isNaN(xuhao)){
						alert("电梯型号必须是数字!!!");
					}else{
						if(date1==null||date1==""){
							alert("请选择申报时间");
						}else{
							if(date2==null||date2==""){
								alert("请选择注册时间");
							}else{
								if(date3==null||date3==""){
									alert("请选择开始使用时间");
								}else{
									myform.submit();
								}
							}
						}
					}
					
				}
			}
		}
		
		
	}
</script>
</head>
<body>
		<form action="${path }/elevator/insert.do" method="post" name="myform">
		<table width="100%" border="0" style="margin-top: 20px;">
		  <tr>
		     <td>
		        <ul>
		               <li>电梯所在位置:
					    <li><input type="text" name="address" size="50">  
					    <li>电梯简称:
				        <li><input type="text" name="desc" size="50"><br><br><br>
		                <li>电梯层数:
					    <li><input type="text" name="num_floor_elevator" id="censhu" size="50">
				        <li>电梯型号顺序:
				        <li><input type="text" name="id_elevator_model" id="xuhao" size="50">
				        <li>申报时间:
					    <li><input type="text" name="date_declare" size="50" id="date1"><br><br><br>
						<li>验收检验机构:
						<li><input type="text" name="check_construct" size="50"/>
						<li>验收报告编号:
						<li><input type="text" name="check_construct_code" size="50"/>
					</ul>
				</td>
				<td style="text-align: top;">
				  <ul>
				   
					<li>安装单位名称:
					<li><select name="id_installer" id="id_installer"></select><br><br>
				    <li>注册时间:
					<li><input type="text" name="date_register" size="50" id="date2"><br><br>
			        <li>开始使用时间:
					<li><input type="text" name="date_enable" size="50" id="date3"><br><br>
					<li>安装项目责任人:
					<li><input type="text" name="project_duty" size="50"><br><br>
					<li>维保单位名称:
					<li><select name="id_service" id="id_service"></select><br><br>
					<li>检验检测单位名称:
					<li><select name="id_test" id="id_test"></select><br>
				    
				    <input type="hidden" name="register_status" value="0">
			     
				  </ul>
				</td>
			</tr>
			<tr>
			 <tr>
		        <td colspan="2" style="padding-left: 450px;"> 
		        <input type="button" value="电梯申报" onclick="ifnan()"/>
		        </td>
		     </tr>
		</table>	  
		</form>
</body>
</html>
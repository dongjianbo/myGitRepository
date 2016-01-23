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
		 $.getJSON("${path}/owner/selectId_owner.do","rand="+Math.random(),function(d){
			 $("#id_owner").append("<option size='"+15+"' value=''>-请选择</option>");
	    	  //对产权单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_owner").append("<option size='"+15+"' value='"+d[i].idowner+"'>"+d[i].name+"</option>");
	    	  }
	       });	
		 $.getJSON("${path}/user/selectId_user.do","rand="+Math.random(),function(d){
			 $("#id_user").append("<option size='"+15+"' value=''>-请选择</option>");
	    	  //对使用单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_user").append("<option size='"+15+"' value='"+d[i].iduser+"'>"+d[i].name+"</option>");
	    	  }
	       });	
		$.getJSON("${path}/designer/selectId_designer.do","rand="+Math.random(),function(d){
			$("#id_designer").append("<option size='"+15+"' value=''>-请选择</option>");
    	 //对设计单位进行循环
    	  for(var i=0;i<d.length;i++){
    		  $("#id_designer").append("<option size='"+15+"' value='"+d[i].iddesigner+"'>"+d[i].name+"</option>");
    	  }
       });
       $.getJSON("${path}/manufer/selectId_manufer.do","rand="+Math.random(),function(d){
    	   $("#id_manufer").append("<option size='"+15+"' value=''>-请选择</option>");
     	  //对生产单位进行循环
     	  for(var i=0;i<d.length;i++){
     		  $("#id_manufer").append("<option size='"+15+"' value='"+d[i].idmanufer+"'>"+d[i].name+"</option>");
     	  }
        }); 
       $.getJSON("${path}/installer/selectId_installer.do","rand="+Math.random(),function(d){
    	   $("#id_installer").append("<option size='"+15+"' value=''>-请选择</option>");
	    	  //对安装单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_installer").append("<option size='"+15+"' value='"+d[i].idinstaller+"'>"+d[i].name+"</option>");
	    	  }
	    	 });	
	});
      
	//
	function ifNull(){
		var code=$("#register_code").val();
		var device=$("#device_code").val();
		var date1=$("#date1").val();
		var date2=$("#date2").val();
		var date3=$("#date3").val();
		var desc=$("#desc").val();
		if(code==null||code==""){
 
		}else{
			if(device==null||device==""){
			 
			}else{
				if(date1==null||date1==""){
					
				}else{
					if(date2==null||date2==""){
						
					}else{
						if(date3==null||date3==""){
							
						}else{
							if(desc==null||desc==""){
								
							}else{
								myform.submit();
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
		<form action="${path }/elevator/insertTo1.do" method="post" name="myform">
		<table width="100%" style="margin-top: 20px;">
		
		        <tr>
		           <td>
		           <ul>
		           <li>登记机构:
		           <li><input type="text" name="register_org" size="50" value="${deptName}"/>
		           <li>特种设备登记代码:
		           <li><input type="text" name="register_code" id="register_code" size="50" placeholder="请输入特种设备登记代码"/>*
		           <li>特种设备代码:
		           <li><input type="text" name="device_code" id="device_code" size="50" placeholder="请输入特种设备代码"/>*
			       <li>产权单位名称:
			       <li><select name="id_owner" id="id_owner"></select><span id="id_owner_error"></span>
		           <li>使用单位名称:
		           <li><select name="id_user" id="id_user"></select><span id="id_user_error"></span> <br><br><br>
		           
		           
		            <li>设计单位名称:
					<li><select id="id_designer" name="id_designer"></select><span id="id_designer_error"></span>
					<li>生产单位名称:
					<li><select id="id_manufer" name="id_manufer"></select><span id="id_manufer_error"></span>
		            <li>出厂编号:
					<li><input type="text" name="code_manufer" size="50"/>
					</ul>
		        </td>
		        <td>
		           <ul>
                    <li>生产日期:
					<li><input type="text" name="date_manufer" size="50" id="date1" placeholder="请选择生产日期"/>*
		            <li>土建施工单位:
					<li><input type="text" name="constucter" size="50"/>
					<li>土建施工开始时间:
					<li><input type="text" name="startdate_construct" size="50" id="date2" placeholder="请选择土地建工开始时间"/> * 
		            <li>土建施工竣工时间:
				    <li><input type="text" name="enddate_construct" size="50" id="date3" placeholder="请选择土地施工竣工时间"/>*
				    <li>土建验收单位名称:
				    <li><input type="text" name="accepter_construct" size="50"/>
				    <li>安装单位名称:
				    <li><select name="id_installer" id="id_installer"></select>
				    <li>安装项目责任人:
				    <li><input type="text" name="project_duty" size="50">
					    <br><br><br>
				    <li>电梯简称:
				    <li><input type="text" name="desc" id="desc" size="50" maxlength="16" placeholder="请输入电梯简称,不能超过16字"/>*
				    <input type="hidden" value="0" name="register_status">
				      </ul>
		        </td>
		        </tr>
		            
		        </table>
		        <table width="90%">
		        <tr>
		              <td align="right"><hr>  <input type="button" value="&nbsp;下一页&nbsp;" onclick="ifNull()"/></td>
		             </tr>
		        </table>
		  </form>
</body>
</html>
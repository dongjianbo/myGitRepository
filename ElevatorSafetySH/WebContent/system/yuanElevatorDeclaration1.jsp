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
		$("#date_register").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date_enable").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		 $.getJSON("${path}/modellist/list_json.do","rand="+Math.random(),function(d){
	    	  //对电梯型号进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_elevator_model").append("<option size='"+50+"' value='"+d[i].idmodel+"'>"+d[i].modelname+"</option>");
	    	  }
	       });	
		 //对注册状态进行循环
		  $.getJSON("${path}/register_status_def/list_json.do","rand="+Math.random(),function(d){
	    	  //对电梯型号进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#register_status").append("<option size='"+50+"' value='"+d[i].id_register_status+"'>"+d[i].name+"</option>");
	    	  }
	       });
		 //-----------------------------------------------------------
		 $.getJSON("${path }/citylist/list.do","rand="+Math.random(),function(d){
				//将查询到的信息放入表单
				for(var i=0;i<d.length;i++){
					if(d[i].id_city!="00"&&d[i].id_city!="99")
				  $("#id_city").append("<option size='"+30+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
				}
				//根据第一个城市的id查区域
				$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+$("#id_city").val(),"rand="+Math.random(),function(s){
					for(var i=0;i<s.length;i++){
						if(s[i].id_district=="00"){
							continue;
						}
						  $("#id_district").append("<option size='"+30+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
					}
				 //根据第一个区域查询乡镇
				    $.getJSON("${path }/subdistictlist/listById.do?id_city="+$("#id_city").val()+"&id_distrct="+$("#id_district").val(),"rand="+Math.random(),function(a){
				    	for(var i=0;i<a.length;i++){
				    		if(a[i].id_subdistrict=="00"){
				    			continue;
				    		}
				    		$("#id_subdistrict").append("<option size='"+30+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
				    	}
				    });
				});
			});
		 $.getJSON("${path}/service/list_json.do","rand="+Math.random(),function(d){
	    	  //对维保单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_service").append("<option size='"+15+"' value='"+d[i].idservice+"'>"+d[i].name+"</option>");
	    	  }
	       });
		$.getJSON("${path}/test/list_json.do","rand="+Math.random(),function(d){
		    	  //对检验检测单位进行循环
		    	  for(var i=0;i<d.length;i++){
		    		  $("#id_test").append("<option size='"+15+"' value='"+d[i].idtest+"'>"+d[i].name+"</option>");
		    	  }
		       });
	});
	//选择城市-----------------------------------------------
	function chooseCity(id_city){
		//不同的城市选择不同的id
		$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
			document.getElementById("id_district").innerHTML="";
			for(var i=0;i<s.length;i++){
				if(s[i].id_district=="00"){
					continue;
				}
				 $("#id_district").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
				}
		//选择区域下面的像乡镇
			 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+$("#id_district").val(),"rand="+Math.random(),function(a){
				 document.getElementById("id_subdistrict").innerHTML="";
				 for(var i=0;i<a.length;i++){
					 	if(a[i].id_subdistrict=="00"){
			    			continue;
			    		}
						$("#id_subdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
			    	}
			    });
		});
	}
	//选择区域来获取乡镇-------------------------------------
	function choosedistrict(id_district){
		//去城市的id
		var id_city=document.getElementById("id_city").value;
		 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
			 document.getElementById("id_subdistrict").innerHTML="";
			 for(var i=0;i<a.length;i++){
				 if(a[i].id_subdistrict=="00"){
		    			continue;
		    		}
		    		$("#id_subdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
		    	}
		    });
	}
	function ifnan(){
		var censhu=$("#censhu").val();
		var xuhao=$("#xuhao").val();
		var date1=$("#date1").val();
		var shangci=$("#date_register").val();//上次注册时间
		var start=$("#date_enable").val();
		if(censhu==null||censhu==""){
			
		}else{
				if(isNaN(censhu)){
					alert("电梯层数必须是数字!!!");
					//$("#censhu").val("电梯层数必须是数字!!!");
				}else{
						if(date1==null||date1==""){
						
						}else{
							if(shangci==null||shangci==""){
								
							}else{
								if(start==null||start==""){
									
								}else{
									myform.submit();
								}
							}
							
						}
			}
		}
		
		
	}
</script>
</head>
<body>
		<form action="${path }/elevator/yuaninsert.do" method="post" name="myform">
		<table width="100%" border="0" style="margin-top: 20px;">
		  <tr>
		     <td>
		        <ul>
		                <li>电梯状态:
						<li><select name="register_status" id="register_status"></select>
		                <li>所属城市:
						<li><select name="id_city" id="id_city" onchange="chooseCity(this.value)"></select>
						<li>所属区县:
						<li><select name="id_district" id="id_district" onchange="choosedistrict(this.value)"></select>
						<li>所属街道乡镇:
						<li><select name="id_subdistrict" id="id_subdistrict"></select>
						<li>电梯所在位置:
					    <li><input type="text" name="address" size="50" maxlength="18">  
				        <br><br><br>
				        
		                <li>电梯层数:
					    <li><input type="text" name="num_floor_elevator" id="censhu" size="50" maxlength="2" placeholder="请输入电梯层数">*
				        <li>电梯型号:
				        <li><select name="id_elevator_model" id="id_elevator_model"></select>
                         <li>申报时间:
					    <li><input type="text" name="date_declare" size="50" id="date1" readonly="readonly" placeholder="请选择申报时间">*<br><br><br>
					</ul>
				</td>
				<td style="text-align: top;">
				<ul>
				<li>验收检验机构:
				<li><input type="text" name="check_construct" size="50" maxlength="30"><br><br><br>
				<li>维保单位名称:
				<li><select name="id_service" id="id_service"></select><br>
				<li>检验检测单位名称:
				<li><select name="id_test" id="id_test"></select><br>
				<li>验收报告编号:
				<li><input type="text" id="check_construct_code" name="check_construct_code" size="50" maxlength="20"/><br><br><br>
				<li>注册时间:
				<li><input type="text" id="date_register" name="date_register" size="30" readonly="readonly" placeholder="请选择上次注册时间"/>*<br>
				<li>开始使用时间:
				<li><input type="text" id="date_enable" name="date_enable" size="30" readonly="readonly" placeholder="请选择开始使用时间"/>*<br><br><br><br><br>
				</ul>
				</td>
			</tr>
			<tr>
			
		</table>	  
		<table width="90%">
		        <tr>
		              <td align="right"><hr>  <input type="button" value="&nbsp;资料录入&nbsp;" onclick="ifnan()"/></td>
		             </tr>
		        </table>
		</form>
</body>
</html>
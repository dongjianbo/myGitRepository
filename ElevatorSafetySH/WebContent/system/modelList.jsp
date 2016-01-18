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
	var dia;
	$().ready(function(){
// 		var $doc;         
// 		if(window.location!=window.top.location){          
// 			// <SPAN style="COLOR: #ff0000">页面在iframe中</SPAN>            
// 			$doc=window.top.jQuery.noConflict();
// 		}else{
// 			// <SPAN style="COLOR: #ff0000">页面单独打开</SPAN>
// 			$doc=jQuery.noConflict();
// 		}         
// 		$doc("body").append('<div id="dialog"  style="display: none" title="添加"></div>');
// 		dia=$doc("#dialog");
// 		dia.html($("#insertDialog").html());
		$("#insertDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:500,
			buttons:{
				"确定":function(fn){
					var form = $("#insertForm");
					var modelname1=$("#modelname1").val();
					if(modelname1==null||modelname1==""){
						$("#modelname2").html("<i>电梯型号不能为空 !!!</i>");
					}else{
						$("#modelname2").html("");
			            var suitplace1=$("#suitplace1").val();
					    if(suitplace1==null||suitplace1==""){
						  $("#suitplace2").html("<i>使用场合不能为空 !!!</i>");
						 }else{
						   $("#suitplace2").html("");
						   //=========判断表单中的 有些文本框是否输入的是数字 ============== 
						      var parameter12_1=$("#parameter12_1").val();
						    if(isNaN(parameter12_1)){
						    	$("#parameter12_2").html("<i>请输入数字!!!</i>");
						   }else{
							   $("#parameter12_2").html("");
							   
							   var parameter13_1=$("#parameter13_1").val();
							    if(isNaN(parameter13_1)){
							    	$("#parameter13_2").html("<i>请输入数字!!!</i>");
							   }else{
								   $("#parameter13_2").html("");
								   var parameter14_1=$("#parameter14_1").val();
								    if(isNaN(parameter14_1)){
								    	$("#parameter14_2").html("<i>请输入数字!!!</i>");
								   }else{
									   $("#parameter14_2").html("");
								   
								    var parameter21_1=$("#parameter21_1").val();
								    if(isNaN(parameter21_1)){
								    	$("#parameter21_2").html("<i>请输入数字!!!</i>");
								    }else{
									   $("#parameter21_2").html("");
									   var parameter22_1=$("#parameter22_1").val();
									    if(isNaN(parameter22_1)){
									    	$("#parameter22_2").html("<i>请输入数字!!!</i>");
									   }else{
										   $("#parameter22_2").html("");
										   
										   var parameter23_1=$("#parameter23_1").val();
										    if(isNaN(parameter23_1)){
										    	$("#parameter23_2").html("<i>请输入数字!!!</i>");
										   }else{
											   $("#parameter23_2").html("");
											   var parameter24_1=$("#parameter24_1").val();
											    if(isNaN(parameter24_1)){
											    	$("#parameter24_2").html("<i>请输入数字!!!</i>");
											   }else{
												   $("#parameter24_2").html("");
												   var parameter32_1=$("#parameter32_1").val();
												    if(isNaN(parameter32_1)){
												    	$("#parameter32_2").html("<i>请输入数字!!!</i>");
												   }else{
													   $("#parameter32_2").html("");
													   var parameter33_1=$("#parameter33_1").val();
													    if(isNaN(parameter33_1)){
													    	$("#parameter33_2").html("<i>请输入数字!!!</i>");
													   }else{
														   $("#parameter33_2").html("");
														   var parameter34_1=$("#parameter34_1").val();
														    if(isNaN(parameter34_1)){
														    	$("#parameter34_2").html("<i>请输入数字!!!</i>");
														   }else{
															   $("#parameter34_2").html("");
															   var parameter41_1=$("#parameter41_1").val();
															    if(isNaN(parameter41_1)){
															    	$("#parameter41_2").html("<i>请输入数字!!!</i>");
															   }else{
																   $("#parameter41_2").html("");
																   var parameter42_1=$("#parameter42_1").val();
																    if(isNaN(parameter42_1)){
																    	$("#parameter42_2").html("<i>请输入数字!!!</i>");
																   }else{
																	   $("#parameter42_2").html("");
																	   var parameter43_1=$("#parameter43_1").val();
																	    if(isNaN(parameter43_1)){
																	    	$("#parameter43_2").html("<i>请输入数字!!!</i>");
																	   }else{
																		   $("#parameter43_2").html("");
																		   var parameter44_1=$("#parameter44_1").val();
																		    if(isNaN(parameter44_1)){
																		    	$("#parameter44_2").html("<i>请输入数字!!!</i>");
																		   }else{
																			   $("#parameter44_2").html("");
																			   var parameter45_1=$("#parameter45_1").val();
																			    if(isNaN(parameter45_1)){
																			    	$("#parameter45_2").html("<i>请输入数字!!!</i>");
																			   }else{
																				   $("#parameter45_2").html("");
																				   var parameter46_1=$("#parameter46_1").val();
																				    if(isNaN(parameter46_1)){
																				    	$("#parameter46_2").html("<i>请输入数字!!!</i>");
																				   }else{
																					   $("#parameter46_2").html("");
																					   
																					    //提交表单 
																						$.post(form.attr('action'),form.serialize(),function(a){
																							if(a=="ok"){
																								location.reload();
																							}else{
																								alert("程序有点问题哟！");
																							}
																						});
																				   }
																			   }
																		   }
																	   }
																   }
															   }
														   }
													   }
												   }
											   }
										   }
									   }
								   }
							   }
						   }
						   }  
						   
						 
				 }
			    }
				},
				"关闭":function(){
					$(this).dialog("close");
				}
			},
			close:function(){
				$(this).dialog("close");
			}
		});
		
		//查询生产厂家（下拉框中的值 ）
			$.getJSON("${path }/manufer/list_json.do","rand="+Math.random(),function(d){
				  //将查询到的信息放入修改表单中--注意隐藏域中的主键
				for(var i=0;i<d.length;i++){
					$("#idmanufer").append("<option size='"+50+"' value='"+d[i].idmanufer+"'>"+d[i].name+"</option>");
					$("#idmanufer1").append("<option size='"+50+"' value='"+d[i].idmanufer+"'>"+d[i].name+"</option>");
				}
			}); 
		 //查询电梯类型 （下拉框 的值 ）
		$.getJSON("${path }/elevator_type_def/list_json.do","rand="+Math.random(),function(d){
				  //将查询到的信息放入修改表单中--注意隐藏域中的主键
				for(var i=0;i<d.length;i++){
					$("#typeElevator").append("<option size='"+50+"' value='"+d[i].elevatortype+"'>"+d[i].name+"</option>");
					$("#typeElevator1").append("<option size='"+50+"' value='"+d[i].elevatortype+"'>"+d[i].name+"</option>");
				}
			});
		$("#updateDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:500,
			buttons:{
				"确定":function(){
					var form = $("#updateForm");
					var modelname=$("#modelname").val();
					if(modelname==null||modelname==""){
						$("#modelname0").html("<i>电梯型号不能为空 !!!</i>");
					}else{
						$("#modelname0").html("");
			            var suitplace=$("#suitplace").val();
					    if(suitplace==null||suitplace==""){
						  $("#suitplace0").html("<i>使用场合不能为空 !!!</i>");
						 }else{
						   $("#suitplace0").html("");
						   
						   //=========判断表单中的 有些文本框是否输入的是数字 ============== 
						      var parameter12=$("#parameter12").val();
						    if(isNaN(parameter12)){
						    	$("#parameter12_0").html("<i>请输入数字!!!</i>");
						   }else{
							   $("#parameter12_0").html("");
							   
							   var parameter13=$("#parameter13").val();
							    if(isNaN(parameter13)){
							    	$("#parameter13_0").html("<i>请输入数字!!!</i>");
							   }else{
								   $("#parameter13_0").html("");
								   var parameter14=$("#parameter14").val();
								    if(isNaN(parameter14)){
								    	$("#parameter14_0").html("<i>请输入数字!!!</i>");
								   }else{
									   $("#parameter14_0").html("");
								   
								    var parameter21=$("#parameter21").val();
								    if(isNaN(parameter21)){
								    	$("#parameter21_0").html("<i>请输入数字!!!</i>");
								    }else{
									   $("#parameter21_0").html("");
									   var parameter22=$("#parameter22").val();
									    if(isNaN(parameter22)){
									    	$("#parameter22_0").html("<i>请输入数字!!!</i>");
									   }else{
										   $("#parameter22_0").html("");
										   
										   var parameter23=$("#parameter23").val();
										    if(isNaN(parameter23)){
										    	$("#parameter23_0").html("<i>请输入数字!!!</i>");
										   }else{
											   $("#parameter23_0").html("");
											   var parameter24=$("#parameter24").val();
											    if(isNaN(parameter24)){
											    	$("#parameter24_0").html("<i>请输入数字!!!</i>");
											   }else{
												   $("#parameter24_0").html("");
												   var parameter32=$("#parameter32").val();
												    if(isNaN(parameter32)){
												    	$("#parameter32_0").html("<i>请输入数字!!!</i>");
												   }else{
													   $("#parameter32_0").html("");
													   var parameter33=$("#parameter33").val();
													    if(isNaN(parameter33)){
													    	$("#parameter33_0").html("<i>请输入数字!!!</i>");
													   }else{
														   $("#parameter33_0").html("");
														   var parameter34=$("#parameter34").val();
														    if(isNaN(parameter34)){
														    	$("#parameter34_0").html("<i>请输入数字!!!</i>");
														   }else{
															   $("#parameter34_0").html("");
															   var parameter41=$("#parameter41").val();
															    if(isNaN(parameter41)){
															    	$("#parameter41_0").html("<i>请输入数字!!!</i>");
															   }else{
																   $("#parameter41_0").html("");
																   var parameter42=$("#parameter42").val();
																    if(isNaN(parameter42)){
																    	$("#parameter42_0").html("<i>请输入数字!!!</i>");
																   }else{
																	   $("#parameter42_0").html("");
																	   var parameter43=$("#parameter43").val();
																	    if(isNaN(parameter43)){
																	    	$("#parameter43_0").html("<i>请输入数字!!!</i>");
																	   }else{
																		   $("#parameter43_0").html("");
																		   var parameter44=$("#parameter44").val();
																		    if(isNaN(parameter44)){
																		    	$("#parameter44_0").html("<i>请输入数字!!!</i>");
																		   }else{
																			   $("#parameter44_0").html("");
																			   var parameter45=$("#parameter45").val();
																			    if(isNaN(parameter45)){
																			    	$("#parameter45_0").html("<i>请输入数字!!!</i>");
																			   }else{
																				   $("#parameter45_0").html("");
																				   var parameter46=$("#parameter46").val();
																				    if(isNaN(parameter46)){
																				    	$("#parameter46_0").html("<i>请输入数字!!!</i>");
																				   }else{
																					   $("#parameter46_0").html("");
																					
																					   //提交表单 
																						$.post(form.attr('action'),form.serialize(),function(a){
																							if(a=="ok"){
																								location.reload();
																							}else{
																								alert("程序有点问题哟！");
																							}
																						});
																				   }
																			   }
																		   }
																	   }
																   }
															   }
														   }
													   }
												   }
											   }
										   }
									   }
								   }
							   }
						   }
						   }  
				 }
			    }	   
				},
				"关闭":function(){
					$(this).dialog("close");
				}
			},
			close:function(){
				$(this).dialog("close");
			}
		});
	});

	function showInsert(){
		
// 		dia.dialog("open");
		$("#insertDialog").dialog("open");
	}
	function showUpdate(did){
		
		
	 $.getJSON("${path }/modellist/toUpdate.do?idmodel="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			
			$("#idmanufer").val(d.idmanufer);
			$("#modelname").val(d.modelname);
			$("#typeElevator").val(d.typeElevator);
			$("#suitplace").val(d.suitplace);
			$("#idmodel").val(d.idmodel);
			
			$("#parameter11").val(d.parameter11);
			$("#parameter12").val(d.parameter12);
			$("#parameter13").val(d.parameter13);
			$("#parameter14").val(d.parameter14);
			$("#parameter21").val(d.parameter21);
			$("#parameter22").val(d.parameter22);
			$("#parameter23").val(d.parameter23);
			$("#parameter24").val(d.parameter24);
			$("#parameter25").val(d.parameter25);
			$("#parameter31").val(d.parameter31);
			$("#parameter32").val(d.parameter32);
			$("#parameter33").val(d.parameter33);
			$("#parameter34").val(d.parameter34);
			$("#parameter41").val(d.parameter41);
			$("#parameter42").val(d.parameter42);
			$("#parameter43").val(d.parameter43);
			$("#parameter44").val(d.parameter44);
			$("#parameter45").val(d.parameter45);
			$("#parameter46").val(d.parameter46);
			$("#idmodel").val(d.idmodel);
			
			//打开修改对话框
			$("#updateDialog").dialog("open");
			
		});
		
	}
	function showSelectValue(){
		
	}
</script>

</head>

<body>
	<form action="${path}/modellist/list.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 电梯型号/适用场合 <input type="text" name="key" size="50"
					value="${param.key}" /> <input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>电梯型号流水号</th>
			<th>生产厂家</th>
			<th>电梯型号</th>
			<th>电梯类型</th>
			<th>适用场合</th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${modelList}" var="d">
			<tr>
				<td>${d.idmodel}</td>
				<td>${d.manufer.name }</td>
				<td>${d.modelname}</td>
				<td>${d.elevator_type_def.name}</td>
				<td>${d.suitplace}</td>
				<td><a
				       href="javascript:showUpdate(${d.idmodel})">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="${path }/modellist/delete.do?idmodel=${d.idmodel}">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="8" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div align="right">
		<input type="button" value="添加一个电梯型号"
		onclick="showInsert()" />
	</div>

 <div id="insertDialog" style="display: none" title="添加">
		<form action="${path }/modellist/insert.do" method="post" id="insertForm">
			<ul>
				<li>生产厂家:
				<li><select name="idmanufer" id="idmanufer1"></select>
				<li>电梯型号:
				<li><input type="text" name="modelname" id="modelname1" maxlength="20" size="50"/>*<div id="modelname2" style="float: right; margin-right:220 " ></div>
				<li>电梯类型:
				<li><select name="typeElevator" id="typeElevator1"></select>
				<li>适用场合:
				<li><input type="text" name="suitplace" id="suitplace1" maxlength="20" size="50"/>*<div id="suitplace2" style="float: right; margin-right:220 " ></div>
				<li>1类驱动方式：
				<li><input type="text" name="parameter11" maxlength="1" size="50"/>
				<li>1类额定载重量:（单位：千克）
				<li><input type="text" name="parameter12" id="parameter12_1" maxlength="11" size="50"/><div id="parameter12_2" style="float: right; margin-right:220 " ></div>
				<li>1类额定速度:（单位：m/s）
				<li><input type="text" name="parameter13" id="parameter13_1" maxlength="5" size="50"/><div id="parameter13_2" style="float: right; margin-right:220 " ></div>
				<li>1类层站数:
				<li><input type="text" name="parameter14" id="parameter14_1" maxlength="11" size="50"/><div id="parameter14_2" style="float: right; margin-right:220 " ></div>
				<li>2类额定载重量 （单位：千克）
				<li><input type="text" name="parameter21" id="parameter21_1"  maxlength="11" size="50"/><div id="parameter21_2" style="float: right; margin-right:220 " ></div>
				<li>2类额定速度  （单位：m/s）
				<li><input type="text" name="parameter22" id="parameter22_1" maxlength="5" size="50"/><div id="parameter22_2" style="float: right; margin-right:220 " ></div>
				<li>2类层站数
				<li><input type="text" name="parameter23" id="parameter23_1" maxlength="11"  size="50"/><div id="parameter23_2" style="float: right; margin-right:220 " ></div>
				<li>2类油缸数量
				<li><input type="text" name="parameter24" id="parameter24_1" maxlength="11"  size="50"/><div id="parameter24_2" style="float: right; margin-right:220 " ></div>
				<li>2类顶升方式 
				<li><input type="text" name="parameter25" maxlength="1" size="50"/>
				<li>3类驱动方式
				<li><input type="text" name="parameter31" maxlength="1" size="50"/>
				<li>3类额定载重量 （单位=kg）
				<li><input type="text" name="parameter32" id="parameter32_1" maxlength="11"  size="50"/><div id="parameter32_2" style="float: right; margin-right:220 " ></div>
				<li>3类额定速度 （单位=m/s）
				<li><input type="text" name="parameter33" id="parameter33_1" maxlength="5" size="50"/><div id="parameter33_2" style="float: right; margin-right:220 " ></div>
				<li>3类层站数
				<li><input type="text" name="parameter34" id="parameter34_1" maxlength="11" size="50"/><div id="parameter34_2" style="float: right; margin-right:220 " ></div>
				<li>4类倾斜角度
				<li><input type="text" name="parameter41" id="parameter41_1" maxlength="11"  size="50"/><div id="parameter42_2" style="float: right; margin-right:220 " ></div>
				<li>4类额定速度（单位=m/s）
				<li><input type="text" name="parameter42" id="parameter42_1" maxlength="5" size="50"/><div id="parameter42_2" style="float: right; margin-right:220 " ></div>
				<li>4类提升高度（单位=m）
				<li><input type="text" name="parameter43" id="parameter43_1" maxlength="5" size="50"/><div id="parameter43_2" style="float: right; margin-right:220 " ></div>
				<li>4类梯级宽度（单位=m）
				<li><input type="text" name="parameter44" id="parameter44_1" maxlength="5" size="50"/><div id="parameter44_2" style="float: right; margin-right:220 " ></div>
				<li>4类主机功率（单位=kw）
				<li><input type="text" name="parameter45" id="parameter45_1" maxlength="5" size="50"/><div id="parameter45_2" style="float: right; margin-right:220 " ></div>
				<li>4类使用区长度（单位=m）
				<li><input type="text" name="parameter46" id="parameter46_1" maxlength="5" size="50"/><div id="parameter46_2" style="float: right; margin-right:220 " ></div>
	
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/modellist/update.do" method="post" id="updateForm">
			<ul>
				<li>生产厂家:
				<li><select name="idmanufer" id="idmanufer"></select>
				<li>电梯型号:
				<li><input type="text" name="modelname" id="modelname" maxlength="20" size="50"/>*<div id="modelname0" style="float: right; margin-right:220 " ></div>
				<li>电梯类型:
				<li><select name="typeElevator" id="typeElevator"></select>
				<li>适用场合:
				<li><input type="text" name="suitplace" id="suitplace" maxlength="20" size="50"/>*<div id="suitplace0" style="float: right; margin-right:220 " ></div>
				<li>1类驱动方式：
				<li><input type="text" name="parameter11" id="parameter11" maxlength="1" size="50"/>
				<li>1类额定载重量:（单位：千克）
				<li><input type="text" name="parameter12" id="parameter12" maxlength="11" size="50"/><div id="parameter12_0" style="float: right; margin-right:220 " ></div>
				<li>1类额定速度:（单位：m/s）
				<li><input type="text" name="parameter13" id="parameter13" maxlength="5" size="50"/><div id="parameter13_0" style="float: right; margin-right:220 " ></div>
				<li>1类层站数:
				<li><input type="text" name="parameter14" id="parameter14" maxlength="11" size="50"/><div id="parameter14_0" style="float: right; margin-right:220 " ></div>
				<li>2类额定载重量 （单位：千克）
				<li><input type="text" name="parameter21" id="parameter21" maxlength="11" size="50"/><div id="parameter21_0" style="float: right; margin-right:220 " ></div>
				<li>2类额定速度  （单位：m/s）
				<li><input type="text" name="parameter22" id="parameter22" maxlength="5" size="50"/><div id="parameter22_0" style="float: right; margin-right:220 " ></div>
				<li>2类层站数
				<li><input type="text" name="parameter23" id="parameter23" maxlength="11" size="50"/><div id="parameter23_0" style="float: right; margin-right:220 " ></div>
				<li>2类油缸数量
				<li><input type="text" name="parameter24" id="parameter24" maxlength="11" size="50"/><div id="parameter24_0" style="float: right; margin-right:220 " ></div>
				<li>2类顶升方式 
				<li><input type="text" name="parameter25"  id="parameter25" maxlength="1" size="50"/>
				<li>3类驱动方式
				<li><input type="text" name="parameter31" id="parameter31" maxlength="1" size="50"/>
				<li>3类额定载重量 （单位=kg）
				<li><input type="text" name="parameter32" id="parameter32" maxlength="11" size="50"/><div id="parameter32_0" style="float: right; margin-right:220 " ></div>
				<li>3类额定速度 （单位=m/s）
				<li><input type="text" name="parameter33" id="parameter33" maxlength="5" size="50"/><div id="parameter33_0" style="float: right; margin-right:220 " ></div>
				<li>3类层站数
				<li><input type="text" name="parameter34" id="parameter34" maxlength="11" size="50"/><div id="parameter34_0" style="float: right; margin-right:220 " ></div>
				<li>4类倾斜角度
				<li><input type="text" name="parameter41" id="parameter41" maxlength="11" size="50"/><div id="parameter41_0" style="float: right; margin-right:220 " ></div>
				<li>4类额定速度（单位=m/s）
				<li><input type="text" name="parameter42" id="parameter42" maxlength="5" size="50"/><div id="parameter42_0" style="float: right; margin-right:220 " ></div>
				<li>4类提升高度（单位=m）
				<li><input type="text" name="parameter43"  id="parameter43" maxlength="5" size="50"/><div id="parameter43_0" style="float: right; margin-right:220 " ></div>
				<li>4类梯级宽度（单位=m）
				<li><input type="text" name="parameter44" id="parameter44" maxlength="5" size="50"/><div id="parameter44_0" style="float: right; margin-right:220 " ></div>
				<li>4类主机功率（单位=kw）
				<li><input type="text" name="parameter45" id="parameter45" maxlength="5" size="50"/><div id="parameter45_0" style="float: right; margin-right:220 " ></div>
				<li>4类使用区长度（单位=m）
				<li><input type="text" name="parameter46" id="parameter46" maxlength="5" size="50"/><div id="parameter46_0" style="float: right; margin-right:220 " ></div>
				<li><input type="hidden" name="idmodel" id="idmodel" />
			</ul>
		</form>
	</div>
</body>
</html>
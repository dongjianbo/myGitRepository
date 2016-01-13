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
					$.post(form.attr('action'),form.serialize(),function(a){
						if(a=="ok"){
							location.reload();
						}else{
							alert("程序有点问题哟！");
						}
					});
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
					$.post(form.attr('action'),form.serialize(),function(a){
						if(a=="ok"){
							location.reload();
						}else{
							alert("程序有点问题哟！");
						}
					});
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
				<td>${d.idmanufer }</td>
				<td>${d.modelname}</td>
				<td>${d.typeElevator}</td>
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
				<li><input type="text" name="modelname" size="50"/>
				<li>电梯类型:
				<li><select name="typeElevator" id="typeElevator1"></select>
				<li>适用场合:
				<li><input type="text" name="suitplace" size="50"/>
				<li>1类驱动方式：
				<li><input type="text" name="parameter11" size="50"/>
				<li>1类额定载重量:（单位：千克）
				<li><input type="text" name="parameter12" size="50"/>
				<li>1类额定速度:（单位：m/s）
				<li><input type="text" name="parameter13" size="50"/>
				<li>1类层站数:
				<li><input type="text" name="parameter14" size="50"/>
				<li>2类额定载重量 （单位：千克）
				<li><input type="text" name="parameter21" size="50"/>
				<li>2类额定速度  （单位：m/s）
				<li><input type="text" name="parameter22" size="50"/>
				<li>2类层站数
				<li><input type="text" name="parameter23" size="50"/>
				<li>2类油缸数量
				<li><input type="text" name="parameter24" size="50"/>
				<li>2类顶升方式 
				<li><input type="text" name="parameter25" size="50"/>
				<li>3类驱动方式
				<li><input type="text" name="parameter31" size="50"/>
				<li>3类额定载重量 （单位=kg）
				<li><input type="text" name="parameter32" size="50"/>
				<li>3类额定速度 （单位=m/s）
				<li><input type="text" name="parameter33" size="50"/>
				<li>3类层站数
				<li><input type="text" name="parameter34" size="50"/>
				<li>4类倾斜角度
				<li><input type="text" name="parameter41" size="50"/>
				<li>4类额定速度（单位=m/s）
				<li><input type="text" name="parameter42" size="50"/>
				<li>4类提升高度（单位=m）
				<li><input type="text" name="parameter43" size="50"/>
				<li>4类梯级宽度（单位=m）
				<li><input type="text" name="parameter44" size="50"/>
				<li>4类主机功率（单位=kw）
				<li><input type="text" name="parameter45" size="50"/>
				<li>4类使用区长度（单位=m）
				<li><input type="text" name="parameter46" size="50"/>
	
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/modellist/update.do" method="post" id="updateForm">
			<ul>
				<li>生产厂家:
				<li><select name="idmanufer" id="idmanufer"></select>
				<li>电梯型号:
				<li><input type="text" name="modelname" id="modelname" size="50"/>
				<li>电梯类型:
				<li><select name="typeElevator" id="typeElevator"></select>
				<li>适用场合:
				<li><input type="text" name="suitplace" id="suitplace" size="50"/>
				<li>1类驱动方式：
				<li><input type="text" name="parameter11" id="parameter11" size="50"/>
				<li>1类额定载重量:（单位：千克）
				<li><input type="text" name="parameter12" id="parameter12" size="50"/>
				<li>1类额定速度:（单位：m/s）
				<li><input type="text" name="parameter13" id="parameter13" size="50"/>
				<li>1类层站数:
				<li><input type="text" name="parameter14" id="parameter14" size="50"/>
				<li>2类额定载重量 （单位：千克）
				<li><input type="text" name="parameter21" id="parameter21" size="50"/>
				<li>2类额定速度  （单位：m/s）
				<li><input type="text" name="parameter22" id="parameter22" size="50"/>
				<li>2类层站数
				<li><input type="text" name="parameter23" id="parameter23" size="50"/>
				<li>2类油缸数量
				<li><input type="text" name="parameter24" id="parameter24" size="50"/>
				<li>2类顶升方式 
				<li><input type="text" name="parameter25"  id="parameter25" size="50"/>
				<li>3类驱动方式
				<li><input type="text" name="parameter31" id="parameter31" size="50"/>
				<li>3类额定载重量 （单位=kg）
				<li><input type="text" name="parameter32" id="parameter32" size="50"/>
				<li>3类额定速度 （单位=m/s）
				<li><input type="text" name="parameter33" id="parameter33" size="50"/>
				<li>3类层站数
				<li><input type="text" name="parameter34" id="parameter34" size="50"/>
				<li>4类倾斜角度
				<li><input type="text" name="parameter41" id="parameter41" size="50"/>
				<li>4类额定速度（单位=m/s）
				<li><input type="text" name="parameter42" id="parameter42" size="50"/>
				<li>4类提升高度（单位=m）
				<li><input type="text" name="parameter43"  id="parameter43" size="50"/>
				<li>4类梯级宽度（单位=m）
				<li><input type="text" name="parameter44" id="parameter44" size="50"/>
				<li>4类主机功率（单位=kw）
				<li><input type="text" name="parameter45" id="parameter45" size="50"/>
				<li>4类使用区长度（单位=m）
				<li><input type="text" name="parameter46" id="parameter46" size="50"/>
				<li><input type="hidden" name="idmodel" id="idmodel" />
			</ul>
		</form>
	</div>
</body>
</html>
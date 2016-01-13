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
//查询城市id
		$.getJSON("${path }/citylist/list.do","rand="+Math.random(),function(d){
			//将查询到的信息放入表单
			for(var i=0;i<d.length;i++){
			  $("#idcity").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
			  $("#idcity1").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
			}
			//根据第一个城市的id查区域
			$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+d[0].id_city,"rand="+Math.random(),function(s){
				for(var i=0;i<s.length;i++){
					  $("#iddistrict").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
					  $("#iddistrict1").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
					}
			 //根据第一个区域查询乡镇
			    $.getJSON("${path }/subdistictlist/listById.do?id_city="+d[0].id_city+"&id_distrct="+s[0].id_district,"rand="+Math.random(),function(a){
			    	for(var i=0;i<a.length;i++){
			    		$("#idsubdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
			    		$("#idsubdistrict1").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
			    	}
			    });
			});
		});
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
		// 查询所有的 权限类别流水号 (下拉框的值 )
		  $.getJSON("${path }/role/list_json.do","rand="+Math.random(),function(d){
			  //将查询到的信息放入修改表单中--注意隐藏域中的主键
			for(var i=0;i<d.length;i++){
				$("#idprivilege").append("<option size='"+50+"' value='"+d[i].idrole+"'>"+d[i].idrole+"</option>");
				$("#idprivilege1").append("<option size='"+50+"' value='"+d[i].idrole+"'>"+d[i].idrole+"</option>");
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
	
	//选择城市-----------------------------------------------
	function chooseCity(id_city){
		//不同的城市选择不同的id
		$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
			document.getElementById("iddistrict").innerHTML="";
			for(var i=0;i<s.length;i++){
				 $("#iddistrict").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
				}
		//选择区域下面的像乡镇
			 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+s[0].id_district,"rand="+Math.random(),function(a){
				 document.getElementById("idsubdistrict").innerHTML="";	
				 for(var i=0;i<a.length;i++){
						$("#idsubdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
			    	}
			    });
		});
	}
	function chooseCity1(id_city){
		//不同的城市选择不同的id
		$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
			document.getElementById("iddistrict1").innerHTML="";
			for(var i=0;i<s.length;i++){
				 $("#iddistrict1").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
				}
		//选择区域下面的像乡镇
			 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+s[0].id_district,"rand="+Math.random(),function(a){
				 document.getElementById("idsubdistrict1").innerHTML="";	
				 for(var i=0;i<a.length;i++){
						$("#idsubdistrict1").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
			    	}
			    });
		});
	}
	//选择区域来获取乡镇-------------------------------------
	function choosedistrict(id_district){
		//去城市的id
		var id_city=document.getElementById("idcity").value;
		 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
			 document.getElementById("idsubdistrict").innerHTML="";	
			 for(var i=0;i<a.length;i++){
		    		$("#idsubdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
		    	}
		    });
	}
	function choosedistrict1(id_district){
		//去城市的id
		var id_city=document.getElementById("idcity1").value;
		 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
			 document.getElementById("idsubdistrict1").innerHTML="";	
			 for(var i=0;i<a.length;i++){
		    		$("#idsubdistrict1").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
		    	}
		    });
	}
	function showInsert(){
		//查询所有的 维保公司的名字 
// 		dia.dialog("open");
		$("#insertDialog").dialog("open");
	}
	function showUpdate(did){
		
		$.getJSON("${path }/operator/toUpdate.do?idoperator="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			
			$("#idprivilege").val(d.idprivilege);
			$("#name").val(d.name);
			$("#idcard").val(d.idcard);
			$("#idcity").val(d.idcity);
			$("#iddistrict").val(d.iddistrict);
			$("#idsubdistrict").val(d.idsubdistrict);
			$("#loginname").val(d.loginname);
			$("#password").val(d.password);
			$("#idOrganization").val(d.idOrganization);
			$("#status").val(d.status);
			$("#idoperator").val(d.idoperator);
			//打开修改对话框
			$("#updateDialog").dialog("open");
			
		});
	}
</script>
</head>
<body>
	<form action="${path}/operator/list.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 姓名/身份证号 <input type="text" name="key" size="50"
					value="${param.key}" /> <input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>编号</th>
			<th>权限类别流水号</th>
			<th>操作员姓名</th>
			<th>身份证号码</th>
			<th>所属城市编号</th>
			<th>所属区县编号</th>
			<th>所属乡镇街道编码</th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${operatorList}" var="d">
			<tr>
				<td>${d.idoperator}</td>
				<td>${d.idprivilege }</td>
				<td>${d.name}</td>
				<td>${d.idcard}</td>
				<td>${d.idcity }</td>
				<td>${d.iddistrict }</td>
				<td>${d.idsubdistrict}</td>
				
				<td><a
					href="javascript:showUpdate(${d.idoperator})">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="${path }/operator/delete.do?idoperator=${d.idoperator}">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="8" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div align="right">
		<input type="button" value="添加一个监督人员"
			onclick="showInsert()" />
	</div>
	
 <div id="insertDialog" style="display: none" title="添加">
		<form action="${path }/operator/insert.do" method="post" id="insertForm">
			<ul>
			    <li>权限类别流水号:
				<li><select name="idprivilege" id="idprivilege1"></select>
				<li>操作员姓名:
				<li><input type="text" name="name" size="50"/>
				<li>身份证号码:
				<li><input type="text" name="idcard" size="50"/>
				<li>所属城市编号:
				<li><select name="idcity" id="idcity1" onchange="chooseCity(this.value)"></select>
				<li>所属区县编号:
				<li><select name="iddistrict" id="iddistrict1" onchange="choosedistrict(this.value)"></select> 
				<li>所属乡镇街道编码:
				<li><select id="idsubdistrict1" name="idsubdistrict"></select>
				<li>登陆名:
				<li><input type="text" name="loginname" size="50"/>
				<li>密码:
				<li><input type="text" name="password" size="50"/>
				<li>所属单位顺序号:
				<li><input type="text" name="idOrganization" size="50"/>
				<li>技术监督局操作员状态:
				<li><input type="text" name="status" size="50"/>
				<li><input type="hidden" name="typeOperator" value="00">
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/operator/update.do" method="post" id="updateForm">
			<ul>
			    <li>权限类别流水号:
				<li><select name="idprivilege" id="idprivilege"></select>
				<li>操作员姓名:
				<li><input type="text" name="name" id="name" size="50"/>
				<li>身份证号码:
				<li><input type="text" name="idcard" id="idcard" size="50"/>
				<li>所属城市编号:
				<li><select name="idcity" id="idcity" onchange="chooseCity(this.value)"></select>
				<li>所属区县编号:
				<li><select name="iddistrict" id="iddistrict" onchange="choosedistrict(this.value)"></select> 
				<li>所属乡镇街道编码:
				<li><select id="idsubdistrict" name="idsubdistrict"></select>
				<li>登陆名:
				<li><input type="text" name="loginname" id="loginname" size="50"/>
				<li>密码:
				<li><input type="text" name="password" id="password" size="50"/>
				<li>所属单位顺序号:
				<li><input type="text" name="idOrganization" id="idOrganization" size="50"/>
				<li>技术监督局操作员状态:
				<li><input type="text" name="status" id="status" size="50"/>
				<li><input type="hidden" name="idoperator" id="idoperator" />
				<li><input type="hidden" name="typeOperator" value="00">
			</ul>
		</form>
	</div>

	
</body>
</html>
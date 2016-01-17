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
					//=================检查表单中的字段是否为空 ===================================
					//判断文本框中的值是否为空
				var name1=$("#name1").val();
				if(name1==null||name1==""){
					$("#name2").html("<i>姓名不能为空 !!!</i>");
				}else{
					$("#name2").html("");
					var loginname1=$("#loginname1").val();
				    if(loginname1==null||loginname1==""){
					 $("#loginname2").html("<i>登陆名不能为空 !!!</i>");
					}else{
						$("#loginname2").html("");
						var password1=$("#password1").val();
						if(password1==null||password1==""){
							$("#password2").html("<i>密码不能为空 !!!</i>");
						}else{
							$("#password2").html("");
							var idOrganization1=$("#idOrganization1").val();
						    if(idOrganization1==null||idOrganization1==""){
							  $("#idOrganization2").html("<i>所属单位顺序号不能为空 !!!</i>");
							 }else{
							   $("#idOrganization2").html("");
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
				},
				"关闭":function(){
					$(this).dialog("close");
				}
			},
			close:function(){
				$(this).dialog("close");
			}
		});
		// 查询所有的 操作员类别 (下拉框的值 )
		  $.getJSON("${path }/operator_type_def/list_json.do","rand="+Math.random(),function(d){
			  //将查询到的信息放入修改表单中--注意隐藏域中的主键
			for(var i=0;i<d.length;i++){
				//放入修改 界面的 类别下拉框中（插入界面有默认 插入的值所以不用放 ）
				//alert(d[i].name);
				$("#typeOperator").append("<option size='"+50+"' value='"+d[i].id_operator_type+"'>"+d[i].name+"</option>");
				
			}
		}); 
		//查询维保人员状态（下拉框 的值 ）
			$.getJSON("${path }/status_def/list_json.do","rand="+Math.random(),function(d){
					  //将查询到的信息放入修改表单中--注意隐藏域中的主键
					for(var i=0;i<d.length;i++){
						//alert(d[i].name);
						$("#status").append("<option size='"+50+"' value='"+d[i].idstatus+"'>"+d[i].name+"</option>");
						$("#status1").append("<option size='"+50+"' value='"+d[i].idstatus+"'>"+d[i].name+"</option>");
					}
				}); 
		//查询 操作员角色 （下拉框的值 ）
			$.getJSON("${path }/role/list_json.do","rand="+Math.random(),function(d){
					  //将查询到的信息放入修改表单中--注意隐藏域中的主键
					for(var i=0;i<d.length;i++){
						alert(d[i].name);
						$("#idprivilege").append("<option size='"+50+"' value='"+d[i].idrole+"'>"+d[i].name_role+"</option>");
						$("#idprivilege1").append("<option size='"+50+"' value='"+d[i].idrole+"'>"+d[i].name_role+"</option>");
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
					//=================检查表单中的字段是否为空 ===================================
					//判断文本框中的值是否为空
				var name=$("#name").val();
				if(name==null||name==""){
					$("#name0").html("<i>姓名不能为空 !!!</i>");
				}else{
					$("#name0").html("");
					var loginname=$("#loginname").val();
				    if(loginname==null||loginname==""){
					 $("#loginname0").html("<i>登陆名不能为空 !!!</i>");
					}else{
						$("#loginname0").html("");
						var password=$("#password").val();
						if(password==null||password==""){
							$("#password0").html("<i>密码不能为空 !!!</i>");
						}else{
							$("#password0").html("");
							var idOrganization=$("#idOrganization").val();
						    if(idOrganization==null||idOrganization==""){
							  $("#idOrganization0").html("<i>所属单位顺序号不能为空 !!!</i>");
							 }else{
							   $("#idOrganization0").html("");
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
			$("#typeOperator").val(d.typeOperator);
			$("#name").val(d.name);
			$("#idcard").val(d.idcard);
			$("#idcity").val(d.idcity);
			$("#iddistrict").val(d.iddistrict);
			$("#idsubdistrict").val(d.idsubdistrict);
			$("#loginname").val(d.loginname);
			$("#password").val(d.password);
			$("#idOrganization").val(d.idOrganization);
			$("#idprivilege").val(d.idprivilege);
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
			<th>操作员类别</th>
			<th>操作员姓名</th>
			<th>所属城市</th>
			<th>所属区县</th>
			<th>所属乡镇街道</th>
			<th>角色</th>
			<th>操作员状态</th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${operatorList}" var="d">
			<tr>
				<td>${d.idoperator}</td>
				<td>${d.operator_type_def.name}</td>
				<td>${d.name}</td>
				<td>${d.city.name_city }</td>
				<td>${d.distict.name_district }</td>
				<td>${d.subdistict.name_subdistrict}</td>
				<td>${d.role.name_role}</td>
				<td>${d.status_def.name}</td>
				
				<td><a
					href="javascript:showUpdate(${d.idoperator})">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="${path }/operator/delete.do?idoperator=${d.idoperator}">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="9" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div align="right">
		<input type="button" value="添加一个监督人员"
			onclick="showInsert()" />
	</div>
	
 <div id="insertDialog" style="display: none" title="添加">
		<form action="${path }/operator/insert.do" method="post" id="insertForm">
			<ul>
			    
				<li>操作员姓名:
				<li><input type="text" name="name" id="name1" maxlength="20" size="50"/>*<div id="name2" style="float: right; margin-right:220 " ></div>
				<li>身份证号码:
				<li><input type="text" name="idcard" id="idcard1" maxlength="18" size="50"/>
				<li>所属城市:
				<li><select name="idcity" id="idcity1" onchange="chooseCity(this.value)"></select>
				<li>所属区县:
				<li><select name="iddistrict" id="iddistrict1" onchange="choosedistrict(this.value)"></select> 
				<li>所属乡镇街道:
				<li><select id="idsubdistrict1" name="idsubdistrict"></select>
				<li>登陆名:
				<li><input type="text" name="loginname" id="loginname1" maxlength="20" size="50" />*<div id="loginname2" style="float: right; margin-right:220 " ></div>
				<li>密码:
				<li><input type="text" name="password" id="password1" maxlength="32" size="50"/>*<div id="password2" style="float: right; margin-right:220 " ></div>
				<li>所属单位顺序号:
				<li><input type="text" name="idOrganization" id="idOrganization1" maxlength="11" size="50"/>*<div id="idOrganization2" style="float: right; margin-right:220 " ></div>
				<li>角色:
				<li><select name="idprivilege" id="idprivilege1"></select>
				<li><input type="hidden" name="status" id="status1" value="1"/>
				<li><input type="hidden" name="typeOperator" id="typeOperator1" value="00">
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/operator/update.do" method="post" id="updateForm">
			<ul>
			    <li>操作员类别：
			    <li><select  name="typeOperator" id="typeOperator" ></select>
				<li>操作员姓名:
				<li><input type="text" name="name" id="name" maxlength="20" size="50"/>*<div id="name0" style="float: right; margin-right:220 " ></div>
				<li>身份证号码:
				<li><input type="text" name="idcard" id="idcard" maxlength="18" size="50"/>
				<li>所属城市:
				<li><select name="idcity" id="idcity" onchange="chooseCity(this.value)"></select>
				<li>所属区县:
				<li><select name="iddistrict" id="iddistrict" onchange="choosedistrict(this.value)"></select> 
				<li>所属乡镇街道:
				<li><select id="idsubdistrict" name="idsubdistrict"></select>
				<li>登陆名:
				<li><input type="text" name="loginname" id="loginname" maxlength="20" size="50"/>*<div id="loginname0" style="float: right; margin-right:220 " ></div>
				<li>密码:
				<li><input type="text" name="password" id="password" maxlength="32" size="50"/>*<div id="password0" style="float: right; margin-right:220 " ></div>
				<li>所属单位顺序号:
				<li><input type="text" name="idOrganization" id="idOrganization" maxlength="11" size="50"/>*<div id="idOrganization0" style="float: right; margin-right:220 " ></div>
				<li>角色:
				<li><select name="idprivilege" id="idprivilege"></select>
				<li>技术监督局操作员状态:
				<li><select name="status" id="status"></select>
			    <li><input type="hidden" name="idoperator" id="idoperator" />
			</ul>
		</form>
	</div>

	
</body>
</html>
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
		
		
		//查询 操作员角色 （下拉框的值 ）
			$.getJSON("${path }/role/list_json.do","rand="+Math.random(),function(d){
					  //将查询到的信息放入修改表单中--注意隐藏域中的主键
					for(var i=0;i<d.length;i++){
						//alert("维保人员角色 ："+d[i].name);
						$("#idprivilege").append("<option size='"+50+"' value='"+d[i].idrole+"'>"+d[i].name_role+"</option>");
						$("#idprivilege1").append("<option size='"+50+"' value='"+d[i].idrole+"'>"+d[i].name_role+"</option>");
					}
					
				}); 
			
		$("#updateDialog").dialog({
			modal:true,
			autoOpen:false,
			width:400,
			height:200,
			buttons:{
				"确定":function(){
					var form = $("#updateForm");
					
								//提交表单 
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
	
	
	function fenpei(did){
		
		$.getJSON("${path }/operator/toUpdate.do?idoperator="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			
			$("#idprivilege").val(d.idprivilege);
			$("#idoperator").val(d.idoperator);
			//打开修改对话框
			$("#updateDialog").dialog("open");
			
		});
	}
	
	
	//按照id查询操作人员状态 
	function zhuangtai(did){
		$.getJSON("${path }/operator/toUpdate.do?idoperator="+did,"rand="+Math.random(),function(d){
				var status=d.status;
				var idoperator=d.idoperator;
				if(status=="1"){
					a=confirm("你确定要禁用吗？");
					if(a==true){
						//alert("禁用 ");
						//修改 该操作员的状态 ，把状态改为 0（非正常）
						window.location.href="${path }/operator/UpdateStatus.do?idoperator="+did;
					}
					
				}else{
					a=confirm("你确定要启用吗？");
					if(a==true){
						//alert("启用 ");
						//修改该操作员的状态 ，把状态改为 1（正常）
						window.location.href="${path }/operator/UpdateStatus1.do?idoperator="+did;
					}
				}
			
		});
	}
	
</script>
</head>
<body>
	<form action="${path}/operator/list.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 姓名 <input type="text" name="key" size="50"
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
				
				<td><input type="button" onclick="fenpei(${d.idoperator})" value="重新分配角色">
				<input type="button" onclick="zhuangtai(${d.idoperator})" value="启用/禁用">
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="9" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	
	
 
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/operator/updateRole.do" method="post" id="updateForm">
			<ul>
			    
				<li>角色:
				<li><select name="idprivilege" id="idprivilege"></select>
				
			    <li><input type="hidden" name="idoperator" id="idoperator" />
			</ul>
		</form>
	</div>

	
</body>
</html>
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
			for(var i=0;i<d.length;i++){
				if(d[i].id_city=="00"){
					continue;
				}
			  $("#register_area1").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
			  $("#register_area2").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
			}
		});
		$("#insertDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:450,
			buttons:{
				"确定":function(fn){
					var danweiname=$("#danweiname").val();
					if(danweiname==null||danweiname==""){
						$("#message").html("<i>单位名称不能为空 !!!</i>");
					}else{
						$("#message").html("");
						var form = $("#insertForm");
							$.post(form.attr('action'),form.serialize(),function(a){
								if(a=="ok"){
									location.reload();
								}else{
									alert("程序有点问题哟！");
								}
							});
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
		$("#updateDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:450,
			buttons:{
				"确定":function(){
					var danweiname=$("#name").val();
					if(danweiname==null||danweiname==""){
						$("#message1").html("<i>单位名称不能为空 !!!</i>");
					}else{
						$("#message1").html("");
						var form = $("#updateForm");
						$.post(form.attr('action'),form.serialize(),function(a){
							if(a=="ok"){
								location.reload();
							}else{
								alert("程序有点问题哟！");
							}
					});
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
		$.getJSON("${path }/designer/toUpdate.do?iddesigner="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			$("#code").val(d.code);
			$("#name").val(d.name);
			$("#licence").val(d.licence);
			$("#licename").val(d.licename);
			$("#manager").val(d.manager);
			$("#tel").val(d.tel);
			$("#addr").val(d.addr);
			$("#register_area2").val(d.register_area);
			$("#iddesigner").val(d.iddesigner);
			
			//打开修改对话框
			$("#updateDialog").dialog("open");
		});
	}
</script>
</head>
<body>
	<form action="${path}/designer/list.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 单位名称/代码 <input type="text" name="key" size="50"
					value="${param.key}" /> <input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>编号</th>
			<th>设计单位代码</th>
			<th>单位名称</th>
			<th>设计许可证编号</th>
<!-- 			<th>设计许可证名称</th> -->
			<th>单位负责人</th>
			<th>联系电话</th>
			<th>单位地址</th>
			<th>注册区域</th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${designerList}" var="d">
			<tr>
				<td style="text-align: right">${d.iddesigner}</td>
				<td style="text-align: left">${d.code }</td>
				<td style="text-align: left">${d.name}</td>
				<td style="text-align: left">${d.licence }</td>
<%-- 				<td style="text-align: left">${d.licename }</td> --%>
				<td style="text-align: left">${d.manager }</td>
				<td style="text-align: left">${d.tel }</td>
				<td style="text-align: left">${d.addr }</td>
				<td style="text-align: left">${d.registCity.name_city }</td>
				<td>
				<input onclick="javascript:showUpdate(${d.iddesigner})" type="button" value="&nbsp;&nbsp;&nbsp;&nbsp;修改&nbsp;&nbsp;&nbsp;&nbsp;">
				
<!-- 					&nbsp;&nbsp;&nbsp;&nbsp; -->
<%-- 					<a href="${path }/designer/delete.do?iddesigner=${d.iddesigner}">删除</a> --%>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="10" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div align="right">
		<input type="button" value="添加一个设计单位"
			onclick="showInsert()" />
	</div>
	<div id="insertDialog" style="display: none" title="添加">
		<form action="${path }/designer/insert.do" method="post" id="insertForm">
			<ul>
			
				<li>设计单位代码:
				<li><input type="text" name="code" size="50" maxlength="9"/>
				<li>单位名称:
				<li><input type="text" id="danweiname" name="name" size="50"/>*<div id="message" style="float: right;padding-right:220px;"></div>
				<li>设计许可证编号:
				<li><input type="text" name="licence" size="50"/>
<!-- 				<li>设计许可证名称: -->
<!-- 				<li><input type="text" name="licename" size="50"/> -->
				<li>单位负责人:
				<li><input type="text" name="manager" size="50"/>
				<li>联系电话:
				<li><input type="text" name="tel" size="50"/>
				<li>单位地址:
				<li><input type="text" name="addr" size="50"/>
				<li>注册区域:
				<li><select name="register_area" id="register_area1">
				
				</select>
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/designer/update.do" method="post" id="updateForm">
			<ul>
				<li>设计单位代码:
				<li><input type="text" id="code" name="code" size="50" maxlength="9"/>
				<li>单位名称:
				<li><input type="text" id="name" name="name" size="50"/>*<div id="message1" style="float: right;padding-right:220px;"></div>
				<li>设计许可证编号:
				<li><input type="text" id="licence" name="licence" size="50"/>
<!-- 				<li>设计许可证名称: -->
<!-- 				<li><input type="text" id="licename" name="licename" size="50"/> -->
				<li>单位负责人:
				<li><input type="text" id="manager" name="manager" size="50"/>
				<li>联系电话:
				<li><input type="text" id="tel" name="tel" size="50"/>
				<li>单位地址:
				<li><input type="text" id="addr" name="addr" size="50"/>
				<li>注册区域:
				<li><select name="register_area" id="register_area2">
				  
				</select>
				<li><input type="hidden" id="iddesigner" name="iddesigner"/>
			</ul>
		</form>
	</div>
</body>
</html>
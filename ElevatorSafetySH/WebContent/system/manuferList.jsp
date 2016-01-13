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
		$("#insertDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:450,
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
		$("#updateDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:450,
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
		$.getJSON("${path }/manufer/toUpdate.do?idmanufer="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			$("#code").val(d.code);
			$("#name").val(d.name);
			$("#licence").val(d.licence);
			$("#licename").val(d.licename);
			$("#tel").val(d.tel);
			$("#manager").val(d.manager);
			$("#addr").val(d.addr);
			$("#register_area").val(d.register_area);
			$("#idmanufer").val(d.idmanufer);
			//打开修改对话框
			$("#updateDialog").dialog("open");
		});
	}
</script>
</head>
<body>
	<form action="${path}/manufer/list.do" method="post">
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
			<th>制造单位代码</th>
			<th>制造单位名称</th>
			<th>制造许可证编号</th>
			<th>制造许可证</th>
			<th>制造负责人</th>
			<th>联系电话</th>
			<th>制造单位地址</th>
			<th>注册区域</th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${manuferList}" var="d">
			<tr>
				<td>${d.idmanufer}</td>
				<td>${d.code }</td>
				<td>${d.name}</td>
				<td>${d.licence }</td>
				<td>${d.licename }</td>
				<td>${d.manager }</td>
				<td>${d.tel }</td>
				<td>${d.addr }</td>
				<td>${d.register_area }</td>
				<td><a
					href="javascript:showUpdate(${d.idmanufer})">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="${path }/manufer/delete.do?idmanufer=${d.idmanufer}">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="10" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div align="right">
		<input type="button" value="添加一个制造单位"
			onclick="showInsert()"/>
	</div>
<div id="insertDialog" style="display: none" title="添加">
		<form action="${path }/manufer/insert.do" method="post" id="insertForm">
			<ul>
				<li>制造单位代码:
				<li><input type="text" name="code" size="50"/>
				<li>单位名称:
				<li><input type="text" name="name" size="50"/>
				<li>制造许可证编号:
				<li><input type="text" name="licence" size="50"/>
				<li>制造许可证:
				<li><input type="text" name="licename" size="50"/>
				<li>制造负责人:
				<li><input type="text" name="manager" size="50"/>
				<li>联系电话:
				<li><input type="text" name="tel" size="50"/>
				<li>单位地址:
				<li><input type="text" name="addr" size="100"/>
				<li>注册区域:
				<li><input type="text" name="register_area" size="100"/>
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/manufer/update.do" method="post" id="updateForm">
			<ul>
				<li>制造单位代码:
				<li><input type="text" id="code" name="code" size="50"/>
				<li>单位名称:
				<li><input type="text" id="name" name="name" size="50"/>
				<li>制造许可证编号:
				<li><input type="text" id="licence" name="licence" size="50"/>
				<li>制造许可证:
				<li><input type="text" id="licename" name="licename" size="50"/>
				<li>制造负责人:
				<li><input type="text" id="manager" name="manager" size="50"/>
				<li>联系电话:
				<li><input type="text" id="tel" name="tel" size="50"/>
				<li>单位地址:
				<li><input type="text" id="addr" name="addr" size="100"/>
				<li>注册区域:
				<li><input type="text" id="register_area" name="register_area" size="100"/>
				<li><input type="hidden" id="idmanufer" name="idmanufer"/>
			</ul>
		</form>
	</div>
</body>
</html>
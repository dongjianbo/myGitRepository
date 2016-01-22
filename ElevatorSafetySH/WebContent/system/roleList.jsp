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
	$().ready(function(){
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
		$("#insertDialog").dialog("open");
	}
	function showUpdate(did){
		$("#updateDialog input[name='menus_id']").each(function(){
			$(this).removeAttr("checked");
		});
		$.getJSON("${path }/role/toUpdate.do?idrole="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			$("#name_role").val(d.name_role);
			$("#desc").val(d.desc);
			$("#idrole").val(d.idrole);
			$("#updateDialog input[name='menus_id']").each(function(){
				var b=false;
				var m=$(this).val();
				$(d.menus).each(function(){
					if(this["id_system_menu"]==m){
						b=true;
						return;
					}
				});
				if(b){
					alert($(this).val());
					$(this).attr("checked","checked");
				}
			});
			//打开修改对话框
			$("#updateDialog").dialog("open");
			
		});
	}
</script>
</head>
<body>
	<ul>
		<li><h3>角色定义</h3>
		<li><table id="list" cellpadding="0" cellspacing="1">
					<tr>
						<th align="center">角色编号</th>
						<th align="center">角色名称</th>
						<th align="center">角色描述</th>
						<th align="center">当前状态</th>
						<th align="center">操作</th>
					</tr>
					<c:forEach items="${roleList }" var="r">
						<tr>
							<td style="text-align: right">${r.idrole }</td>
							<td style="text-align: left">${r.name_role }</td>
							<td style="text-align: left">${r.desc }</td>
							<td style="text-align: left">${r.role_status eq '1'?'启用':'禁用'}</td>
							<td><a
								href="javascript:showUpdate(${r.idrole })">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="${path }/role/delete.do?idrole=${r.idrole}">删除</a>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="8" style="text-align: left;">${pagination}</td>
					</tr>
				</table>
				<div align="right">
					<input type="button" value="自定义一个角色"
						onclick="showInsert()" />
				</div>
		</li>
		<li><h3><a href="${path}/operator/list1.do">为操作员分配角色</a></h3>
	</ul>
	<div id="insertDialog" style="display: none" title="添加">
		<form action="${path }/role/insert.do" method="post" id="insertForm">
			<ul>
				<li>请填入角色名称:
				<li><input type="text" name="name_role" size="30"/>
				<li>角色描述:
				<li><input type="text" name="desc" size="50"/>
				<li>为这个角色定义相应的权限（在拥有的权限前打勾）:
				<li><ul>
					<c:forEach items="${menuList}" var="m">
						<li><input type="checkbox" name="menus_id" value="${m.id_system_menu}">${m.name_item}&nbsp;&nbsp;(${m.item_desc})
					</c:forEach>
				</ul>
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/role/update.do" method="post" id="updateForm">
			<ul>
				<li>请填入角色名称:
				<li><input type="text" name="name_role" id="name_role" size="30"/>
				<li>角色描述:
				<li><input type="text" name="desc" id="desc" size="50"/>
				<li>为这个角色定义相应的权限（在拥有的权限前打勾）:
				<li><ul>
					<c:forEach items="${menuList}" var="m">
						<li><input type="checkbox" name="menus_id" value="${m.id_system_menu}">${m.name_item}&nbsp;&nbsp;(${m.item_desc})
					</c:forEach>
				</ul>
				<input type="hidden" name="idrole" id="idrole"/>
			</ul>
		</form>
	</div>
	
</body>
</html>
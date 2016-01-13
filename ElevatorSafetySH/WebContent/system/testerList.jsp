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
		// 查询所有 所属检验检测机构 (下拉框的值 )
		  $.getJSON("${path }/test/list_json.do","rand="+Math.random(),function(d){
			  //将查询到的信息放入修改表单中--注意隐藏域中的主键
			for(var i=0;i<d.length;i++){
				$("#idtest").append("<option size='"+50+"' value='"+d[i].idtest+"'>"+d[i].name+"</option>");
				$("#idtest1").append("<option size='"+50+"' value='"+d[i].idtest+"'>"+d[i].name+"</option>");
			}
		}); 
		//查询检验检测人员状态（下拉框 的值 ）
			$.getJSON("${path }/status_def/list_json.do","rand="+Math.random(),function(d){
					  //将查询到的信息放入修改表单中--注意隐藏域中的主键
					for(var i=0;i<d.length;i++){
						$("#status").append("<option size='"+50+"' value='"+d[i].idstatus+"'>"+d[i].name+"</option>");
						$("#status1").append("<option size='"+50+"' value='"+d[i].idstatus+"'>"+d[i].name+"</option>");
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
		
		$.getJSON("${path }/tester/toUpdate.do?idtester="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			
			$("#name").val(d.name);
			$("#idcard").val(d.idcard);
			$("#idtest").val(d.idtest);
			$("#licencecode").val(d.licencecode);
			$("#status").val(d.status);
			$("#idMifare").val(d.idMifare);
			$("#idtester").val(d.idtester);
			//打开修改对话框
			$("#updateDialog").dialog("open");
			
		});
	}
</script>

</head>
<body>
	<form action="${path}/tester/list.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 姓名/编码 <input type="text" name="key" size="50"
					value="${param.key}" /> <input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>检验检测人员顺序号</th>
			<th>检验检测人员姓名</th>
			<th>身份证号码</th>
			<th>所属检验检测机构</th>
			<th>从业资格证书编号</th>
			<th>检验检测人员状态 </th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${testerList}" var="d">
			<tr>
				<td>${d.idtester}</td>
				<td>${d.name }</td>
				<td>${d.idcard}</td>
				<td>${d.idtest}</td>
				<td>${d.licencecode}</td>
				<td>${d.status }</td>
				<td><a
					href="javascript:showUpdate(${d.idtester})">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="${path }/tester/delete.do?idtester=${d.idtester}">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div align="right">
		<input type="button" value="添加一个检验检测人员"
			onclick="showInsert()" />
	</div>
   
<div id="insertDialog" style="display: none" title="添加">
		<form action="${path }/tester/insert.do" method="post" id="insertForm">
			<ul>
				<li>检验检测人员姓名:
				<li><input type="text" name="name" size="50"/>
				<li>身份证号码:
				<li><input type="text" name="idcard" size="50"/>
				<li>所属检验检测机构:
				<li><select name="idtest" id="idtest1"></select>
				<li>从业资格证书编号:
				<li><input type="text" name="licencecode" size="50"/>
				<li>检验检测人员状态:
				<li><select name="status" id="status1"></select>
				<li>上岗卡标识号:
				<li><input type="text" name="idMifare" size="50"/>
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/tester/update.do" method="post" id="updateForm">
			<ul>
				<li>检验检测人员姓名:
				<li><input type="text" name="name" id="name" size="50"/>
				<li>身份证号码:
				<li><input type="text" name="idcard" id="idcard" size="50"/>
				<li>所属检验检测机构:
				<li><select name="idtest" id="idtest"></select>
				<li>从业资格证书编号:
				<li><input type="text" name="licencecode" id="licencecode" size="50"/>
				<li>检验检测人员状态:
				<li><select name="status" id="status"></select>
				<li>上岗卡标识号:
				<li><input type="text" name="idMifare" id="idMifare" size="50"/>
				<li><input type="hidden" name="idtester" id="idtester" />
			</ul>
		</form>
	</div>



</body>
</html>
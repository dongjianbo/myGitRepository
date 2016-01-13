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
		// 查询所有的 维保公司的名字 (下拉框的值 )
		  $.getJSON("${path }/service/list_json.do","rand="+Math.random(),function(d){
			  //将查询到的信息放入修改表单中--注意隐藏域中的主键
			for(var i=0;i<d.length;i++){
				$("#idservice").append("<option size='"+50+"' value='"+d[i].idservice+"'>"+d[i].name+"</option>");
				$("#idservice1").append("<option size='"+50+"' value='"+d[i].idservice+"'>"+d[i].name+"</option>");
			}
		}); 
		//查询维保人员类别（下拉框的值 ）
			$.getJSON("${path }/servicer_type_def/list_json.do","rand="+Math.random(),function(d){
				  //将查询到的信息放入修改表单中--注意隐藏域中的主键
				for(var i=0;i<d.length;i++){
					$("#type").append("<option size='"+50+"' value='"+d[i].idservicertype+"'>"+d[i].name+"</option>");
					$("#type1").append("<option size='"+50+"' value='"+d[i].idservicertype+"'>"+d[i].name+"</option>");
				}
			}); 
		//查询维保人员状态（下拉框 的值 ）
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
		//查询维保人员类别（下拉框的值 ）
		
	 $.getJSON("${path }/servicer/toUpdate.do?idservicer="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			$("#name").val(d.name);
			$("#idcard").val(d.idcard);
			$("#idservice").val(d.idservice);
			$("#licencecode").val(d.licencecode);
			$("#type").val(d.type);
			$("#idMifare").val(d.idMifare);
			$("#status").val(d.status);
			$("#idservicer").val(d.idservicer);
			//打开修改对话框
			$("#updateDialog").dialog("open");
			
		});
		
	}
	function showSelectValue(){
		
	}
</script>

</head>

<body>
	<form action="${path}/servicer/list.do" method="post">
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
			<th>编号</th>
			<th>维保人员姓名</th>
			<th>身份证号码</th>
			<th>所属维保公司</th>
			<th>从业资格证书编号</th>
			<th>维保人员类别</th>
			<th>维保人员状态</th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${servicerList}" var="d">
			<tr>
				<td>${d.idservicer}</td>
				<td>${d.name }</td>
				<td>${d.idcard}</td>
				<td>${d.idservice}</td>
				<td>${d.licencecode}</td>
				<td>${d.type }</td>
				<td>${d.status }</td>
				<td><a
				       href="javascript:showUpdate(${d.idservicer})">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="${path }/servicer/delete.do?idservicer=${d.idservicer}">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="8" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div align="right">
		<input type="button" value="添加一个维保人员"
		onclick="showInsert()" />
	</div>

 <div id="insertDialog" style="display: none" title="添加">
		<form action="${path }/servicer/insert.do" method="post" id="insertForm">
			<ul>
				<li>维保人员姓名:
				<li><input type="text" name="name" size="50"/>
				<li>身份证号码:
				<li><input type="text" name="idcard" size="50"/>
				<li>所属维保公司:
				<li><select name="idservice" id="idservice1"></select>
				<li>从业资格证书编号:
				<li><input type="text" name="licencecode" size="50"/>
				<li>维保人员类别:
				<li><select name="type" id="type1" ></select>
				<li>上岗卡标识号:
				<li><input type="text" name="idMifare" size="50"/>
				<li>维保人员状态:
				<li><select name="status" id="status1"></select>
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/servicer/update.do" method="post" id="updateForm">
			<ul>
				<li>维保人员姓名:
				<li><input type="text" name="name" id="name" size="50"/>
				<li>身份证号码:
				<li><input type="text" name="idcard" id="idcard" size="50"/>
				<li>所属维保公司:
				<li><select name="idservice" id="idservice">
				</select>
				<li>从业资格证书编号:
				<li><input type="text" name="licencecode" id="licencecode" size="50"/>
				<li>维保人员类别:
				<li><select name="type" id="type"></select>
				<li>上岗卡标识号:
				<li><input type="text" name="idMifare" id="idMifare" size="50"/>
				<li>维保人员状态:
				<li><select name="status" id="status"></select>
				<li><input type="hidden" name="idservicer" id="idservicer" />
			</ul>
		</form>
	</div>
</body>
</html>
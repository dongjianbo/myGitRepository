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
	<script src="${path}/jquery/ui/jquery.ui.datepicker.js"></script><!-- 日期控件的js -->
	<script type="text/javascript">
	$().ready(function(){
		
		 $("#date_enable1").datepicker({dateFormat:'yy-mm-dd'});//日期控件 
		 $.getJSON("${path}/service/list_json.do","rand="+Math.random(),function(d){
	    	  //对维保单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_service").append("<option size='"+15+"' value='"+d[i].idservice+"'>"+d[i].name+"</option>");
	    	  }
	       });
		$.getJSON("${path}/test/list_json.do","rand="+Math.random(),function(d){
		    	  //对检验检测单位进行循环
		    	  for(var i=0;i<d.length;i++){
		    		  $("#id_test").append("<option size='"+15+"' value='"+d[i].idtest+"'>"+d[i].name+"</option>");
		    	  }
		       });
		$("#regist").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:500,
			buttons:{
				"确定":function(){
					edate=$("#date_enable1").val();
					if(edate==null||edate==""){
						$("#date_enable_error").html("必须选择使用时间");
					}else{
						$("#date_enable_error").html("");
						var form=$("#updateForm");
						$.post(form.attr('action'),form.serialize(),function(a){
							if(a=="ok"){
								location.reload();
							}else{
								alert("程序有点问题哟！");
							}
						});
					}
				}
			},
			close:function(){
				$(this).dialog("close");
			}
		});
	});
	function zhuc(){
		var start=$("#date_enable").val();
		if(start==null||start==""){
			
		}else{
			myform.submit();
		}
	}
	function toRegist(id_elevator){
		$("#id_elevator").val(id_elevator);
		$("#regist").dialog("open");
	}
</script>
</head>
<body>
	<form action="${path}/elevator/list1.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 电梯位置 <input type="text" name="key" size="50"
					value="${param.key}" /> <input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>电梯编号</th>
			<th>电梯型号</th>
			<th>出厂编号</th>
			<th>设计单位</th>
			<th>生产单位</th>
			<th>电梯所在位置</th>
			<th>安装项目负责人</th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${elevListRegist}" var="d">
			<tr>
				<td style="text-align: right">${d.id_elevator}</td>
				<td style="text-align: left">${d.model.modelname }</td>
				<td style="text-align: left">${d.code_manufer}</td>
				<td style="text-align: left">${d.designer.name }</td>
				<td style="text-align: left">${d.manufer.name }</td>
				<td style="text-align: left">${d.address }</td>
				<td style="text-align: left">${d.project_duty }</td>
				<td>
				<a href="javascript:toRegist(${d.id_elevator})">&nbsp;&nbsp;&nbsp;&nbsp;注册&nbsp;&nbsp;&nbsp;&nbsp;</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="8" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div title="电梯注册" id="regist">
	<form action="${path }/elevator/regist.do" method="post" id="updateForm" name="myform">
			<ul>
				<li>维保单位名称:
				<li><select name="id_service" id="id_service"></select><br>
				<li>检验检测单位名称:
				<li><select name="id_test" id="id_test"></select><br>
				<li>验收报告编号:
				<li><input type="text" id="check_construct_code" name="check_construct_code" size="50"/><br>
				<li>开始使用时间:
				<li><input type="text" id="date_enable1" name="date_enable" size="30" placeholder="请选择开始使用时间"/>*
				<span id="date_enable_error"></span>
				<br>
				
				<li><input type="hidden" id="id_elevator" name="id_elevator" value=""/>
			</ul>
		</form>
	</div>
</body>
</html>
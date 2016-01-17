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
	var dia;
	$().ready(function(){
		
		 $("#date_enable").datepicker({dateFormat:'yy-mm-dd'});//日期控件 
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
	});
	function zhuc(){
		var start=$("#date_enable").val();
		if(start==null||start==""){
			
		}else{
			myform.submit();
		}
	}
</script>
</head>
<body>
	<form action="${path }/elevator/regist.do" method="post" id="updateForm" name="myform">
			<ul>
				<li>维保单位名称:
				<li><select name="id_service" id="id_service"></select><br>
				<li>检验检测单位名称:
				<li><select name="id_test" id="id_test"></select><br>
				<li>验收报告编号:
				<li><input type="text" id="check_construct_code" name="check_construct_code" size="50"/><br>
				<li>开始使用时间:
				<li><input type="text" id="date_enable" name="date_enable" size="30" placeholder="请选择开始使用时间"/>*<br>
				<li><input type="button" value="注册" onclick="zhuc()"/>
				<li><input type="hidden" id="id_elevator" name="id_elevator" value="<%=request.getParameter("id_elevator") %>"/>
			</ul>
		</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
	
	
	function mima(){
		
		
		//var pwd=$("#password").val();
		//$.getJSON("${path }/operator/selectById.do?password="+pwd+"","rand="+Math.random(),function(d){
			//alert(d);
	  //查询操作员密码 
	   	var pwd1=$("#password1").val();
				if(pwd1==null||pwd1==""){
					$("#password10").html("<i>密码不能为空!!!</i>");
				}else{
					$("#password10").html("");
					var pwd2=$("#password2").val();
					if(pwd2==null||pwd2==""){
						$("#password20").html("<i>密码不能为空!!!</i>");
					}else{
						
						if(pwd1!=pwd2){
							$("#password20").html("两次密码输入不一致！！");
							}else{
								updateForm.submit();
							}
						}
				}
		//});

	</script>
</head>
<body>
<form action="${path }/operator/updatePassword.do" method="post" name="updateForm">
			<ul>
			   
				<li>输入原来密码：
				<li><input type="text" name="password1"  id="password"  maxlength="32" size="50"/>*<div id="password0" style="float:right; margin-right:700px; " >${result}</div>
				<li>输入新密码：
				<li><input type="text" name="password2" id="password1" maxlength="32" size="50"/>*<div id="password10" style="float: right; margin-right:700px; " ></div>
				<li>再次输入新密码：
				<li><input type="text" name="password" id="password2"  maxlength="32" size="50"/>*<div id="password20" style="float: right; margin-right:700px; " ></div>
			    <li><input type="button" value="确定" onclick="mima()" maxlength="32" size="50"/>
			</ul>
		</form>
</body>
</html>
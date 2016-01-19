<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.servletContext.contextPath}/css/system.css" rel="stylesheet" type="text/css">
<link href="${pageContext.servletContext.contextPath}/css/table.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/jquery/themes/base/jquery.ui.all.css">
	<script src="${pageContext.servletContext.contextPath}/jquery/jquery-1.10.2.js"></script>
	<script src="${pageContext.servletContext.contextPath}/jquery/ui/jquery.ui.core.js"></script>
	<script src="${pageContext.servletContext.contextPath}/jquery/ui/jquery.ui.widget.js"></script>
	<script src="${pageContext.servletContext.contextPath}/jquery/ui/jquery.ui.mouse.js"></script>
	<script src="${pageContext.servletContext.contextPath}/jquery/ui/jquery.ui.button.js"></script>
	<script src="${pageContext.servletContext.contextPath}/jquery/ui/jquery.ui.draggable.js"></script>
	<script src="${pageContext.servletContext.contextPath}/jquery/ui/jquery.ui.position.js"></script>
	<script src="${pageContext.servletContext.contextPath}/jquery/ui/jquery.ui.resizable.js"></script>
	<script src="${pageContext.servletContext.contextPath}/jquery/ui/jquery.ui.button.js"></script>
	<script src="${pageContext.servletContext.contextPath}/jquery/ui/jquery.ui.dialog.js"></script>
	<script src="${pageContext.servletContext.contextPath}/jquery/ui/jquery.ui.effect.js"></script>
	<script type="text/javascript">
	$().ready(function(){
		var $doc;         
		if(window.location!=window.top.location){          
			// <SPAN style="COLOR: #ff0000">页面在iframe中</SPAN>            
			$doc=window.top.jQuery.noConflict();
		}else{
			// <SPAN style="COLOR: #ff0000">页面单独打开</SPAN>
			$doc=jQuery.noConflict();
		}         
		$doc("body").append('<div id="dialog"  style="display: none" title="未检出登录信息"><h3>登录信息已失效，请重新登录！</3></div>');
		$doc("#dialog").dialog({
			modal:true,
			autoOpen:false,
			width:450,
			height:350,
			buttons:{
				"确定":function(fn){
					window.top.location="${pageContext.servletContext.contextPath}/login.jsp";
				}
			}	
		});
		
		$doc("#dialog").dialog("open");
	});
	</script>

</head>
<body bgcolor="#accccc">

	
	
</body>
</html>
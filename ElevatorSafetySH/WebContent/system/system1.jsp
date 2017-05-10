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
<style type="text/css">
ul {
	margin-top: 20px;
}

ul li {
	float: left;
	margin-right: 40px;
}
</style>
<script type="text/javascript" src="${path}/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript">
	function toManage(v,s){
		if(v==1){
			$('#_sFrame').attr("src","${path}/designer/list1.do");
		}
		if(v==2){
			$('#_sFrame').attr("src","${path}/manufer/list1.do");
		}
		if(v==3){
			$('#_sFrame').attr("src","${path}/installer/list1.do");
		}
		if(v==4){
			$('#_sFrame').attr("src","${path}/test/list1.do");
		}
		if(v==5){
			$('#_sFrame').attr("src","${path}/service/list1.do");
		}
		if(v==6){
			$('#_sFrame').attr("src","${path}/owner/list1.do");
		}
		if(v==7){
			$('#_sFrame').attr("src","${path}/user/list1.do");
		}
		$('span.title').each(function(index){
			if(s==this){
				this.style.backgroundColor='#accccc';
			}else{
				this.style.backgroundColor='#ffffff';
			}
		});
	}
</script>
<style type="text/css">
	.title{
		border: 1px solid #accccc;
		cursor: pointer;
		padding: 5px;
	}
</style>
</head>
<body>
	<div style="height: 30px; vertical-align: center;" align="center">
		<ul>
			<li><span class="title" style="background-color: #accccc;" onclick="toManage(1,this)">设计单位查询</span>
			<li><span class="title" onclick="toManage(2,this)">制造单位查询</span>
			<li><span class="title" onclick="toManage(3,this)">安装单位查询</span>
			<li><span class="title" onclick="toManage(4,this)">检验检测单位查询</span>
			<li><span class="title" onclick="toManage(5,this)">维保单位查询</span>
			<li><span class="title" onclick="toManage(6,this)">产权单位查询</span>
			<li><span class="title" onclick="toManage(7,this)">使用单位查询</span>
		</ul>
	</div>
	<iframe id="_sFrame" width="100%" height="100%" frameborder="0" scrolling="no"
		marginheight="0" marginwidth="0" name="_sFrame"
		src="${path}/designer/list1.do"></iframe>

</body>
</html>
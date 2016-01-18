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
			$('#_sFrame').attr("src","${path}/servicer/list.do");
		}
		if(v==2){
			$('#_sFrame').attr("src","${path}/safer/list.do");
		}
		if(v==3){
			$('#_sFrame').attr("src","${path}/tester/list.do");
		}
		if(v==4){
			$('#_sFrame').attr("src","${path}/role/list.do");
		}
		if(v==5){
			$('#_sFrame').attr("src","${path}/operator/list.do");
		} 
		if(v==6){
			$('#_sFrame').attr("src","${path}/modellist/list.do");
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
			<li><span class="title" style="background-color: #accccc;" onclick="toManage(1,this)">维保人员维护</span>
			<li><span class="title" onclick="toManage(2,this)">安全人员维护</span>
			<li><span class="title" onclick="toManage(3,this)">检验检测人员维护</span>
			<li><span class="title" onclick="toManage(4,this)">新增操作人员角色</span>
			<li><span class="title" onclick="toManage(5,this)">监管部门人员维护</span>
			<li><span class="title" onclick="toManage(6,this)">电梯型号维护</span>
			
		</ul>
	</div>
	<iframe id="_sFrame" width="100%" height="100%" frameborder="0" scrolling="no"
		marginheight="0" marginwidth="0" name="_sFrame"
		src="${path}/servicer/list.do"></iframe>

</body>
</html>
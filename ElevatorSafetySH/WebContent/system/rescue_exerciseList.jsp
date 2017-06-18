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
			$("#reDetail").dialog({
				modal:true,
				autoOpen:false,
				width:1050,
				height:600,
				buttons:{
					"确定":function(){
						$(this).dialog("close");
					}
				},
				close:function(){
					$(this).dialog("close");
				}
			});
			$("#reDetail2").dialog({
				modal:true,
				autoOpen:false,
				width:1050,
				height:600,
				buttons:{
					"确定":function(){
						$(this).dialog("close");
					}
				},
				close:function(){
					$(this).dialog("close");
				}
			});
		});
		function toDetail(rid,note,image,image2,type){
			var title='';
			if(type==1){
				title='救援演习';
			}else{
				title='应急维修';
			}
			$.getJSON("http://longwan.shifting.com.cn/pda_api.php?name=re_info&rid="+rid,"",function(v){
				if(v[0].error=="OK"){
					var h="<ul>";
					h+="<li>"+title+"描述：<div style='width:950px;display:block;word-break: break-all;word-wrap: break-word;'>"+note+"<div></li>";
					h+="<hr>";
					h+="<li>"+title+"照片："+image+"</li>";
					h+="<hr>";
					/* h+="<li>已上传图像个数："+image+"</li>"; */
					if(v[0].tag.length>0){
						h+="<li>";
					}
					for(i=0;i<v[0].tag.length;i++){
						h+="<img alt='图片不存在！' width=200 height=200 src='http://longwan.shifting.com.cn/pda_api.php?name=re_info&rid="+rid+"&image="+v[0].tag[i]+"&zoom=8'/>";
					};	
					h+="</ul>";
					if(type==1){
						$("#reDetail").html(h);
						$("#reDetail").dialog("open");
					}else{
						$("#reDetail2").html(h);
						$("#reDetail2").dialog("open");
					}
					
				}
			});
			
			
		}
		
		
		
		
	</script>
</head>
<body>

	<h3>急修演习列表</h3>
	<table cellpadding="0" cellspacing="1">
		<tr>
		 	<th>记录编号</th>
			<th>电梯简称</th>
			<th>工作类型</th>
			<th>维保人员</th>
			<th>维保人员</th>
			<th>安全人员</th>
			<th>报救时间</th>
			<th>到达时间</th>
			<th>完成时间</th>
			<th>操作</th>
		</tr>
		<c:if test="">
			
		</c:if>
		<c:choose>
			<c:when test="${empty reList}">
				<tr>
					<td colspan="10" align="center">暂无记录！</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${reList }" var="re">
			<tr>
				<td>${re.rid}</td>
				<td>${re.elevator.desc}</td>
				<td>${re.type==1?"救援演习":"应急维修"}</td>
				<td>${re.servicer1.name}</td>
				<td>${re.servicer2.name}</td>
				<td>${re.safer.name }</td>
				<td>${re.arrived }</td>
				<td>${re.work_begin }</td>
				<td>${re.work_end }</td>
				<td><a href="javascript:toDetail(${re.rid},'${re.note}',${re.image},${re.image2},${re.type})">详细信息</a></td>
				
			</tr>
		</c:forEach>
			<tr>
				<td colspan="10" style="text-align: left;">${pagination}</td>
			</tr>
			</c:otherwise>
		</c:choose>
		
		
	</table>
	<div align="right">
		<button onclick="history.back();">返回上一页</button>
	</div>
	<div title="救援演习详细内容" id="reDetail"></div>
	<div title="应急维修详细内容" id="reDetail2"></div>
</body>
</html>
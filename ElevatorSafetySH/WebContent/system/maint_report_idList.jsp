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
			$("#showDetail").dialog({
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
		function toDetail(maint_id){
			$.getJSON("${path}/maint_item_def/listById.do?rand="+Math.random()+"&maint_id="+maint_id,"",function(midlist){
				$("#table1 tr:not(:first)").remove();
				for(i=0;i<midlist.length;i++){
					var tr1="<tr onmouseover='toImage("+maint_id+","+midlist[i].maint_item_id+","+i+")' onmouseout='outImage("+i+")'>"
					tr1+="<td>"+midlist[i].t5001_no+"</td>";
					tr1+="<td>"+midlist[i].elType.name+"</td>";
					tr1+="<td>"+midlist[i].mType.name+"</td>";
					tr1+="<td>"+midlist[i].mArea.name+"</td>";
					tr1+="<td>"+midlist[i].title+"</td>";
					tr1+="<td>"+midlist[i].content+"</td>";
					tr1+="<td>"+midlist[i].info+"</td>";
					tr1+="</tr>";
					tr1+="<tr id='tr"+i+"' style='display:none'><td id='td"+i+"' align='left' colspan='7'></td></tr>"
					$("#table1").append(tr1);
				}
				$("#showDetail").dialog("open");
			});
		}
		function toImage(maint_id,maint_item_id,ii){
			
			$.getJSON("${path}/maint_item_def/getDetail.do?maint_id="+maint_id+"&maint_item_id="+maint_item_id,"",function(md){
				$("#td"+ii).html("");
			
				if((md[0]!=null&&md[0]!="")||md[1]>0){
					$("#tr"+ii).show();
					$("#td"+ii).append("<h3 align='left'>备注："+decodeURI(md[0])+"</h3>");
				}
				if(md[1]>0){
					$("#td"+ii).append("<h3 align='left'>图样：("+md[1]+"p)");
					for(i=0;i<md[1];i++){
						$("#td"+ii).append("<img src='${path}/maint_item_def/getImage.do?maint_id="+maint_id+"&maint_item_id="+maint_item_id+"&image_val="+i+"' width=200 height=200/>&nbsp;&nbsp;&nbsp;");
					}
				}
				
				
			});
		}
		function outImage(i){
			$("#tr"+i).hide();
		}
	</script>
</head>
<body>

<ul>
	
	<li><h3>${typeName }&nbsp;&nbsp;记录列表</h3>
	<li>
	<table cellpadding="0" cellspacing="1">
	<tr>
		<th>记录编号</th>
		<th>电梯简称</th>
		<th>操作员1</th>
		<th>操作员2</th>
		<th>操作员3</th>
		<th>维保时间</th>
		<th>记录上传时间</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${list }" var="l">
		<tr>
			<td>${l.maint_id }</td>
			<td>${l.elevator.desc }</td>
			<td>${l.servicer1.name }</td>
			<td>${l.servicer2.name }</td>
			<td>${l.servicer3.name }</td>
			<td>${l.maint_date }</td>
			<td>${l.maint_upload }</td>
			<td><a href="javascript:toDetail(${l.maint_id })">查看记录明细</a></td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="8" style="text-align: left;">${pagination}</td>
	</tr>
	</table>
	<div align="right">
		<button onclick="history.back();">返回上一页</button>
	</div>
	
</ul>
<div id="showDetail">
	<table cellpadding="0" cellspacing="1" width="90%" id="table1">
		<tr>
			<th>t5001编号</th>
			<th>电梯类型</th>
			<th>检测类型</th>
			<th>检测区域</th>
			<th>检测项</th>
			<th>检测结果</th>
			<th>备注</th>
		</tr>
	</table>
	
				
			
</div>

</body>
</html>
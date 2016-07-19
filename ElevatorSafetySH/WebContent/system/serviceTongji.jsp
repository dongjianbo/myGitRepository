<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>serviceTongji.jsp</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link href="${path}/css/table.css" rel="stylesheet" type="text/css">
<style type="text/css">
a{
	text-decoration: underline;
}
</style>
<script type="text/javascript" src="${path }/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript">
	
	$().ready(function(){
		
		
	});
</script>
</head>
<body>
<ul>
	<li><h3>电梯数量</h3>
	<li><table cellpadding="0" cellspacing="1">
	<tr>
		<th>电梯总数量</th>
		<th>已注册数量</th>
		<th>待注册数量</th>
		<th>已停用数量</th>
		<th>已注销数量</th>
	</tr>
	<tr>
		<td><a href="${path }/service/listForSearch.do?key=count">${count }</a></td>
		<td><a href="${path }/service/listForSearch.do?key=count_registed">${count_registed}</a></td>
		<td><a href="${path }/service/listForSearch.do?key=count_noregist">${count_noregist }</a></td>
		<td><a href="${path }/service/listForSearch.do?key=count_stop">${count_stop }</a></td>
		<td><a href="${path }/service/listForSearch.do?key=count_destory">${count_destory}</a></td>
	</tr>
	</table>
	<li><h3>正常运行电梯统计</h3>
	<li><table cellpadding="0" cellspacing="1">
		<tr>
			<th>&nbsp;</th>
			<th>正常数量</th>
			<th>提示数量</th>
			<th>逾期数量</th>
		</tr>
		<tr>
			<td>年检</td>
			<td><a
				<c:if test="${count_rounds_normal!=0 }">
					style="color:green"
				</c:if>	
			
			 href="${path }/service/listForSearch.do?key=count_rounds_normal">${count_rounds_normal }</a></td>
			<td><a
				<c:if test="${count_rounds_warnning!=0 }">
					style="color:#00FFFF"
				</c:if>
			 href="${path }/service/listForSearch.do?key=count_rounds_warnning">${count_rounds_warnning }</a></td>
			<td><a 
				<c:if test="${count_rounds_overdue!=0 }">
					style="color:red"
				</c:if>
			href="${path }/service/listForSearch.do?key=count_rounds_overdue">${count_rounds_overdue }</a></td>
		</tr>
		<tr>
			<td>半月维保</td>
			<td><a
				<c:if test="${count_15service_normal!=0 }">
					style="color:green"
				</c:if>	
			 href="${path }/service/listForSearch.do?key=count_15service_normal">${count_15service_normal }</a></td>
			<td><a 
				<c:if test="${count_15service_warnning!=0 }">
					style="color:blue"
				</c:if>
			href="${path }/service/listForSearch.do?key=count_15service_warnning">${count_15service_warnning }</a></td>
			<td><a 
				<c:if test="${count_15service_overdue!=0 }">
					style="color:red"
				</c:if>
			href="${path }/service/listForSearch.do?key=count_15service_overdue">${count_15service_overdue }</a></td>
		</tr>
		<tr>
			<td>季度维保</td>
			<td><a 
				<c:if test="${count_90service_normal!=0 }">
					style="color:green"
				</c:if>	
			href="${path }/service/listForSearch.do?key=count_90service_normal">${count_90service_normal }</a></td>
			<td><a 
				<c:if test="${count_90service_warnning!=0 }">
					style="color::#00FFFF"
				</c:if>
			href="${path }/service/listForSearch.do?key=count_90service_warnning">${count_90service_warnning }</a></td>
			<td><a 
				<c:if test="${count_90service_overdue!=0 }">
					style="color:red"
				</c:if>
			href="${path }/service/listForSearch.do?key=count_90service_overdue">${count_90service_overdue }</a></td>
		</tr>
		<tr>
			<td>半年维保</td>
			<td><a 
				<c:if test="${count_180service_normal!=0 }">
					style="color:green"
				</c:if>	
			href="${path }/service/listForSearch.do?key=count_180service_normal">${count_180service_normal }</a></td>
			<td><a 
				<c:if test="${count_180service_warnning!=0 }">
					style="color:blue"
				</c:if>
			href="${path }/service/listForSearch.do?key=count_180service_warnning">${count_180service_warnning }</a></td>
			<td><a 
				<c:if test="${count_180service_overdue!=0 }">
					style="color:red"
				</c:if>
			href="${path }/service/listForSearch.do?key=count_180service_overdue">${count_180service_overdue }</a></td>
		</tr>
		<tr>
			<td>年度维保</td>
			<td><a 
				<c:if test="${count_360service_normal!=0 }">
					style="color:green"
				</c:if>	
			href="${path }/service/listForSearch.do?key=count_360service_normal">${count_360service_normal }</a></td>
			<td><a 
				<c:if test="${count_360service_warnning!=0 }">
					style="color:blue"
				</c:if>	
			href="${path }/service/listForSearch.do?key=count_360service_warnning">${count_360service_warnning }</a></td>
			<td><a 
				<c:if test="${count_360service_overdue!=0 }">
					style="color:red"
				</c:if>	
			href="${path }/service/listForSearch.do?key=count_360service_overdue">${count_360service_overdue }</a></td>
		</tr>
	</table>
</ul>
<ul>
	<li>提前提示时间:年检${system_setting.alarm_test}天,半月维保${system_setting.alarm_15_service}天,
	季度维保${system_setting.alarm_90_service}天,半年维保${system_setting.alarm_180_service}天,
	年度维保${system_setting.alarm_360_service}天。
</ul>
</body>
</html>
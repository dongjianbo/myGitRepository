<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link href="${path}/css/table.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${path }/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript">
	
	$().ready(function(){
		
		
	});
</script>
</head>
<body>
<ul>
	<li><h3>${user.name }&nbsp;&nbsp;各状态下的电梯数量</h3>
	<li><table cellpadding="0" cellspacing="1">
	<tr>
		<th>电梯总数量</th>
		<th>已注册数量</th>
		<th>待注册数量</th>
		<th>已停用数量</th>
		<th>已注销数量</th>
	</tr>
	<tr>
		<td><a href="${path }/user/listForSearch.do?key=count">${count }</a></td>
		<td><a href="${path }/user/listForSearch.do?key=count_registed">${count_registed}</a></td>
		<td><a href="${path }/user/listForSearch.do?key=count_noregist">${count_noregist }</a></td>
		<td><a href="${path }/user/listForSearch.do?key=count_stop">${count_stop }</a></td>
		<td><a href="${path }/user/listForSearch.do?key=count_destory">${count_destory}</a></td>
	</tr>
	</table>
	<li><h3>已注册电梯统计</h3>
	<li><table cellpadding="0" cellspacing="1">
		<tr>
			<th>&nbsp;</th>
			<th>正常数量</th>
			<th>提示数量</th>
			<th>逾期数量</th>
		</tr>
		<tr>
			<td>年检</td>
			<td><a href="${path }/user/listForSearch.do?key=count_rounds_normal">${count_rounds_normal }</a></td>
			<td><a href="${path }/user/listForSearch.do?key=count_rounds_warnning">${count_rounds_warnning }</a></td>
			<td><a href="${path }/user/listForSearch.do?key=count_rounds_overdue">${count_rounds_overdue }</a></td>
		</tr>
		<tr>
			<td>半月维保</td>
			<td><a href="${path }/user/listForSearch.do?key=count_15user_normal">${count_15user_normal }</a></td>
			<td><a href="${path }/user/listForSearch.do?key=count_15user_warnning">${count_15user_warnning }</a></td>
			<td><a href="${path }/user/listForSearch.do?key=count_15user_overdue">${count_15user_overdue }</a></td>
		</tr>
		<tr>
			<td>季度维保</td>
			<td><a href="${path }/user/listForSearch.do?key=count_90user_normal">${count_90user_normal }</a></td>
			<td><a href="${path }/user/listForSearch.do?key=count_90user_warnning">${count_90user_warnning }</a></td>
			<td><a href="${path }/user/listForSearch.do?key=count_90user_overdue">${count_90user_overdue }</a></td>
		</tr>
		<tr>
			<td>半年维保</td>
			<td><a href="${path }/user/listForSearch.do?key=count_180user_normal">${count_180user_normal }</a></td>
			<td><a href="${path }/user/listForSearch.do?key=count_180user_warnning">${count_180user_warnning }</a></td>
			<td><a href="${path }/user/listForSearch.do?key=count_180user_overdue">${count_180user_overdue }</a></td>
		</tr>
		<tr>
			<td>年度维保</td>
			<td><a href="${path }/user/listForSearch.do?key=count_360user_normal">${count_360user_normal }</a></td>
			<td><a href="${path }/user/listForSearch.do?key=count_360user_warnning">${count_360user_warnning }</a></td>
			<td><a href="${path }/user/listForSearch.do?key=count_360user_overdue">${count_360user_overdue }</a></td>
		</tr>
	</table>
</ul>

</body>
</html>
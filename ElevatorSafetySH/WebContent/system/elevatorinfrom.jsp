<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详细列表</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
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
	
</head>
<body>
  <ul>
  <li><button onclick="history.back();">返回上一页</button>
  <li><h2>${eById.id_elevator }号${eById.elevatorType.name}详细信息</h2>
  </ul>
	<table width="100%" height="100%" id="table">	
		        <tr>
		           <td>
		           <ul>
		           <li>登记机构:
		           <li><input type="text" value="${eById.register_org }" size="30" readonly="readonly"/>
		           <li>特种设备登记代码:
		           <li><input type="text" value="${eById.register_code }" readonly="readonly" size="30" />
		           <li>特种设备代码:
		           <li><input type="text" value="${eById.device_code }" readonly="readonly" size="30" />
			       <li>设计单位名称:
			       <li><input type="text" value="${eById.designer.name}" readonly="readonly" size="30" />
		           <li>生产单位名称:
		           <li><input type="text" value="${eById.manufer.name}" readonly="readonly" size="30" />
		           <li>生产日期:
		           <li><input type="text" value="${eById.date_manufer }" readonly="readonly" size="30" />
		           <li>出厂编号:
		           <li><input type="text" value="${eById.code_manufer }" readonly="readonly" size="30" />
		           <li>土建施工单位:<li>
				   <li><input type="text" value="${eById.constucter }" readonly="readonly" size="30" />
				   <li>土建施工开始时间:
				   <li><input type="text" value="${eById.startdate_construct }" readonly="readonly" size="30" />
		            
					</ul>
					
		        </td>
		        <td>
		           <ul>
		            <li>土建施工竣工时间:
					<li><input type="text" value="${eById.enddate_construct }" readonly="readonly" size="30" />
		            <li> 土建验收单位名称:
					<li><input type="text" value="${eById.accepter_construct }" readonly="readonly" size="30" />
					<li> 验收检验机构:
					<li><input type="text" value="${eById.check_construct }" readonly="readonly" size="30" />
					<li> 验收报告编号:
					<li><input type="text" value="${eById.check_construct_code }" readonly="readonly" size="30" />
					<li> 安装单位名称:
					<li><input type="text" value="${eById.installer.name }" readonly="readonly" size="30" />
					<li> 产权单位名称:
					<li><input type="text" value="${eById.owner.name }" readonly="readonly" size="30" />
					<li> 使用单位名称:
					<li><input type="text" value=" ${eById.user.name }" readonly="readonly" size="30" />
                    <li>电梯所在位置::
					<li><input type="text" value="${eById.address }" readonly="readonly" size="30" />
					<li> 申报时间:
					<li><input type="text" value="${eById.date_declare }" readonly="readonly" size="30" />
					 
                 </ul>
		     </td>
		     <td>
		         <ul>
		            <li>注册时间:
					<li><input type="text" value="${eById.date_register }" readonly="readonly" size="30" />
					<li> 开始使用时间:
					<li><input type="text" value="${eById.date_enable }" readonly="readonly" size="30" />
					<li>安装项目责任人:
					<li><input type="text" value="${eById.project_duty }" readonly="readonly" size="30" />
					<li>  维保单位名称:
					<li><input type="text" value="${eById.service.name }" readonly="readonly" size="30" />
					<li> 检验坚持测单位名称:
					<li><input type="text" value=" ${eById.test.name }" readonly="readonly" size="30" />
					<li>电梯层数:
					<li><input type="text" value=" ${eById.num_floor_elevator }" readonly="readonly" size="30" />
					<li>电梯型号:
					<li><input type="text" value="${eById.elevatorType.name }" readonly="readonly" size="30" />
					<li>注册状态 :
					<li><input type="text" value="${eById.regist_status.name }" readonly="readonly" size="30" />
					<li> 电梯简称:
					<li><input type="text" value=" ${eById.desc }" readonly="readonly" size="30" />
		        </ul>
		      </td>
      </tr>
      <tr>
       <td colspan="3">
       <ul>
       <li><input type="button" onclick="history.back();" value="返回上一页">
       </ul>
       </td>
      </tr>
      </table>

</body>
</html>
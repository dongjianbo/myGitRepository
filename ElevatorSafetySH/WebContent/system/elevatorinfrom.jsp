<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 
	<table width="100%" height="100%" id="table">	
		        <tr>
		           <td style="text-align: left;">
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
		        <td style="text-align: left;">
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
		     <td style="text-align: left;">
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
					<li><input type="text" value="${eById.model.modelname }" readonly="readonly" size="30" />
					<li>注册状态 :
					<li><input type="text" value="${eById.regist_status.name }" readonly="readonly" size="30" />
					<li> 电梯简称:
					<li><input type="text" value=" ${eById.desc }" readonly="readonly" size="30" />
		        </ul>
		      </td>
		      <td style="text-align: left;">
		      	<ul>
		      		<li>上次半月维保日期
		      		<li><input type="text" value="${es.last_15_service}" readonly="readonly" size="30"/>
		      		<li>上次季度维保日期
		      		<li><input type="text" value="${es.last_90_service}" readonly="readonly" size="30"/>
		      		<li>上次半年维保日期
		      		<li><input type="text" value="${es.last_180_service}" readonly="readonly" size="30"/>
		      		<li>上次年度维保日期
		      		<li><input type="text" value="${es.last_360_service}" readonly="readonly" size="30"/>
		      		<li>上次安全员巡检日期
		      		<li><input type="text" value="${es.lastrounds}" readonly="readonly" size="30"/>
		      		<li>上次检验检测日期
		      		<li><input type="text" value="${es.lasttest}" readonly="readonly" size="30"/>
		      		<li>标签数据下载标识
		      		<li><input type="text" value="${es.labelwrite=='1'?'已写':'待写'}" readonly="readonly" size="30"/>
		      		<li>标签下载备注项
		      		<li><input type="text" value="${es.labeldemo}" readonly="readonly" size="30"/>
		      		<li>最后修改日期
		      		<li><input type="text" value="${es.lastmodified}" readonly="readonly" size="30"/>
		      	</ul>
		      </td>
      </tr>
     
      </table>

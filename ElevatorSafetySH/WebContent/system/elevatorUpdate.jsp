<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<script src="${path}/jquery/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
	<script type="text/javascript">
	$().ready(function(){
		$("#mapDialog").dialog({
			modal:true,
			autoOpen:false,
			width:800,
			height:640,
		    href: 'testBMap2.jsp',
			buttons:{
				"确定":function(){
					$(this).dialog("close");
				}
			}, 
			close:function(){
				$(this).dialog("close");
			}
		});
		$("#date_declare").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date_enable").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date_manufer").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date_register").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#enddate_construct").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#startdate_construct").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#elevator").change(function(){
			var eid=$(this).val();
			if(eid!=-1){
				$.getJSON("${path}/elevator/getElevatorById.do?eid="+eid,null,function(e){
					$("#id_elevator").val(e.id_elevator);
					$("#register_org").val(e.register_org);
					$("#register_code").val(e.register_code);
					$("#device_code").val(e.device_code);
					$("#id_designer option").each(function(){
							$(this).removeAttr("selected");
					})
					$("#id_designer option").each(function(){
						if($(this).val()==e.id_designer){
							$(this).attr("selected",true);
						}
					})
					//生产单位名称
					$("#id_manufer option").each(function(){
							$(this).removeAttr("selected");
					})
					$("#id_manufer option").each(function(){
						if($(this).val()==e.id_manufer){
							$(this).attr("selected",true);
						}
					})
					//
					$("#date_manufer").val(e.date_manufer);
					$("#code_manufer").val(e.code_manufer);
					$("#constucter").val(e.constucter);
					$("#startdate_construct").val(e.startdate_construct);
					$("#enddate_construct").val(e.enddate_construct);
					$("#accepter_construct").val(e.accepter_construct);
					$("#check_construct").val(e.check_construct);
					$("#check_construct_code").val(e.check_construct_code);
					//安装单位名称
					$("#id_installer option").each(function(){
							$(this).removeAttr("selected");
					})
					$("#id_installer option").each(function(){
						if($(this).val()==e.id_installer){
							$(this).attr("selected",true);
						}
					})
					//产权单位名称
					$("#id_owner option").each(function(){
							$(this).removeAttr("selected");
					})
					$("#id_owner option").each(function(){
						if($(this).val()==e.id_owner){
							$(this).attr("selected",true);
						}
					})
					//使用单位名称
					$("#id_user option").each(function(){
							$(this).removeAttr("selected");
					})
					$("#id_user option").each(function(){
						if($(this).val()==e.id_user){
							$(this).attr("selected",true);
						}
					})
					$("#address").val(e.address);
					$("#date_declare").val(e.date_declare);
					$("#date_register").val(e.date_register);
					$("#date_enable").val(e.date_enable);
					$("#project_duty").val(e.project_duty);
					//维保单位
					$("#id_service option").each(function(){
							$(this).removeAttr("selected");
					})
					$("#id_service option").each(function(){
						if($(this).val()==e.id_service){
							$(this).attr("selected",true);
						}
					})
					//检验检测单位
					$("#id_test option").each(function(){
							$(this).removeAttr("selected");
					})
					$("#id_test option").each(function(){
						if($(this).val()==e.id_test){
							$(this).attr("selected",true);
						}
					})
					$("#num_floor_elevator").val(e.num_floor_elevator);
					//电梯型号
					$("#id_elevator_model option").each(function(){
							$(this).removeAttr("selected");
					})
					$("#id_elevator_model option").each(function(){
						if($(this).val()==e.id_elevator_model){
							$(this).attr("selected",true);
						}
					})
					//注册状态
					$("#register_status option").each(function(){
							$(this).removeAttr("selected");
					})
					$("#register_status option").each(function(){
						if($(this).val()==e.register_status){
							$(this).attr("selected",true);
						}
					})
					//电梯安装场所类型
					$("#id_siteDef option").each(function(){
							$(this).removeAttr("selected");
					})
					$("#id_siteDef option").each(function(){
						if($(this).val()==e.gis_type){
							$(this).attr("selected",true);
						}
					})
					$("#desc").val(e.desc);
					$("#gis").val(e.gis_x+","+e.gis_y);
					$("#gis_x").val(e.gis_x);
					$("#gis_y").val(e.gis_y);
				});
			}
		});
		$("#form_sub").click(function(){
			if($("#id_elevator").val()==""){
				alert("请选择要修改的电梯");
				return;
			}
			if($("#register_code").val()==""){
				alert("特种设备登记代码不可为空！");
				return;
			}
			if($("#device_code").val()==""){
				alert("特种设备代码不可为空！");
				return;
			}
			if($("#id_designer").val()==-1){
				alert("请选择设计单位");
				return;
			}
			if($("#id_manufer").val()==-1){
				alert("请选择生产单位");
				return;
			}
			if($("#id_installer").val()==-1){
				alert("请选择安装单位");
				return;
			}
			if($("#id_owner").val()==-1){
				alert("请选择产权单位");
				return;
			}
			if($("#id_user").val()==-1){
				alert("请选择使用单位");
				return;
			}
			if($("#id_service").val()==-1){
				alert("请选择维保单位");
				return;
			}
			if($("#id_test").val()==-1){
				alert("请选择检验检测单位");
				return;
			}
			if($("#id_elevator_model").val()==-1){
				alert("请选择电梯型号");
				return;
			}
			if($("#register_status").val()==-1){
				alert("请选择注册状态");
				return;
			}
			$("#myform1").submit();
		});
		$("#gis").click(function(){
			document.getElementById("prodcutDetailSrc").src="${path }/elevator/map2.do?rand="+Math.random();
			$("#mapDialog").dialog("open");
		});
	});
	</script>
	</head>
	<body>

 	<ul>
 		<li>请选择要修改的电梯:
 		<select id="elevator">
 			<option value="-1">请选择</option>
 			<c:forEach items="${elevatorList }" var="e">
 				<option value="${e[0] }">${e[1] }</option>
 			</c:forEach>
 		</select>
 		
 	</ul>
 	<hr>
<form id="myform1" action="${path }/elevator/update.do" method="post">
<input type="hidden" id="id_elevator" name="id_elevator"/>
<table width="100%" height="100%" id="table" cellspacing="0">	
        <tr>
           <td style="text-align: left;vertical-align: top;">
           <ul>
           <li>登记机构:
           <li><input type="text" id="register_org" name="register_org" size="50"/>
           <li>特种设备登记代码:
           <li><input type="text" id="register_code" name="register_code" size="50" />
           <li>特种设备代码:
           <li><input type="text" id="device_code" name="device_code"  size="50" />
	       <li>设计单位名称:
	       <li><select id="id_designer" name="id_designer">
	       	<option value="-1">请选择</option>
	       	<c:forEach items="${designerList }" var="v">
	       		<option value="${v[0]}">${v[1] }</option>
	       	</c:forEach>
	       </select>
           <li>生产单位名称:
           <li><select id="id_manufer" name="id_manufer">
	       	<option value="-1">请选择</option>
	       	<c:forEach items="${manuferList }" var="v">
	       		<option value="${v.idmanufer}">${v.name}</option>
	       	</c:forEach>
	       </select>
           <li>生产日期:
           <li><input type="text" id="date_manufer" name="date_manufer" readonly="readonly" size="30" />
           <li>出厂编号:
           <li><input type="text" id="code_manufer" name="code_manufer" size="30" />
           <li>土建施工单位:<li>
		   <li><input type="text" id="constucter" name="constucter" size="50" />
		   <li>土建施工开始时间:
		   <li><input type="text" id="startdate_construct" name="startdate_construct" readonly="readonly" size="30" />
           <li>电梯安装场所类型:
           <li><select id="id_siteDef" name="gis_type">
	       	<option value="">请选择</option>
	       	<c:forEach items="${siteDefList }" var="v">
	       		<option value="${v.idsite}">${v.site_name}</option>
	       	</c:forEach>
	       </select>
			</ul>
			
        </td>
        <td style="text-align: left;vertical-align: top;">
           <ul>
            <li>土建施工竣工时间:
			<li><input type="text" id="enddate_construct" name="enddate_construct" readonly="readonly" size="30" />
            <li> 土建验收单位名称:
			<li><input type="text" id="accepter_construct" name="accepter_construct"  size="50" />
			<li> 验收检验机构:
			<li><input type="text" id="check_construct" name="check_construct"  size="50" />
			<li> 验收报告编号:
			<li><input type="text" id="check_construct_code" name="check_construct_code" size="30" />
			<li> 安装单位名称:
			<li><select id="id_installer" name="id_installer">
	       	<option value="-1">请选择</option>
	       	<c:forEach items="${installerList }" var="v">
	       		<option value="${v[0]}">${v[1]}</option>
	       	</c:forEach>
	       </select>
			<li> 产权单位名称:
			<li><select id="id_owner" name="id_owner">
	       	<option value="-1">请选择</option>
	       	<c:forEach items="${ownerList }" var="v">
	       		<option value="${v[0]}">${v[1]}</option>
	       	</c:forEach>
	       </select>
			<li> 使用单位名称:
			<li><select id="id_user" name="id_user">
	       	<option value="-1">请选择</option>
	       	<c:forEach items="${userList }" var="v">
	       		<option value="${v.iduser}">${v.name}</option>
	       	</c:forEach>
	       </select>
            <li>电梯所在位置:
			<li><input type="text" id="address" name="address" size="50" />
			<li>电梯坐标:
			<li><input type="text" id="gis" name="gis" size="50" />
			<input type="hidden" id="gis_x" name="gis_x" size="50" />
			<input type="hidden" id="gis_y" name="gis_y" size="50" />
			<li> 申报时间:
			<li><input type="text" id="date_declare" name="date_declare" readonly="readonly" size="30" />
			 
               </ul>
     </td>
     <td style="text-align: left;vertical-align: top;">
         <ul>
            <li>注册时间:
			<li><input type="text" id="date_register" name="date_register" readonly="readonly" size="30" />
			<li>开始使用时间:
			<li><input type="text" id="date_enable" name="date_enable" readonly="readonly" size="30" />
			<li>安装项目责任人:
			<li><input type="text" id="project_duty" name="project_duty" size="50" />
			<li>维保单位名称:
			<li><select id="id_service" name="id_service">
	       	<option value="-1">请选择</option>
	       	<c:forEach items="${serviceList }" var="v">
	       		<option value="${v.idservice}">${v.name}</option>
	       	</c:forEach>
	       </select>
			<li>检验检测单位名称:
			<li><select name="id_test" id="id_test">
	       	<option value="-1">请选择</option>
	       	<c:forEach items="${testList }" var="v">
	       		<option value="${v.idtest}">${v.name}</option>
	       	</c:forEach>
	       </select>
			<li>电梯层数:
			<li><input type="text" id="num_floor_elevator" name="num_floor_elevator" size="30" />
			<li>电梯型号:
			<li><select id="id_elevator_model" name="id_elevator_model">
	       	<option value="-1">请选择</option>
	       	<c:forEach items="${modelList }" var="v">
	       		<option value="${v.idmodel}">${v.modelname}</option>
	       	</c:forEach>
	       </select>
			<li>注册状态 :
			<li><select id="register_status" name="register_status">
	       	<option value="-1">请选择</option>
	       	<c:forEach items="${regList }" var="v">
	       		<option value="${v.id_register_status}">${v.name}</option>
	       	</c:forEach>
	       </select>
			<li>电梯简称:
			<li><input type="text" id="desc" name="desc"  size="50" />
			<li>&nbsp;
      		<li><input type="button" id="form_sub" style="margin-left: 200px;width: 100px;" value="提交修改内容"/>
        </ul>
      </td>
    </tr>
</table>
</form>
<div id="mapDialog" style="display: none" title="map">
<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" 
		id="prodcutDetailSrc"  scrolling="no"  width="100%" height="100%" ></iframe>
		<!-- <div align="center" style="width:600px;height:400px;border:0px solid gray" id="container"></div> -->
</div>
</body>
</html>
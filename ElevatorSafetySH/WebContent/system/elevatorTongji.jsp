<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link href="${path}/css/table.css" rel="stylesheet" type="text/css">
<style type="text/css">
a{
	text-decoration: underline;
	
}
span{
	font-size: 12px;
	font-weight: bold;
	padding-left: 50px;
}
</style>
<script type="text/javascript" src="${path }/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript">
	
	$().ready(function(){
			//获取当前登录人的所在城市
			var id_city=$("#idcity").val();
			//如果是省直,则显示所有的城市
			if(id_city=="00"){
				$.getJSON("${path }/citylist/list.do","rand="+Math.random(),function(d){
					$("#idcity1").append("<option size='"+50+"' value=''>-请选择</option>");
					//将查询到的信息放入表单
					for(var i=0;i<d.length;i++){
						if(d[i].id_city!="99"&&d[i].id_city!="00"){
						  $("#idcity1").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
						}
					}
					//如果是提交过一次
					if("${param.id_city==null}"=="false"){
						$("#idcity1 option").each(function(){
							if($(this).val()=="${param.id_city}"){
								$(this).attr("selected",true);
								return;
							}
						});
						var id_city="${param.id_city}";
	 					if(id_city==""){
	 						$("#iddistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
	 						$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
	 						return;
	 					}
	 					//把数据放入下拉框
	 					$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
	 						document.getElementById("iddistrict1").innerHTML="";
	 						$("#iddistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
	 						for(var i=0;i<s.length;i++){
	 							if(s[i].id_district=="00"){
	 								continue;
	 							}
	 							 $("#iddistrict1").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
	 						}
	 						$("#iddistrict1 option").each(function(){
	 							if($(this).val()=="${param.id_district}"){
	 								$(this).attr("selected",true);
	 								return;
	 							}
	 						});
	 						var id_district="${param.id_district}";
	 						if(id_district==""){
	 							$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
	 							return;
	 						}
	 						$.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
			 					
	 							document.getElementById("idsubdistrict1").innerHTML="";
	 							$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
	 		 					 for(var i=0;i<a.length;i++){
	 		 			    		$("#idsubdistrict1").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
	 		 			    	}
	 		 					$("#idsubdistrict1 option").each(function(){
	 		 	 					if($(this).val()=="${param.id_subdistrict}"){
	 		 	 						$(this).attr("selected",true);
	 		 	 					}
	 		 	 				});
	 		 				});
							
	 					});
						
					}else{
						$("#iddistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
						$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
					}
					
	// 				$("#iddistrict1").attr("disabled",true);
	// 				$("#idsubdistrict1").attr("disabled",true);
				});
			}else{
				$("#idcity1").append("<option size='"+50+"' value='${login.idcity}'>${login.city.name_city}</option>");
				var id_district=$("#iddistrict").val();
				//如果是市直
				if(id_district=="00"){
					//把数据放入下拉框
					$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
						document.getElementById("iddistrict1").innerHTML="";
						$("#iddistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
						for(var i=0;i<s.length;i++){
							if(s[i].id_district=="00"){
								continue;
							}
							 $("#iddistrict1").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
						}
						//如果提交过一次
						if("${param.id_district==null}"=="false"){
							$("#iddistrict1 option").each(function(){
	 							if($(this).val()=="${param.id_district}"){
	 								$(this).attr("selected",true);
	 								return;
	 							}
	 						});
							var id_district="${param.id_district}";
							$.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
			 					
	 							document.getElementById("idsubdistrict1").innerHTML="";
	 							$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
	 		 					 for(var i=0;i<a.length;i++){
	 		 			    		$("#idsubdistrict1").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
	 		 			    	}
	 		 					$("#idsubdistrict1 option").each(function(){
	 		 	 					if($(this).val()=="${param.id_subdistrict}"){
	 		 	 						$(this).attr("selected",true);
	 		 	 					}
	 		 	 				});
	 		 				});
							
						}
						$("#idsubdistrict1 option").remove();
						$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
	// 					$("#idsubdistrict1").attr("disabled",true);
							
					});
				}else{
					$("#iddistrict1").append("<option size='"+50+"' value='"+id_district+"'>${login.distict.name_district}</option>");
					var id_subdistrict=$("#idsubdistrict").val();
					if(id_subdistrict==""){
						$.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
		 					
							document.getElementById("idsubdistrict1").innerHTML="";
							$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
		 					 for(var i=0;i<a.length;i++){
		 			    		$("#idsubdistrict1").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
		 			    	}
		 					$("#idsubdistrict1 option").each(function(){
		 	 					if($(this).val()=="${param.id_subdistrict}"){
		 	 						$(this).attr("selected",true);
		 	 					}
		 	 				});
		 				});
						$("#idsubdistrict1 option").remove();
						$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
					}else{
						$("#idsubdistrict1 option").remove();
						$("#idsubdistrict1").append("<option size='"+50+"' value='"+id_district+"'>${login.subdistict.name_subdistrict}</option>");
					}
					

				}
			}
			
			
				//如果是省直
// 				if("${param.id_city}"=="00"){
					
// 				}
// 				$.getJSON("${path }/citylist/list.do","rand="+Math.random(),function(d){
// 					$("#idcity1").append("<option size='"+50+"' value=''>-请选择</option>");
// 					//将查询到的信息放入表单
// 					for(var i=0;i<d.length;i++){
// 						if(d[i].id_city!="99"&&d[i].id_city!="00"){
// 						  $("#idcity1").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
// 						}
// 					}
// 					$("#idcity1 option").each(function(){
// 						if($(this).val()=="${param.id_city}"){
// 							$(this).attr("selected",true);
// 							return;
// 						}
// 					});
// 					var id_city="${param.id_city}";
// 					if(id_city==""){
// 						$("#iddistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
// 						$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
// 						return;
// 					}
// 					//把数据放入下拉框
// 					$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
// 						document.getElementById("iddistrict1").innerHTML="";
// 						$("#iddistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
// 						for(var i=0;i<s.length;i++){
// 							if(s[i].id_district=="00"){
// 								continue;
// 							}
// 							 $("#iddistrict1").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
// 						}
// 						$("#iddistrict1 option").each(function(){
// 							if($(this).val()=="${param.id_district}"){
// 								$(this).attr("selected",true);
// 								return;
// 							}
// 						});
// 						var id_district="${param.id_district}";
// 						if(id_district==""){
// 							$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
// 							return;
// 						}
// 						$.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
		 					
// 							document.getElementById("idsubdistrict1").innerHTML="";
// 							$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
// 		 					 for(var i=0;i<a.length;i++){
// 		 			    		$("#idsubdistrict1").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
// 		 			    	}
// 		 					$("#idsubdistrict1 option").each(function(){
// 		 	 					if($(this).val()=="${param.id_subdistrict}"){
// 		 	 						$(this).attr("selected",true);
// 		 	 					}
// 		 	 				});
// 		 				});
						
// 					});
// 				});
			
		
		$.getJSON("${path}/service/list_json.do","rand="+Math.random(),function(d){
			$("#id_service").append("<option size='"+15+"' value='0'>-请选择</option>");
    	  //对维保单位进行循环
    	  for(var i=0;i<d.length;i++){
    		  $("#id_service").append("<option size='"+15+"' value='"+d[i].idservice+"'>"+d[i].name+"</option>");
    	  }
    	  $("#id_service option").each(function(){
				if($(this).val()=="${param.id_service}"){
					$(this).attr("selected",true);
				}
			});
       });
		$.getJSON("${path}/test/list_json.do","rand="+Math.random(),function(d){
    	  //对检验检测单位进行循环
    	  $("#id_test").append("<option size='"+15+"' value='0'>-请选择</option>");
    	  for(var i=0;i<d.length;i++){
    		  $("#id_test").append("<option size='"+15+"' value='"+d[i].idtest+"'>"+d[i].name+"</option>");
    	  }
    	  $("#id_test option").each(function(){
				if($(this).val()=="${param.id_test}"){
					$(this).attr("selected",true);
				}
			});
       });
		$.getJSON("${path}/user/selectId_user.do","rand="+Math.random(),function(d){
			 $("#id_user").append("<option size='"+15+"' value='0'>-请选择</option>");
	    	  //对使用单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_user").append("<option size='"+15+"' value='"+d[i].iduser+"'>"+d[i].name+"</option>");
	    	  }
	    	  $("#id_user option").each(function(){
					if($(this).val()=="${param.id_user}"){
						$(this).attr("selected",true);
					}
				});
	       });

	});
	
	
	function choosedistrict1(id_district){
		//如果选择市直
		if(id_district=="00"||id_district==""){
			$("#idsubdistrict1 option").remove();
			$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
// 			$("#idsubdistrict1").attr("disabled",true);
		}else{
// 			$("#idsubdistrict1").attr("disabled",false);
		//去城市的id
		var id_city=document.getElementById("idcity1").value;
		 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
			 document.getElementById("idsubdistrict1").innerHTML="";
			 $("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
			 for(var i=0;i<a.length;i++){
		    		$("#idsubdistrict1").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
		    	}
		    });
		}
	}
	
	function chooseCity1(id_city){
		if(id_city==""){
			$("#iddistrict1 option").remove();
			$("#idsubdistrict1 option").remove();
			$("#iddistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
			$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
// 			$("#iddistrict1").attr("disabled",true);
// 			$("#idsubdistrict1").attr("disabled",true);
			return;
		}
			$("#iddistrict1").attr("disabled",false);
			//不同的城市选择不同的id
			$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
				document.getElementById("iddistrict1").innerHTML="";
				$("#iddistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
				for(var i=0;i<s.length;i++){
					if(s[i].id_district=="00"){
						continue;
					}
					 $("#iddistrict1").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
					}
				$("#idsubdistrict1 option").remove();
				$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
// 				$("#idsubdistrict1").attr("disabled",true);
			});
		
	}
</script>
</head>
<body>
<input type="hidden" value="${login.idcity}" id="idcity"/>
<input type="hidden" value="${login.iddistrict}" id="iddistrict"/>
<input type="hidden" value="${login.idsubdistrict}" id="idsubdistrict"/>
<form action="${path }/elevator/search.do" method="get">
<div style="margin-top: 50px;">
	<ul>
			<li>
			<span>所属城市:</span><select name="id_city" id="idcity1" onchange="chooseCity1(this.value)">
			</select>
			<span>所属区县:</span><select name="id_district" id="iddistrict1" onchange="choosedistrict1(this.value)">
			</select> 
			<span>所属乡镇街道:</span><select id="idsubdistrict1" name="id_subdistrict">
			</select>
	</ul>
</div>
<br>
<div>
	<ul>
			<li><span>维保单位:</span><select name="id_service" id="id_service">
			</select>
			<span>使用单位:</span><select name="id_user" id="id_user">
			</select>
			<span>检验检测单位:</span><select name="id_test" id="id_test">
			</select>
		</ul>
</div>
<br>
<div>
	<ul>
		<li><span>电梯简称:</span><input type="text" id="desc" name="desc" size="39" maxlength="16" value="${desc }"/>
	</ul>
</div>
<br>
<div style="width: 95%;text-align: right;">
	<input type="submit" value="&nbsp;&nbsp;搜索&nbsp;&nbsp;"/>
</div>
</form>
<hr>
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
		<td><a href="${path }/elevator/listForSearch.do?key=count&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count }</a></td>
		<td><a href="${path }/elevator/listForSearch.do?key=count_registed&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_registed}</a></td>
		<td><a href="${path }/elevator/listForSearch.do?key=count_noregist&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_noregist }</a></td>
		<td><a href="${path }/elevator/listForSearch.do?key=count_stop&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_stop }</a></td>
		<td><a href="${path }/elevator/listForSearch.do?key=count_destory&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_destory}</a></td>
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
			<td><a href="${path }/elevator/listForSearch.do?key=count_rounds_normal&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_rounds_normal }</a></td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_rounds_warnning&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_rounds_warnning }</a></td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_rounds_overdue&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_rounds_overdue }</a></td>
		</tr>
		<tr>
			<td>半月维保</td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_15service_normal&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_15service_normal }</a></td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_15service_warnning&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_15service_warnning }</a></td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_15service_overdue&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_15service_overdue }</a></td>
		</tr>
		<tr>
			<td>季度维保</td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_90service_normal&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_90service_normal }</a></td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_90service_warnning&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_90service_warnning }</a></td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_90service_overdue&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_90service_overdue }</a></td>
		</tr>
		<tr>
			<td>半年维保</td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_180service_normal&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_180service_normal }</a></td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_180service_warnning&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_180service_warnning }</a></td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_180service_overdue&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_180service_overdue }</a></td>
		</tr>
		<tr>
			<td>年度维保</td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_360service_normal&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_360service_normal }</a></td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_360service_warnning&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_360service_warnning }</a></td>
			<td><a href="${path }/elevator/listForSearch.do?key=count_360service_overdue&id_service=${param.id_service}&id_test=${param.id_test}&id_user=${param.id_user}&id_city=${id_city}&id_district=${id_district}&id_subdistrict=${id_subdistrict}&desc=${desc}">${count_360service_overdue }</a></td>
		</tr>
	</table>
</ul>
<ul>
	<li><h3>备注：年检提前提示时间${system_setting.alarm_test}天,半月维保提前提示时间${system_setting.alarm_15_service}天,
	季度维保提前提示时间${system_setting.alarm_90_service}天,半年维保提前提示时间${system_setting.alarm_180_service}天,
	年度维保提前提示时间${system_setting.alarm_360_service}天。</h3>
</ul>

</body>
</html>
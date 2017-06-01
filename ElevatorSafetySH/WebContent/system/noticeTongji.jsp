<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>elevatorTongji.jsp</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link href="${path}/css/table.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="${path}/jquery/themes/base/jquery.ui.all.css">
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
		//对话框
		$("#notice").dialog({
			modal:true,
			autoOpen:false,
			width:800,
			height:300,
			buttons:{
				"确定":function(){
					$(this).dialog("close");
				}
			},
			close:function(){
				$(this).dialog("close");
			}
		});
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
	function form_submit(ck){
		$("#noticeType").val(ck);
		$("#form1").submit();
	}
	function notice1(user,desc,registerCode,maintType,noticeDate){
		$("#notice").html("");
		var message="<h3>"+user+":</h3>";
		message+="<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据《中华人民共特种设备安全法》和《河南省电梯安全管理办法》有关规定，你单位位于<i style='color:blue'>"+desc;
		message+="</i>,注册号为<i style='color:blue'>"+registerCode+"</i>电梯，未依法按时维保，<i style='color:blue'>"+maintType;
		message+="维保</i>于<i style='color:blue'>"+noticeDate+"</i>逾期，限你单位在3日内到所属县、区质监部门接受调查。<h3>";
		message+="<h3 align='right'>漯河市质量技术监督局<br>"+noticeDate+"</h3>";
		$("#notice").html(message);
		$("#notice").dialog("open");
	}
</script>
</head>
<body>
<input type="hidden" value="${login.idcity}" id="idcity"/>
<input type="hidden" value="${login.iddistrict}" id="iddistrict"/>
<input type="hidden" value="${login.idsubdistrict}" id="idsubdistrict"/>
<form action="${path }/notice/search.do" method="get" id="form1">
<div style="margin-top: 10px;">
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
			<div style="display:none">
			<span>检验检测单位:</span><select name="id_test" id="id_test">
			</select>
			</div>
			
		</ul>
</div>
<br>
<div>
	<ul>
		<li><span>电梯简称:</span><input type="text" id="desc" name="desc" size="39" maxlength="16" value="${desc }"/>
		<input type="submit" style="margin-left: 650px" value="&nbsp;&nbsp;搜索&nbsp;&nbsp;"/>
	</ul>
</div>

<hr style="margin-top: 10px">
<ul>
	<li><h3>逾期通知单统计</h3>
	<li><table cellpadding="0" cellspacing="1">
	<tr>
		<th>逾期通知单总数</th>
		<th>已处理数目</th>
		<th>未处理数目</th>
	</tr>
	<tr>
		<td style="font-size: 14px;"><a href="javascript:form_submit(0)">${noticeCount }</a></td>
		<td style="color: blue;font-size: 14px;"><a href="javascript:form_submit(1)">${noticeCount_processed }</a></td>
		<td style="color: red;font-size: 14px;"><a href="javascript:form_submit(2)">${noticeCount_noprocessed }</a></td>
	</tr>
	</table>
	<li><h3>逾期通知单列表</h3>
		<input type="hidden" name="noticeType" id="noticeType" value="0"/></form>
	<li><table cellpadding="0" cellspacing="1">
		<tr>
			<th>电梯简称</th>
			<th>使用单位</th>
			<th>维保单位</th>
			<th>维保类型</th>
			<th>逾期日期</th>
			<th>完成日期</th>
			<th>通知书</th>
		</tr>
		<c:forEach items="${nList}" var="n">
		<tr>
			<td>${n.elevator.desc }</td>
			<td>${n.user.name }</td>
			<td>${n.service.name }</td>
			<td>${n.maintType.name }</td>
			<td>${n.date_notice }</td>
			<td>${n.this_service }</td>
			<td><a href="javascript:notice1('${n.user.name }','${n.elevator.desc }','${n.elevator.register_code}','${n.maintType.name }','${n.date_notice }')">通知书</a></td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="7" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
</ul>

<div id="notice" title="通知书"></div>
</body>
</html>
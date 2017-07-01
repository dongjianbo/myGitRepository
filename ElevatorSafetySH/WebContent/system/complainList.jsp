<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>repair_query.jsp</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link href="${path}/css/table.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${path}/jquery/themes/base/jquery.ui.all.css">
<!-- <style type="text/css">
a{
	text-decoration: underline;
	
}
span{
	font-size: 12px;
	font-weight: bold;
	padding-left: 50px;
} -->
</style>
		<script src="${path}/jquery/jquery-1.10.2.js"></script>
<script src="${path}/jquery/ui/jquery.ui.core.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.widget.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.mouse.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.button.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.draggable.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.position.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.resizable.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.dialog.js"></script>
	<script src="${path}/jquery/ui/jquery.ui.effect.js"></script>
<script type="text/javascript">
$().ready(function(){
	$.getJSON("${path}/complainObject/list_json.do","rand="+Math.random(),function(d){
		$("#type_object").append("<option size='"+15+"' value='-1'>-请选择</option>");
		$("#type_object1").append("<option size='"+15+"' value='-1'>-请选择</option>");
    	   for(var i=0;i<d.length;i++){
    		  $("#type_object").append("<option size='"+15+"' value='"+d[i].id_type+"'>"+d[i].type_name+"</option>");
    		  $("#type_object1").append("<option size='"+15+"' value='"+d[i].id_type+"'>"+d[i].type_name+"</option>");
    	  }
    	  $("#type_object option").each(function(){
				if($(this).val()=="${param.type_object}"){
					$(this).attr("selected",true);
				}
			}); 
    	  /* $("#type_object1 option").each(function(){
				if($(this).val()=="${param.type_object}"){
					$(this).attr("selected",true);
				}
			});  */
       });
	
		$("#object").change(function(){
			var type_object=$("#type_object1").val();
			var object=$(this).val();
			if(object!=-1&&type_object!=-1){
				$.getJSON("${path}/complain/getObjectByType.do?type="+type_object+"&object="+object,null,function(e){
					$("#id_object").val(e.id_object);
				});
			}
		});
	$.getJSON("${path}/complainSource/list_json.do","rand="+Math.random(),function(d){
		$("#source").append("<option size='"+15+"' value='-1'>-请选择</option>");
		$("#source1").append("<option size='"+15+"' value='-1'>-请选择</option>");
   	   for(var i=0;i<d.length;i++){
   		 $("#source").append("<option size='"+15+"' value='"+d[i].id_source+"'>"+d[i].source_name+"</option>");
   		 $("#source1").append("<option size='"+15+"' value='"+d[i].id_source+"'>"+d[i].source_name+"</option>");
   	  }
   	  $("#source option").each(function(){
			if($(this).val()=="${param.source}"){
				$(this).attr("selected",true);
			}
		}); 
	   	/* $("#source1 option").each(function(){
			if($(this).val()=="${param.source}"){
				$(this).attr("selected",true);
			}
		});  */
	});
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
	/* $.getJSON("${path}/dealStatus/list_json.do","rand="+Math.random(),function(d){
	$("#status").append("<option size='"+15+"' value='-1'>-请选择</option>");
   	   for(var i=0;i<d.length;i++){
   		  $("#status").append("<option size='"+15+"' value='"+d[i].id_deal+"'>"+d[i].deal_name+"</option>");
   	  }
   	  $("#status option").each(function(){
			if($(this).val()=="${param.id_deal}"){
				$(this).attr("selected",true);
			}
		}); 

    }); */
	$.getJSON("${path}/complainLevel/list_json.do","rand="+Math.random(),function(d){
		$("#level").append("<option size='"+15+"' value='-1'>-请选择</option>");
	   	   for(var i=0;i<d.length;i++){
	   		  $("#level").append("<option size='"+15+"' value='"+d[i].id_level+"'>"+d[i].level_name+"</option>");
	   	  }
	   	  $("#level option").each(function(){
				if($(this).val()=="${param.id_level}"){
					$(this).attr("selected",true);
				}
			}); 

	    });
			
	$("#complainDialog").dialog({
		modal:true,
		autoOpen:false,
		width:400,
		height:300,
		buttons:{
			"确定":function(){
				var form = $("#complainForm");
				//=================检查表单中的字段是否为空 ===================================
				
				//提交表单 
				$.post(form.attr('action'),form.serialize(),function(a){
					if(a=="ok"){
						location.reload();
					}else{
						alert("程序有点问题哟！");
					}
				});
	 		 },
			"关闭":function(){
				$(this).dialog("close");
			}
		},
		close:function(){
			$(this).dialog("close");
		}
	});
	$("#contentDialog").dialog({
		modal:true,
		autoOpen:false,
		width:400,
		height:250,
		buttons:{
			"关闭":function(){
				$(this).dialog("close");
			}
		},
		close:function(){
			$(this).dialog("close");
		}
	});
	$("#toComplainDialog").dialog({
		modal:true,
		autoOpen:false,
		width:680,
		height:400,
		buttons:{
			"确定":function(){
				var form = $("#complainInsertForm");
				//=================检查表单中的字段是否为空 ===================================
				var type_object=$("#type_object").val();
				var object=$("#object").val();
				if(type_object==""){
					alert("被投诉对象不能为空！");
					return;
				}
				if(object==""){
					alert("被投诉对象类型不能为空！");
					return;
				}
				//提交表单 
				$.post(form.attr('action'),form.serialize(),function(a){
					if(a=="ok"){
						location.reload();
					}else{
						alert("程序有点问题哟！");
					}
				});
	 	 	},
			"关闭":function(){
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
});
	function showDialog(did){
		$("#cid").val(did);
		$("#complainDialog").dialog("open");
	}
	function showContentDialog(content){
		$("#content_show").html(content);
		$("#contentDialog").dialog("open");
	}
	function showToDialog(){
		$("#toComplainDialog").dialog("open");
	}
	function chooseObject(id){
		if(id=="-1"){
			$("#object option").remove();
			$("#object").append("<option size='"+50+"' value=''>-请选择</option>");
		}else if(id=="0"){
		 $.getJSON("${path }/elevator/listByObject.do","rand="+Math.random(),function(a){
			 document.getElementById("object").innerHTML="";
			 $("#object").append("<option size='"+50+"' value=''>-请选择</option>");
			 for(var i=0;i<a.length;i++){
		    		$("#object").append("<option size='"+50+"' value='"+a[i].id_elevator+"'>"+a[i].desc+"</option>");
		    	}
		    });
		}else if(id=="1"){
		 $.getJSON("${path }/service/listByObject.do","rand="+Math.random(),function(a){
			 document.getElementById("object").innerHTML="";
			 $("#object").append("<option size='"+50+"' value=''>-请选择</option>");
			 for(var i=0;i<a.length;i++){
		    		$("#object").append("<option size='"+50+"' value='"+a[i].idservice+"'>"+a[i].name+"</option>");
		    	}
		    });
		}else if(id=="2"){
		 $.getJSON("${path }/user/list_json.do","rand="+Math.random(),function(a){
			 document.getElementById("object").innerHTML="";
			 $("#object").append("<option size='"+50+"' value=''>-请选择</option>");
			 for(var i=0;i<a.length;i++){
		    		$("#object").append("<option size='"+50+"' value='"+a[i].iduser+"'>"+a[i].name+"</option>");
		    	}
		    });
		}
	}
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
<form action="${path }/complain/selectList.do" method="get" id="form1">
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
		<span>检验检测单位:</span><select name="id_test" id="id_test">
		</select>
	</ul>
</div>
<br>
<div style="margin-top: 10px;">
	<ul>
		<li><span>投诉来源:</span><select name="source" id="source">
		</select>
		<span>投诉对象:</span><select name="type_object" id="type_object">
		</select>
	</ul>
</div>
<br>
<div>
	<ul>
		<li><span><select name="keyType" style="width: 80px;">
			<option value="1"
				<c:if test="${param.keyType==1 }">selected='selected'</c:if>
			>电梯简称</option>
			<option value="2"
				<c:if test="${param.keyType==2 }">selected='selected'</c:if>
			>电梯品牌</option>
		</select></span>
		<input type="text" id="desc" name="desc" size="33" maxlength="16" value="${desc }"/>
		<input type="submit" style="margin-left: 470px" value="&nbsp;&nbsp;搜索&nbsp;&nbsp;"/>
	</ul>
</div>

<hr style="margin-top: 10px">
</form>
<ul>
	<li>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th width="25%">投诉总数</th>
			<th width="25%">投诉电梯数</th>
			<th width="25%">投诉使用单位数</th>
			<th width="25%">投诉维保单位数</th>
		</tr>
		<tr>
			<td>${count}</td>
			<td>${countElevator}</td>
			<td>${countUse}</td>
			<td>${countService}</td>
		</tr>
	</table>
	<li>投诉信息列表

	<li>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>投诉编号</th>
			<th>投诉级别</th>
			<th>投诉类别</th>
			<th>投诉对象</th>
			<th>投诉来源</th>
			<th>投诉内容</th>
			<th>投诉时间 </th>
			<th>联系方式 </th>
			<th>操作</th>
		</tr>
		<c:forEach items="${list}" var="n">
		<tr>
			<td> ${n.cid}</td>
			<td>${n.complainLevel.level_name}</td>
			<td>${n.complainObject.type_name}</td>
			<td>${n.objectName}</td>
			<td>${n.complianSource.source_name}</td>
			<td><a href="javascript:showContentDialog('${n.content}')">${n.content}</a></td>
			<td>${n.date1}</td>
			<td>${n.contact}</td>
			<td>
			<c:if test="${n.status eq 0 }">
				<a href="javascript:showDialog(${n.cid})">投诉处理</a>&nbsp;&nbsp;&nbsp;
			</c:if>
			</td>
		</tr>
		</c:forEach>
		<tr>
		  <td colspan="14" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<li>
	<div style="text-align: right;">
		<input type="button" onclick="showToDialog()"  style="margin-left: 200px;width: 100px;" value="投诉录入"/>
	</div>
		
</ul>
<div id="complainDialog" style="display: none" title="投诉处理">
	<form action="${path }/complain/dealComplain.do" method="post" id="complainForm">
		<ul>
			<li>处理意见:
			<li><textarea rows="10" cols="45" name="result" id="result"></textarea><br>
			<li><input type="hidden" name="cid" id="cid" />
		</ul>
	</form>
</div>
<div id="contentDialog" style="display: none" title="投诉内容">
	<form action="${path }/complain/dealComplain.do" method="post" id="complainForm">
		<ul>
			<li><p id="content_show"></p>
		</ul>
	</form>
</div>
<div id="toComplainDialog" style="display: none" title="投诉录入" align="left">
	<form action="${path }/complain/insertComplain.do" method="post" id="complainInsertForm">
		<table width="100%" height="100%" id="table" cellspacing="0" align="left">
			<tr>
				<td style="text-align: left;">
					<ul>
						<li>被投诉对象类型:
						<li><select name="type_object" id="type_object1" onchange="chooseObject(this.value)"></select><br>
						<li>投诉级别:
						<li><select name="level" id="level" ></select><br>
						<li>投诉内容:
						<li><textarea rows="5" cols="40" name="content" id="content"></textarea><br>
					</ul>
				</td>
				<td style="text-align: left;">
					<ul>
						<li>被投诉对象:
						<li><select name="object" id="object"></select><br>
						<li>投诉来源:
						<li><select name="source" id="source1"></select><br>
						<!-- <li>被投诉对象编号: -->
						<input type="hidden" name="id_object" id="id_object" /><br>
						<li>联系方式:
						<li><input type="text" name="contact" id="contact"  size="38"/><br>
					</ul>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
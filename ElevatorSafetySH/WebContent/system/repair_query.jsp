<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
      <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>repair_query.jsp</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<link href="${path}/css/table.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="${path}/jquery/themes/base/jquery.ui.all.css">
 <style type="text/css">
a{
	text-decoration: underline;
	
}
/* span{
	font-size: 12px;
	font-weight: bold;
	padding-left: 50px;
} */
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
	
	$("#updateDialog").dialog({
		modal:true,
		autoOpen:false,
		width:400,
		height:300,
		buttons:{
			"确定":function(){
				var form = $("#updateForm");
				//=================检查表单中的字段是否为空 ===================================
				//判断文本框中的值是否为空
				var note=$("#note").val();
				var bool="";
				if(note==null||note==""){
					alert("请填写批复信息！");
					return;
				}else{
					var radio = document.getElementsByName("approveack");
				    for (i=0; i<radio.length; i++) {
				        if (radio[i].checked==true) {  
				        	bool=true;
				        	break;
				        }  
				    }
				    if(bool==""){
						alert("请选择是否同意！");
						return;
					}
					if(bool){
						//提交表单 
						$.post(form.attr('action'),form.serialize(),function(a){
							if(a=="ok"){
								location.reload();
							}else{
								alert("程序有点问题哟！");
							}
						});
					}
						
			   }
		  },
			"关闭":function(){
				$(this).dialog("close");
			}
		},
		close:function(){
			$(this).dialog("close");
		}
	});
	
	$("#detailDialog").dialog({
		modal:true,
		autoOpen:false,
		width:800,
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

	function form_submit(ck){
		if(ck){
			$("#form1").submit();
		}
	}
	function showUpdate(did){
		$("#rid").val(did);
		$("#note").val("");
		$("input[name='approveack']").attr("checked",false);
		$("#updateDialog").dialog("open");
	}
	function showDetail(did){
		//开始查询个人信息
		$.getJSON("${path }/repair_query/detail.do?rid="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			$("#rids").val(d.rid);
			$("#eids").val(d.eid);
			$("#suser1").val(d.service1.name);
			$("#suser2").val(d.service2.name);
			$("#ssafer").val(d.safer.name); 
			$("#upload").val(d.upload);
			$("#notes").val(d.note);
			if(d.repairapprove !=""&&d.repairapprove !=null){
				$("#approver_ack").val(d.repairapprove.approveType.approve_name);
				$("#approver").val(d.repairapprove.approver_name); 
				$("#notep").val(d.repairapprove.note);
				$("#notep2").val(d.repairapprove.note2);
				$("#approve_date").val(d.repairapprove.approve_date);
				if(d.repairapprove.approveack !=0&&d.repairapprove.approveack !=""){
					$("#pifu").attr("style", "display:block;");  
				}
				if(d.repairapprove.approveack ==3){
					$("#weixiu").attr("style", "display:block;");  
				}
			}
			if(d.repairmaint !=""&&d.repairmaint !=null){
				$("#wuser1").val(d.repairmaint.service1.name);
				$("#wuser2").val(d.repairmaint.service2.name);
				$("#wsafer").val(d.repairmaint.safer.name); 
				$("#repair_date").val(d.repairmaint.repairdate);
				$("#image").val(d.repairmaint.image);
				$("#notew").val(d.repairmaint.note);
				$.getJSON("${path}/repair_query/listImageById.do?rand="+Math.random()+"&rid="+d.rid,"",function(midlist){
					// $("#table1 tr:not(:first)").remove();
					//$("#table2 tr:not(:first)").remove();
					var tr1="<tr>";
					var tr2="<tr>";
					var a1=0;
					var a2=0;
					$("#table1").html("");
					$("#table2").html("");
					for(var i=0;i<midlist.length;i++){
						if(midlist[i].key.type==0){
							if(a1!=0&&a1%3==0){
								tr1+="</tr><tr>"
							}
							$("#sqtp").attr("style", "display:block;");  
							tr1+="<td><img src='http://120.27.196.90/pda_api.php?name=get_repair_image&p="+midlist[i].path+"&h=200&w=200' width=200 height=200 alt='图片不存在！'/>&nbsp;</td>";
							a1++;
						}
						if(midlist[i].key.type==1){
							if(a2!=0&&a2%3==0){
								tr2+="</tr><tr>"
							}
							$("#wxtp").attr("style", "display:block;"); 
							tr2+="<td><img src='http://120.27.196.90/pda_api.php?name=get_repair_image&p="+midlist[i].path+"&h=200&w=200' width=200 height=200 alt='图片不存在！'/>&nbsp;</td>";
							a2++;
						}
					}
					if(a1>3&&a1%3!=0){
						for(var j=0;j<3-a1%3;j++){
							tr1+="<td></td>"
						}
					}
					if(a2>3&&a2%3!=0){
						for(var j=0;j<3-a2%3;j++){
							tr2+="<td></td>"
						}
					}
					tr1+="</tr>";
					tr2+="</tr>";
					$("#table1").append(tr1);
					$("#table2").append(tr2); 
				});
			}
			$("#detailDialog").dialog("open");
		});
		
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
<form action="${path }/repair_query/list.do" method="get" id="form1">
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
<ul>
	<li><h3>维修信息列表</h3>
		<input type="radio" name="approve_ark" value="-1" onclick="form_submit(this.checked)"
			<c:if test="${approve_ark eq -1}">
				checked="checked"
			</c:if>
		/>全部&nbsp;&nbsp;${count }条
		<input type="radio" name="approve_ark" value="0" onclick="form_submit(this.checked)"
			<c:if test="${approve_ark eq 0}">
				checked="checked"
			</c:if>
		/>待批复&nbsp;&nbsp;${count_approve }条
		<input type="radio" name="approve_ark" value="1" onclick="form_submit(this.checked)"
			<c:if test="${approve_ark eq 1}">
				checked="checked"
			</c:if>
		/>已批复&nbsp;&nbsp;${count_approved }条
		<input type="radio" name="approve_ark" value="3" onclick="form_submit(this.checked)"
			<c:if test="${approve_ark eq 3}">
				checked="checked"
			</c:if>
		/>已维修&nbsp;&nbsp;${count_repaired }条
		</ul>
</form>
<ul>
	<li><table cellpadding="0" cellspacing="1">
		<tr>
			<th>维修编号</th>
			<th>电梯简称</th>
			<th>申报日期</th>
			<th>业务状态</th>
			<th>批复时间</th>
			<th>申请信息</th>
			<th>批复信息</th>
			<th>维修信息</th>
			 <c:if test="${approve_ark == 0}">
			  		 <th>操作</th>
			 </c:if>
			
		</tr>
		<c:forEach items="${list}" var="n">
		<tr>
			<td><c:choose>
			    <c:when test="${n.repairapprove.approveack != 4}">
			  		  <a href="javascript:showDetail(${n.rid})">${n.rid}</a>
			    </c:when>
			    <c:otherwise>
			   	 ${n.rid}
			    </c:otherwise>
				</c:choose>
			</td>
			<td>${n.elevator.desc }</td>
			<td>${fn:substring(n.upload,0,10) }</td>
			<td>
			<c:choose>
			    <c:when test="${empty n.repairapprove}">
			  		  待批复
			    </c:when>
			    <c:otherwise>
			        ${n.repairapprove.approveType.approve_name}
			    </c:otherwise>
			</c:choose></td>
			<td>${fn:substring(n.repairapprove.approve_date,0,10) }</td>
			<td>${n.note}</td>
			<td>${n.repairapprove.note }</td>
			<td>${n.repairmaint.note }</td>
			<c:choose>
			    <c:when test="${approve_ark == 0}">
			  		  <td><a href="javascript:showUpdate(${n.rid})">审批</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			    </c:when>
			    <c:otherwise>
			    </c:otherwise>
			</c:choose>
			
		</tr>
		</c:forEach>
		<tr>
		<c:choose>
		    <c:when test="${approve_ark == 0}">
		  		  <td colspan="9" style="text-align: left;">${pagination}</td>
		    </c:when>
		    <c:otherwise>
		     	<td colspan="8" style="text-align: left;">${pagination}</td>
		    </c:otherwise>
		</c:choose>
			
			
		</tr>
	</table>
</ul>


<div id="updateDialog" title="批复">
	<form action="${path }/repairApprove/update.do" method="post" id="updateForm">
		<ul>
			<li>批复说明:
			<li><textarea rows="5" cols="40" name="note" id="note"></textarea><br>
			<input type="radio" name="approveack"  value="1" />同意
			<input type="radio" name="approveack"  value="2" />不同意
			<input type="hidden" name="rid" id="rid" />
		</ul>
	</form>
</div>
<div id="detailDialog" title="详细信息">
	<div title="申请信息">
	<h3>申请信息：</h3>
	<table width="100%" height="100%" id="table" cellspacing="0">
		<tr> 
			<td style="text-align: left;vertical-align: top;">
		        <ul>
		            <li> 维修ID:
					<li><input type="text" id="rids" name="rids" readonly="readonly" size="30" />
		            <li> 电梯ID:
					<li><input type="text" id="eids" name="eids" readonly="readonly"  size="30" />
					<li> 维保人员1:
					<li><input type="text" id="suser1" name="user1" readonly="readonly" size="30" />
					<li> 申报内容:
					<li><textarea rows="5" cols="30" name="note" readonly="readonly" id="notes"></textarea>
                 </ul>
		     </td>
		     <td style="text-align: left;vertical-align: top;">
		        <ul>
		            <li> 维保人员2:
					<li><input type="text" id="suser2" name="user2"  readonly="readonly" size="30" /> 
					<li> 安全人员:
					<li><input type="text" id="ssafer" name="safer"  readonly="readonly" size="30" />
		        	<li> 申报时间:
					<li><input type="text" id="upload" name="upload" readonly="readonly" size="30" />
                 </ul>
		     </td>
		</tr>
	</table>
	</div>
	<div title="批复信息"style="display: none" id="pifu">
	<h3>批复信息：</h3> 
	<table width="100%" height="100%" id="table" cellspacing="0">
		<tr> 
			<td style="text-align: left;vertical-align: top;">
		        <ul>
		            <li> 批复状态:
					<li><input type="text" id="approver_ack" name="approver_ack" readonly="readonly" size="30" />
		            <li> 批复者:
					<li><input type="text" id="approver" name="approver"  readonly="readonly" size="30" />
					<li> 批复说明:
					<li><textarea rows="5" cols="30" name="note" readonly="readonly" id="notep"></textarea>
                 </ul>
		     </td>
		     <td style="text-align: left;vertical-align: top;">
		        <ul>
		        	<li> 批复时间:
					<li><input type="text" id="approve_date" readonly="readonly" name="approve_date"  size="30" />
					<!-- <li> 其他说明:
					<li><textarea rows="5" cols="30" name="note2" id="notep2"></textarea> -->
                 </ul>
		     </td>
		</tr>
	</table>
	</div>
	<div title="维修信息"style="display: none" id="weixiu">
	<h3>维修信息：</h3>
	<table width="100%" height="100%" id="table" cellspacing="0">
		<tr> 
			<td style="text-align: left;vertical-align: top;">
		        <ul>
		            <li> 维保人员1:
					<li><input type="text" id="wuser1" name="user1" readonly="readonly" size="30" />
		            <li> 维保人员2:
					<li><input type="text" id="wuser2" name="user2"  readonly="readonly" size="30" /> 
					<li> 安全人员:
					<li><input type="text" id="wsafer" name="safer"  readonly="readonly" size="30" />
					<li> 维修时间:
					<li><input type="text" id="repair_date" name="repair_date"  readonly="readonly" size="30" />
                 </ul>
		     </td>
		     <td style="text-align: left;vertical-align: top;">
		        <ul>
		        	<li> 图像个数:
					<li><input type="text" id="image" name="image" readonly="readonly" size="30" />
					<li> 维修说明:
					<li><textarea rows="5" cols="30" name="note" readonly="readonly" id="notew"></textarea>
                 </ul>
		     </td>
		</tr>
	</table>
	</div>
	<div title="申请照片"style="display: none" id="sqtp">
	<h3>申请照片：</h3>
	<table width="100%" height="100%" id="table1" cellspacing="0">
	</table>
	</div>
	<div title="维修照片"style="display: none" id="wxtp">
	<h3>维修照片：</h3>
	<table width="100%" height="100%" id="table2" cellspacing="0">
	</table>
	</div>
</div>

</body>
</html>
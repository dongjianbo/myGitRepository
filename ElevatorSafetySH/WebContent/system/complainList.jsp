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
	$.getJSON("${path}/complainObject/list_json.do","rand="+Math.random(),function(d){
		$("#type_object").append("<option size='"+15+"' value='-1'>-请选择</option>");
		$("#type_object1").append("<option size='"+15+"' value='-1'>-请选择</option>");
    	   for(var i=0;i<d.length;i++){
    		  $("#type_object").append("<option size='"+15+"' value='"+d[i].id_type+"'>"+d[i].type_name+"</option>");
    		  $("#type_object1").append("<option size='"+15+"' value='"+d[i].id_type+"'>"+d[i].type_name+"</option>");
    	  }
    	  $("#type_object option").each(function(){
				if($(this).val()=="${param.id_type}"){
					$(this).attr("selected",true);
				}
			}); 
    	  $("#type_object1 option").each(function(){
				if($(this).val()=="${param.id_type}"){
					$(this).attr("selected",true);
				}
			}); 
       });
	
		$("#object").change(function(){
			var type_object=$("#type_object1").val();
			var object=$(this).val();
			if(object!=-1&&type_object!=-1){
				$.getJSON("${path}/complain/getObjectByType.do?type="+type_object+"&object="+object,null,function(e){
					$("#id_object").val(e.id_object);
					$("#contact").val(e.contact);
					
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
			if($(this).val()=="${param.id_source}"){
				$(this).attr("selected",true);
			}
		}); 
	   	$("#source1 option").each(function(){
			if($(this).val()=="${param.id_source}"){
				$(this).attr("selected",true);
			}
		}); 
      });
	$.getJSON("${path}/dealStatus/list_json.do","rand="+Math.random(),function(d){
	$("#status").append("<option size='"+15+"' value='-1'>-请选择</option>");
   	   for(var i=0;i<d.length;i++){
   		  $("#status").append("<option size='"+15+"' value='"+d[i].id_deal+"'>"+d[i].deal_name+"</option>");
   	  }
   	  $("#status option").each(function(){
			if($(this).val()=="${param.id_deal}"){
				$(this).attr("selected",true);
			}
		}); 

    });
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
	$("#toComplainDialog").dialog({
		modal:true,
		autoOpen:false,
		width:680,
		height:400,
		buttons:{
			"确定":function(){
				var form = $("#complainInsertForm");
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
});
	function showDialog(did){
		$("#cid").val(did);
		$("#complainDialog").dialog("open");
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
</script>
</head>
<body>
<form action="${path }/complain/selectList.do" method="get" id="form1">
<div style="margin-top: 10px;">
	<ul>
			<li>
			<span>被投诉对象类型:</span><select name="type_object" id="type_object">
			</select>
			<span>投诉来源:</span><select name="source" id="source">
			</select> 
			<span>处理状态:</span><select id="status" name="status">
			</select>
	</ul>
</div>
<br>
<div>
	<ul>
		<input type="submit" style="margin-left: 650px" value="&nbsp;&nbsp;搜索&nbsp;&nbsp;"/>
	</ul>
</div>

<hr style="margin-top: 10px">
</form>
<ul>
	<li><table cellpadding="0" cellspacing="1">
		<tr>
			<th>投诉编号</th>
			<th>被投诉对象类型</th>
			<th>被投诉对象顺序号</th>
			<th>投诉级别</th>
			<th>投诉来源</th>
			<th>联系方式 </th>
			<th>投诉内容</th>
			<th>处理状态</th>
			<th>处理结果说明</th>
			<th>投诉录入人员姓名</th>
			<th>投诉录入时间 </th>
			<th>处理录入人员姓名</th>
			<th>处理录入时间</th>
			<th>操作</th>
			
		</tr>
		<c:forEach items="${list}" var="n">
		<tr>
			<td> ${n.cid}</td>
			<td>${n.complainObject.type_name}</td>
			<td>${n.id_object}</td>
			<td>${n.complainLevel.level_name}</td>
			<td>${n.complainSource.source_name}</td>
			<td>${n.contact}</td>
			<td>${n.content}</td>
			<td>${n.dealStatus.deal_name}</td>
			<td>${n.result}</td>
			<td>${n.input1}</td>
			<td>${n.date1}</td>
			<td>${n.iput2}</td>
			<td>${n.date2}</td>
			<td>
			<c:if test="${n.status eq 0 }">
				<a href="javascript:showDialog(${n.cid})">投诉处理</a>&nbsp;&nbsp;&nbsp;
			</c:if>
			</td>
		</tr>
		</c:forEach>
		<tr>
		  <td colspan="14" style="text-align: right;"><input type="button" onclick="showToDialog()"  style="margin-left: 200px;width: 100px;" value="投诉录入"/></td>
		</tr>
		<tr>
		  <td colspan="14" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
</ul>
<div id="complainDialog" style="display: none" title="投诉处理">
	<form action="${path }/complain/dealComplain.do" method="post" id="complainForm">
		<ul>
			<li>处理意见:
			<li><textarea rows="5" cols="30" name="result" id="result"></textarea><br>
			<li><input type="hidden" name="cid" id="cid" />
		</ul>
	</form>
</div>
<div id="toComplainDialog" style="display: none" title="投诉录入">
	<form action="${path }/complain/insertComplain.do" method="post" id="complainInsertForm">
		<table width="100%" height="100%" id="table" cellspacing="0">
			<tr>
				<td>
					<ul>
						<li>被投诉对象类型:
						<li><select name="type_object" id="type_object1" onchange="chooseObject(this.value)"></select><br>
						
						<li>投诉级别:
						<li><select name="level" id="level"></select><br>
						<li>投诉内容:
						<li><textarea rows="5" cols="30" name="result" id="result"></textarea><br>
					</ul>
				</td>
				<td>
					<ul>
						<li>被投诉对象:
						<li><select name="object" id="object"></select><br>
						<li>投诉来源:
						<li><select name="source" id="source1"></select><br>
						<li>被投诉对象顺序号:
						<li><input type="text" name="id_object" id="id_object" readonly/><br>
						<li>联系方式:
						<li><input type="text" name="contact" id="contact" /><br>
					</ul>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
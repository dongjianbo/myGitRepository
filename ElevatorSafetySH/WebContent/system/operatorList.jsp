<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<script type="text/javascript">
	var dia;
	$().ready(function(){
		//查询城市id
		$.getJSON("${path }/citylist/list.do","rand="+Math.random(),function(d){
			$("#idcity1").append("<option size='"+50+"' value=''>-请选择</option>");
			//将查询到的信息放入表单
			for(var i=0;i<d.length;i++){
				if(d[i].id_city!="99"){
				  $("#idcity").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
				  $("#idcity1").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
				}
			}
			$("#iddistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
			$("#idsubdistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
		});
		$("#insertDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:500,
			buttons:{
				"确定":function(fn){
					var form = $("#insertForm");
					//=================检查表单中的字段是否为空 ===================================
					//判断文本框中的值是否为空
				var name1=$("#name1").val();
				if(name1==null||name1==""){
					$("#name2").html("<i>姓名不能为空 !!!</i>");
				}else{
					$("#name2").html("");
					var loginname1=$("#loginname1").val();
				    if(loginname1==null||loginname1==""){
					 $("#loginname2").html("<i>登陆名不能为空 !!!</i>");
					}else{
						$("#loginname2").html("");
						var password1=$("#password1").val();
						if(password1==null||password1==""){
							$("#password2").html("<i>密码不能为空 !!!</i>");
						}else{
							$("#password2").html("");
							
							   $("#idOrganization2").html("");
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
		// 查询所有的 操作员类别 (下拉框的值 )
		  $.getJSON("${path }/operator_type_def/list_json.do","rand="+Math.random(),function(d){
			  //将查询到的信息放入修改表单中--注意隐藏域中的主键
			for(var i=0;i<d.length;i++){
				//放入修改 界面的 类别下拉框中（插入界面有默认 插入的值所以不用放 ）
				//alert("操作人员类别："+d[i].name);
				$("#typeOperator").append("<option size='"+50+"' value='"+d[i].id_operator_type+"'>"+d[i].name+"</option>");
				
			}
		}); 
		//查询维保人员状态（下拉框 的值 ）
			$.getJSON("${path }/status_def/list_json.do","rand="+Math.random(),function(d){
					  //将查询到的信息放入修改表单中--注意隐藏域中的主键
					for(var i=0;i<d.length;i++){
						//alert("维保人员状态："+d[i].name);
						$("#status").append("<option size='"+50+"' value='"+d[i].idstatus+"'>"+d[i].name+"</option>");
						$("#status1").append("<option size='"+50+"' value='"+d[i].idstatus+"'>"+d[i].name+"</option>");
					}
				}); 
		//查询 操作员角色 （下拉框的值 ）
			$.getJSON("${path }/role/list_json.do","rand="+Math.random(),function(d){
					  //将查询到的信息放入修改表单中--注意隐藏域中的主键
					for(var i=0;i<d.length;i++){
						//alert("维保人员角色 ："+d[i].name);
						$("#idprivilege").append("<option size='"+50+"' value='"+d[i].idrole+"'>"+d[i].name_role+"</option>");
						$("#idprivilege1").append("<option size='"+50+"' value='"+d[i].idrole+"'>"+d[i].name_role+"</option>");
					}
					
				}); 
			
		$("#updateDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:500,
			buttons:{
				"确定":function(){
					var form = $("#updateForm");
					//=================检查表单中的字段是否为空 ===================================
					//判断文本框中的值是否为空
				var name=$("#name").val();
				if(name==null||name==""){
					$("#name0").html("<i>姓名不能为空 !!!</i>");
				}else{
					$("#name0").html("");
					var loginname=$("#loginname").val();
				    if(loginname==null||loginname==""){
					 $("#loginname0").html("<i>登陆名不能为空 !!!</i>");
					}else{
						$("#loginname0").html("");
						var password=$("#password").val();
						if(password==null||password==""){
							$("#password0").html("<i>密码不能为空 !!!</i>");
						}else{
							$("#password0").html("");
							
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
	});
	
	
	//选择城市-----------------------------------------------
	function chooseCity(id_city){
		//省直的话就不用级联
		if(id_city=="00"){
			$("#iddistrict option").remove();
			$("#idsubdistrict option").remove();
			$("#iddistrict").attr("disabled",true);
			$("#idsubdistrict").attr("disabled",true);
		}else{
			$("#iddistrict").attr("disabled",false);
			//不同的城市选择不同的id
			$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
				document.getElementById("iddistrict").innerHTML="";
				$("#iddistrict").append("<option size='"+50+"' value=''>-请选择</option>");
				for(var i=0;i<s.length;i++){
					 $("#iddistrict").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
				}
				$("#idsubdistrict option").remove();
				$("#idsubdistrict").attr("disabled",true);
				
			});
		}
		
	}
	function chooseCity1(id_city){
		if(id_city=="00"){
			$("#iddistrict1 option").remove();
			$("#idsubdistrict1 option").remove();
			$("#iddistrict1").attr("disabled",true);
			$("#idsubdistrict1").attr("disabled",true);
		}else{
			$("#iddistrict1").attr("disabled",false);
			//不同的城市选择不同的id
			$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
				document.getElementById("iddistrict1").innerHTML="";
				$("#iddistrict1").append("<option size='"+50+"' value=''>-请选择</option>");
				for(var i=0;i<s.length;i++){
					 $("#iddistrict1").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
					}
				$("#idsubdistrict1 option").remove();
				$("#idsubdistrict1").attr("disabled",true);
			});
		}
	}
	//选择区域来获取乡镇-------------------------------------
	function choosedistrict(id_district){
		//如果选择市直
		if(id_district=="00"){
			$("#idsubdistrict option").remove();
			$("#idsubdistrict").attr("disabled",true);
		}else{
			$("#idsubdistrict").attr("disabled",false);
		//去城市的id
		var id_city=document.getElementById("idcity").value;
		 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
			 document.getElementById("idsubdistrict").innerHTML="";
			 $("#idsubdistrict").append("<option size='"+50+"' value=''>-请选择</option>");
			 for(var i=0;i<a.length;i++){
		    		$("#idsubdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
		    	}
		    });
		}
	}
	function choosedistrict1(id_district){
		//如果选择市直
		if(id_district=="00"){
			$("#idsubdistrict1 option").remove();
			$("#idsubdistrict1").attr("disabled",true);
		}else{
			$("#idsubdistrict1").attr("disabled",false);
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
	function showInsert(){
		//查询所有的 维保公司的名字 
// 		dia.dialog("open");
		$("#insertDialog").dialog("open");
	}
	function showUpdate(did){
		//将所有的城市放入第一个下拉框
		$("#idcity option").remove();
		//查询城市id
		$.getJSON("${path }/citylist/list.do","rand="+Math.random(),function(d){
			//将查询到的信息放入表单
			for(var i=0;i<d.length;i++){
				if(d[i].id_city!="99"){
				  $("#idcity").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
				}
			}
			//开始查询个人信息
			$.getJSON("${path }/operator/toUpdate.do?idoperator="+did,"rand="+Math.random(),function(d){
				//将查询到的信息放入修改表单中--注意隐藏域中的主键
				$("#typeOperator").val(d.typeOperator);
				$("#name").val(d.name);
				$("#idcard").val(d.idcard);
				$("#idcity").val(d.idcity);
				$("#loginname").val(d.loginname);
				$("#password").val(d.password);
				$("#idOrganization").val(d.idOrganization);
				$("#idprivilege").val(d.idprivilege);
				$("#status").val(d.status);
				$("#idoperator").val(d.idoperator);
				//如果是省直
				if(d.idcity=="00"){
					
					$("#iddistrict option").remove();
					$("#iddistrict").attr("disabled",true);
					$("#idsubdistrict option").remove();
					$("#idsubdistrict").attr("disabled",true);
				}else{
					//将这个城市下所有的县区放入第二个下拉框
					$("#iddistrict option").remove();
					$("#iddistrict").attr("disabled",false);
					//不同的城市选择不同的id
					$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+d.idcity,"rand="+Math.random(),function(s){
						for(var i=0;i<s.length;i++){
							 $("#iddistrict").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
						}
						$("#iddistrict").val(d.iddistrict);
						//如果是市直
						if(d.iddistrict=="00"){
							$("#idsubdistrict option").remove();
							$("#idsubdistrict").attr("disabled",true);
						}else{
							//将这个县区下所有的乡镇放入第三个下拉框
							$("#idsubdistrict option").remove();
							$("#idsubdistrict").attr("disabled",false);
							$.getJSON("${path }/subdistictlist/listById.do?id_city="+d.idcity+"&id_distrct="+d.iddistrict,"rand="+Math.random(),function(a){
								for(var i=0;i<a.length;i++){
						    		$("#idsubdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
						    	}
								$("#idsubdistrict").val(d.idsubdistrict);
							 });
							
						}
						
					});
					
				}
				//打开修改对话框
				$("#updateDialog").dialog("open");
				
			});
		});
		
	}
	//启用禁用
	function updateStatus(did,status){
		if(status==1){
			$.post("${path }/operator/UpdateStatus.do?idoperator="+did,"rand="+Math.random(),function(d){
				if(d=="ok"){
					location.reload();
				}
			});
		}
		if(status==0){
			$.post("${path }/operator/UpdateStatus1.do?idoperator="+did,"rand="+Math.random(),function(d){
				if(d=="ok"){
					location.reload();
				}
			});
		}
	}
</script>
</head>
<body>
	<form action="${path}/operator/list.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 姓名 <input type="text" name="key" size="50"
					value="${param.key}" /> <input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>编号</th>
			<th>操作员类别</th>
			<th>操作员姓名</th>
			<th>所属城市</th>
			<th>所属区县</th>
			<th>所属乡镇街道</th>
			<th>角色</th>
			<th>操作员状态</th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${operatorList}" var="d">
			<tr>
				<td>${d.idoperator}</td>
				<td>${d.operator_type_def.name}</td>
				<td>${d.name}</td>
				<td>${d.city.name_city }</td>
				<td>${d.distict.name_district }</td>
				<td>${d.subdistict.name_subdistrict}</td>
				<td>${d.role.name_role}</td>
				<td>${d.status_def.name}</td>
				
				<td><a
					href="javascript:showUpdate(${d.idoperator})">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
<%-- 					<a href="${path }/operator/delete.do?idoperator=${d.idoperator}">删除</a>&nbsp;&nbsp;&nbsp;&nbsp; --%>
					<a href="javascript:updateStatus(${d.idoperator},${d.status })">启用/禁用</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="9" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div align="right">
		<input type="button" value="添加一个监督人员" onclick="showInsert()" />
	</div>
	
 <div id="insertDialog" style="display: none" title="添加">
		<form action="${path }/operator/insert.do" method="post" id="insertForm">
			<ul>
			    <li>操作人员类别
				<li><select name="typeOperator">
					<option value="00">技术监督管理人员</option>
					<option value="01">技术监督业务人员</option>
				</select>
				<li>操作员姓名:
				<li><input type="text" name="name" id="name1" maxlength="20" size="50"/>*<div id="name2" style="float: right; margin-right:220 " ></div>
				<li>身份证号码:
				<li><input type="text" name="idcard" id="idcard1" maxlength="18" size="50"/>
				<li>所属城市:
				<li><select name="idcity" id="idcity1" onchange="chooseCity1(this.value)">
				    
				    </select>
				<li>所属区县:
				<li><select name="iddistrict" id="iddistrict1" onchange="choosedistrict1(this.value)">
				</select> 
				<li>所属乡镇街道:
				<li><select id="idsubdistrict1" name="idsubdistrict">
				</select>
				<li>登录名:
				<li><input type="text" name="loginname" id="loginname1" maxlength="20" size="50" />*<div id="loginname2" style="float: right; margin-right:220 " ></div>
				<li>密码:
				<li><input type="password" name="password" id="password1" maxlength="32" size="50"/>*<div id="password2" style="float: right; margin-right:220 " ></div>
<!-- 				<li>所属单位顺序号: -->
<!-- 				<li><input type="text" name="idOrganization" id="idOrganization1" maxlength="11" size="50"/>*<div id="idOrganization2" style="float: right; margin-right:220 " ></div> -->
				<li>角色:
				<li><select name="idprivilege" id="idprivilege1"></select>
				<li><input type="hidden" name="status" id="status1" value="1"/>
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/operator/update.do" method="post" id="updateForm">
			<ul>
				<li>操作人员类别
				<li><select name="typeOperator" id="typeOperator">
				</select>
				<li>操作员姓名:
				<li><input type="text" name="name" id="name" maxlength="20" size="50"/>*<div id="name0" style="float: right; margin-right:220 " ></div>
				<li>身份证号码:
				<li><input type="text" name="idcard" id="idcard" maxlength="18" size="50"/>
				<li>所属城市:
				<li><select name="idcity" id="idcity" onchange="chooseCity(this.value)">
				   
				</select>
				<li>所属区县:
				<li><select name="iddistrict" id="iddistrict" onchange="choosedistrict(this.value)">
				</select> 
				<li>所属乡镇街道:
				<li><select id="idsubdistrict" name="idsubdistrict">
				</select>
				<li>登录名:
				<li><input type="text" name="loginname" id="loginname" maxlength="20" size="50"/>*<div id="loginname0" style="float: right; margin-right:220 " ></div>
				<li><input type="hidden" name="password" id="password" maxlength="32" size="50"/><div id="password0" style="float: right; margin-right:220 " ></div>
				<input type="hidden" name="idOrganization" id="idOrganization" maxlength="11" size="50"/>
				<li>角色:
				<li><select name="idprivilege" id="idprivilege"></select>
				<li><input type="hidden" name="status" id="status">
				
			    <li><input type="hidden" name="idoperator" id="idoperator" />
			</ul>
		</form>
	</div>

	
</body>
</html>
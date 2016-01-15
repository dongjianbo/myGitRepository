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
			//将查询到的信息放入表单
			for(var i=0;i<d.length;i++){
			  $("#idcity").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
			  $("#idcity1").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
			}
			//根据第一个城市的id查区域
			$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+d[0].id_city,"rand="+Math.random(),function(s){
				for(var i=0;i<s.length;i++){
					  $("#iddistrict").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
					  $("#iddistrict1").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
					}
			 //根据第一个区域查询乡镇
			    $.getJSON("${path }/subdistictlist/listById.do?id_city="+d[0].id_city+"&id_distrct="+s[0].id_district,"rand="+Math.random(),function(a){
			    	for(var i=0;i<a.length;i++){
			    		$("#idsubdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
			    		$("#idsubdistrict1").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
			    	}
			    });
			});
		});
		//----------------------------------
		$("#insertDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:450,
			buttons:{
				"确定":function(fn){
					var danweiname=$("#danweiname").val();
					if(danweiname==null||danweiname==""){
						$("#message").html("<i>单位名称不能为空 !!!</i>");
					}else{
						$("#message").html("");
					var form = $("#insertForm");
					$.post(form.attr('action'),form.serialize(),function(a){
						if(a=="ok"){
							$("#insertDialog").dialog("close");
							$("#insertweibaoDialog").dialog("open");
						}else{
							alert("程序有点问题哟！");
						}
					});
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
		$("#updateDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:450,
			buttons:{
				"确定":function(){
					var danweiname=$("#name").val();
					if(danweiname==null||danweiname==""){
						$("#message1").html("<i>单位名称不能为空 !!!</i>");
					}else{
						$("#message1").html("");
					var form = $("#updateForm");
					$.post(form.attr('action'),form.serialize(),function(a){
						if(a=="ok"){
							location.reload();
						}else{
							alert("程序有点问题哟！");
						}
					});
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
		//添加维保单位人员信息
		$("#insertweibaoDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:450,
			buttons:{
				"确定":function(){
					var name=$("#insertname").val();
					var loginname=$("#loginname").val();
					var password=$("#password").val();
					var status=$("#status").val();
					if(name==null||name==""){
						$("#message2").html("<i>姓名不能为空!!!</i>");
					}else{
						$("#message2").html("");
						if(loginname==null||loginname==""){
							$("#message3").html("<i>登录名不能为空</i>");
						}else{
							$("#message3").html("");
							if(password==null||password==""){
								$("#message4").html("<i>密码不能为空</i>");
							}else{
								$("#message4").html("");
								if(status==null||status==""){
									$("#message5").html("<i>状态不能为空</i>");
								}else{
									$("#message5").html("");
									var form = $("#insertweibaoForm");
									$.post(form.attr('action'),form.serialize(),function(a){
										if(a=="ok"){
											//location.reload();
											//插入业务员信息
											$("#insertweibaoDialog").dialog("close");
											$("#insertyewuDialog").dialog("open");
										}else{
											alert("程序有点问题哟！");
										}
									});
								}
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
		//添加业务人员信息
		$("#insertyewuDialog").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:450,
			buttons:{
				"确定":function(){
					var name=$("#insert1name").val();
					var loginname=$("#loginname1").val();
					var password=$("#password1").val();
					var status=$("#status1").val();
					if(name==null||name==""){
						$("#message6").html("<i>姓名不能为空!!!</i>");
					}else{
						$("#message6").html("");
						if(loginname==null||loginname==""){
							$("#message7").html("<i>登录名不能为空</i>");
						}else{
							$("#message7").html("");
							if(password==null||password==""){
								$("#message8").html("<i>密码不能为空</i>");
							}else{
								$("#message8").html("");
								if(status==null||status==""){
									$("#message9").html("<i>状态不能为空</i>");
								}else{
									$("#message9").html("");
									var form = $("#insertyewuForm");
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
		//不同的城市选择不同的id
		$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
			document.getElementById("iddistrict").innerHTML="";
			for(var i=0;i<s.length;i++){
				 $("#iddistrict").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
				}
		//选择区域下面的像乡镇
			 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+s[0].id_district,"rand="+Math.random(),function(a){
				 document.getElementById("idsubdistrict").innerHTML="";	
				 for(var i=0;i<a.length;i++){
						$("#idsubdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
			    	}
			    });
		});
	}
	function chooseCity1(id_city){
		//不同的城市选择不同的id
		$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
			document.getElementById("iddistrict1").innerHTML="";
			for(var i=0;i<s.length;i++){
				 $("#iddistrict1").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
				}
		//选择区域下面的像乡镇
			 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+s[0].id_district,"rand="+Math.random(),function(a){
				 document.getElementById("idsubdistrict1").innerHTML="";	
				 for(var i=0;i<a.length;i++){
						$("#idsubdistrict1").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
			    	}
			    });
		});
	}
	//选择区域来获取乡镇-------------------------------------
	function choosedistrict(id_district){
		//去城市的id
		var id_city=document.getElementById("idcity").value;
		 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
			 document.getElementById("idsubdistrict").innerHTML="";	
			 for(var i=0;i<a.length;i++){
		    		$("#idsubdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
		    	}
		    });
	}
	function choosedistrict1(id_district){
		//去城市的id
		var id_city=document.getElementById("idcity1").value;
		 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
			 document.getElementById("idsubdistrict1").innerHTML="";	
			 for(var i=0;i<a.length;i++){
		    		$("#idsubdistrict1").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
		    	}
		    });
	}
	function showInsert(){
// 		dia.dialog("open");
		$("#insertDialog").dialog("open");
	}
	function showUpdate(did){
		$.getJSON("${path }/test/toUpdate.do?idtest="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			$("#code").val(d.code);
			$("#name").val(d.name);
			$("#licencecode").val(d.licencecode);
			$("#licename").val(d.licename);
			$("#telephone").val(d.telephone);
			$("#manager").val(d.manager);
			$("#addr").val(d.addr);
			$("#registerArea").val(d.registerArea);
			$("#idtest").val(d.idtest);
			//打开修改对话框
			$("#updateDialog").dialog("open");
		});
	}
</script>
</head>
<body>
	<form action="${path}/test/list.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 单位名称/代码 <input type="text" name="key" size="50"
					value="${param.key}" /> <input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>编号</th>
			<th>检验检测单位代码</th>
			<th>单位名称</th>
			<th>检验检测许可证编号</th>
			<th>检验检测许可证</th>
			<th>检验检测负责人</th>
			<th>联系电话</th>
			<th>单位地址</th>
			<th>注册区域</th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${testList}" var="d">
			<tr>
				<td style="text-align: right">${d.idtest}</td>
				<td style="text-align: left">${d.code }</td>
				<td style="text-align: left">${d.name}</td>
				<td style="text-align: left">${d.licencecode }</td>
				<td style="text-align: left">${d.licename }</td>
				<td style="text-align: left">${d.manager }</td>
				<td style="text-align: left">${d.telephone }</td>
				<td style="text-align: left">${d.addr }</td>
				<td style="text-align: left">${d.registerArea }</td>
				<td><a
					href="javascript:showUpdate(${d.idtest})">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="${path }/test/delete.do?idtest=${d.idtest}">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="10" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div align="right">
		<input type="button" value="添加一个设计单位"
			onclick="showInsert()" />
	</div>
	<div id="insertDialog" style="display: none" title="添加">
		<form action="${path }/test/insert.do" method="post" id="insertForm">
			<ul>
				<li>检验检测单位代码:
				<li><input type="text" name="code" size="50" maxlength="9"/>
				<li>单位名称:
				<li><input type="text" name="name" id="danweiname" size="50"/>*<div id="message" style="float: right;padding-right:220px;"></div>
				<li>检验检测许可证编号:
				<li><input type="text" name="licencecode" size="50"/>
				<li>检验检测许可证:
				<li><input type="text" name="licename" size="50"/>
				<li>检验检测负责人:
				<li><input type="text" name="manager" size="50"/>
				<li>联系电话:
				<li><input type="text" name="telephone" size="50"/>
				<li>单位地址:
				<li><input type="text" name="addr" size="100"/>
				<li>注册区域:
				<li><input type="text" name="registerArea" size="100" maxlength="2"/>
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/test/update.do" method="post" id="updateForm">
			<ul>
				<li>检验检测单位代码:
				<li><input type="text" id="code" name="code" size="50" maxlength="9"/>
				<li>单位名称:
				<li><input type="text" id="name" name="name" size="50"/>*<div id="message1" style="float: right;padding-right:220px;"></div>
				<li>检验检测许可证编号:
				<li><input type="text" id="licencecode" name="licencecode" size="50"/>
				<li>检验检测许可证:
				<li><input type="text" id="licename" name="licename" size="50"/>
				<li>检验检测负责人:
				<li><input type="text" id="manager" name="manager" size="50"/>
				<li>联系电话:
				<li><input type="text" id="telephone" name="telephone" size="50"/>
				<li>单位地址:
				<li><input type="text" id="addr" name="addr" size="100"/>
				<li>注册区域:
				<li><input type="text" id="registerArea" name="registerArea" size="100" maxlength="2"/>
				<li><input type="hidden" id="idtest" name="idtest"/>
			</ul>
		</form>
	</div>
	<!--  添加维保管理员-->
	<div id="insertweibaoDialog" style="display: none" title="添加维保管理员">
		<form action="${path }/operator/insert1.do" method="post" id="insertweibaoForm">
			<ul>
				<li>姓名:
				<li><input type="text" id="insertname" name="name" size="50"/>*<div id="message2" style="float: right;padding-right:220px;"></div>
				<li>身份证号码:
				<li><input type="text" id="idcard" name="idcard" size="50"/>
				<li>城市:
				<li><select name="idcity" id="idcity" onchange="chooseCity(this.value)">
				</select> 
				<li>区:
				<li><select name="iddistrict" id="iddistrict" onchange="choosedistrict(this.value)"></select> 
				<li>街道:
				<li><select id="idsubdistrict" name="idsubdistrict"></select>
				<li>登录名:
				<li><input type="text" id="loginname" name="loginname" size="50"/>*<div id="message3" style="float: right;padding-right:220px;"></div>
				<li>密码:
				<li><input type="text" id="password" name="password" size="50" maxlength="8"/>*<div id="message4" style="float: right;padding-right:230px;"></div>
				<input type="hidden" name="status" value="1">
				<input type="hidden" name="typeOperator" value="30">
				<input type="hidden" name="idprivilege" value="1">
				 <input type="hidden" name="idOrganization" value="${idtest }">
			</ul>
		</form>
	</div>
	<!--  添加业务管理员-->
	<div id="insertyewuDialog" style="display: none" title="添加业务管理员">
		<form action="${path }/operator/insert1.do" method="post" id="insertyewuForm">
			<ul>
				<li>姓名:
				<li><input type="text" id="insert1name" name="name" size="50"/>*<div id="message6" style="float: right;padding-right:220px;"></div>
				<li>身份证号码:
				<li><input type="text" id="idcard" name="idcard" size="50"/>
				<li>城市:
				<li><select name="idcity" id="idcity1" onchange="chooseCity1(this.value)">
				</select> 
				<li>区:
				<li><select name="iddistrict" id="iddistrict1" onchange="choosedistrict1(this.value)"></select> 
				<li>街道:
				<li><select id="idsubdistrict1" name="idsubdistrict"></select>
				<li>登录名:
				<li><input type="text" id="loginname1" name="loginname" size="50"/>*<div id="message7" style="float: right;padding-right:220px;"></div>
				<li>密码:
				<li><input type="text" id="password1" name="password" size="50"/>*<div id="message8" style="float: right;padding-right:220px;"></div>
				<input type="hidden" name="status" value="1">
				<input type="hidden" name="typeOperator" value="31">
				<input type="hidden" name="idprivilege" value="2">
				 <input type="hidden" name="idOrganization" value="${idtest }">
			</ul>
		</form>
	</div>
</body>

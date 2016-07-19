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
	function getCard(){
		//获取用户id
		var idservicer=$("#idservicer_card").val();
		var a=card.WriteUserData(3,idservicer,0)
		var b=card.serial;
		//讲序列号写入文本框
		$("#idMifare_card").val(b);
		$("#cardmessage").html("发卡成功");
	}
	//新版写卡程序
	function icw() {

		var utype = 3;
		var uid = $("#idservicer_card").val();

		if ((utype =="") || uid=="") {
			$("#binfo").html("数据填写错误");
			return;
		}

		$("#cardmessage").html("正在写...");

		$.get("http://127.0.0.1:4050", {
			action : "write",
			utype : utype,
			uid : uid
		}, function(result,r,x) {

			if (result == "FAILED") {
				$("#cardmessage").html("写卡失败");
			} else {
				//讲序列号写入文本框
				$("#idMifare_card").val(result);
				$("#cardmessage").html("写卡成功，卡ID=" + result);
			}
		});
	}
	var dia;
	$().ready(function(){
// 		var $doc;         
// 		if(window.location!=window.top.location){          
// 			// <SPAN style="COLOR: #ff0000">页面在iframe中</SPAN>            
// 			$doc=window.top.jQuery.noConflict();
// 		}else{
// 			// <SPAN style="COLOR: #ff0000">页面单独打开</SPAN>
// 			$doc=jQuery.noConflict();
// 		}         
// 		$doc("body").append('<div id="dialog"  style="display: none" title="添加"></div>');
// 		dia=$doc("#dialog");
// 		dia.html($("#insertDialog").html());
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
					var idcard1=$("#idcard1").val();
				    if(idcard1==null||idcard1==""){
					 $("#idcard2").html("<i>身份证号码不能为空 !!!</i>");
					}else{
						$("#idcard2").html("");
						var licencecode1=$("#licencecode1").val();
						if(licencecode1==null||licencecode1==""){
							$("#licencecode2").html("<i>从业资格证书编号不能为空 !!!</i>");
						}else{
							$("#licencecode2").html("");
				           
								//提交表单 
							$.post(form.attr('action'),form.serialize(),function(a){
								$("#idservicer_card").val(a);
								$("#insertDialog").dialog("close");
								$("#getCard").dialog("open");
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
		// 查询所有的 维保公司的名字 (下拉框的值 )
		  $.getJSON("${path }/service/list_json.do","rand="+Math.random(),function(d){
			  //将查询到的信息放入修改表单中--注意隐藏域中的主键
			for(var i=0;i<d.length;i++){
				$("#idservice").append("<option size='"+50+"' value='"+d[i].idservice+"'>"+d[i].name+"</option>");
				$("#idservice1").append("<option size='"+50+"' value='"+d[i].idservice+"'>"+d[i].name+"</option>");
			}
		}); 
		//查询维保人员类别（下拉框的值 ）
			$.getJSON("${path }/servicer_type_def/list_json.do","rand="+Math.random(),function(d){
				  //将查询到的信息放入修改表单中--注意隐藏域中的主键
				for(var i=0;i<d.length;i++){
					$("#type").append("<option size='"+50+"' value='"+d[i].idservicertype+"'>"+d[i].name+"</option>");
					$("#type1").append("<option size='"+50+"' value='"+d[i].idservicertype+"'>"+d[i].name+"</option>");
				}
			}); 
		//查询维保人员状态（下拉框 的值 ）
		$.getJSON("${path }/status_def/list_json.do","rand="+Math.random(),function(d){
				  //将查询到的信息放入修改表单中--注意隐藏域中的主键
				for(var i=0;i<d.length;i++){
					$("#status").append("<option size='"+50+"' value='"+d[i].idstatus+"'>"+d[i].name+"</option>");
					$("#status1").append("<option size='"+50+"' value='"+d[i].idstatus+"'>"+d[i].name+"</option>");
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
					var idcard=$("#idcard").val();
				    if(idcard==null||idcard==""){
					 $("#idcard0").html("<i>身份证号码不能为空 !!!</i>");
					}else{
						$("#idcard0").html("");
						var licencecode=$("#licencecode").val();
						if(licencecode==null||licencecode==""){
							$("#licencecode0").html("<i>从业资格证书编号不能为空 !!!</i>");
						}else{
							$("#licencecode0").html("");
				           
							   $("#idMifare0").html("");
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
		$("#getCard").dialog({
			modal:true,
			autoOpen:false,
			width:750,
			height:500,
			buttons:{
				"确定":function(){
					var form = $("#getCardForm");
					$.post(form.attr('action'),form.serialize(),function(a){
						if(a=="ok"){
							location.reload();
						}else{
							alert("程序有点问题哟！");
						}
	             });
				}
			},
			close:function(){
				$(this).dialog("close");
			}
		});
	});

	//补卡
	function updateCard(idservicer){
		$("#idservicer_card").val(idservicer);
		$("#getCard").dialog("open");
	}
	function showInsert(){
		
// 		dia.dialog("open");
		$("#insertDialog").dialog("open");
	}
	function showUpdate(did){
		//查询维保人员类别（下拉框的值 ）
		
	 $.getJSON("${path }/servicer/toUpdate.do?idservicer="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			$("#name").val(d.name);
			$("#idcard").val(d.idcard);
			$("#idservice").val(d.idservice);
			$("#licencecode").val(d.licencecode);
			$("#type").val(d.type);
			$("#idMifare").val(d.idMifare);
			$("#status").val(d.status);
			$("#idservicer").val(d.idservicer);
			//打开修改对话框
			$("#updateDialog").dialog("open");
			
		});
		
	}
	function showSelectValue(){
		
	}
</script>

</head>

<body>
	<form action="${path}/servicer/list.do" method="post">
		<table cellpadding="0" cellspacing="1">
			<tr>
				<td>按 姓名/编码 <input type="text" name="key" size="50"
					value="${param.key}" /> <input type="submit" value="搜索" />
				</td>
			</tr>
		</table>
	</form>
	<table cellpadding="0" cellspacing="1">
		<tr>
			<th>编号</th>
			<th>维保人员姓名</th>
			<th>所属维保公司</th>
			<th>从业资格证书编号</th>
			<th>维保人员类别</th>
			<th>维保人员状态</th>
			<th>上岗卡标识</th>
			<th>操 作</th>
		</tr>
		<c:forEach items="${servicerList}" var="d">
			<tr>
				<td>${d.idservicer}</td>
				<td>${d.name }</td>
				<td>${d.idservice}</td>
				<td>${d.licencecode}</td>
				<td>${d.servicer_type_def.name }</td>
				<td>${d.status_def.name}</td>
				<td>${d.idMifare}</td>
				<td>
				       <input type="button" value="修改" onclick="showUpdate(${d.idservicer})"/>
				       <input type="button" value="补卡" onclick="updateCard(${d.idservicer})"/>
<%-- 					<a href="${path }/servicer/delete.do?idservicer=${d.idservicer}">删除</a> --%>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="8" style="text-align: left;">${pagination}</td>
		</tr>
	</table>
	<div align="right">
		<input type="button" value="添加一个维保人员"
		onclick="showInsert()" />
	</div>

 <div id="insertDialog" style="display: none" title="添加">
		<form action="${path }/servicer/insert.do" method="post" id="insertForm">
			<ul>
				<li>维保人员姓名:
				<li><input type="text" name="name"  id="name1" maxlength="20" size="50"/>*<div id="name2" style="float: right; margin-right:220 "></div>
				<li>身份证号码:
				<li><input type="text" name="idcard" id="idcard1"  maxlength="18" size="50" />*<div id="idcard2" style="float: right; margin-right:220 "></div>
				<li>所属维保公司:
				<li><select name="idservice" id="idservice1"></select>
				<li>从业资格证书编号:
				<li><input type="text" name="licencecode" id="licencecode1" maxlength="20" size="50"/>*<div id="licencecode2" style="float: right; margin-right:220 "></div>
				<li>维保人员类别:
				<li><select name="type" id="type1" ></select>
				<li><input type="hidden" name="status" value="1"/>
			</ul>
		</form>
	</div>
	<div id="updateDialog" style="display: none" title="修改">
		<form action="${path }/servicer/update.do" method="post" id="updateForm">
			<ul>
				<li>维保人员姓名:
				<li><input type="text" name="name"  id="name" maxlength="20" size="50"/>*<div id="name0" style="float: right; margin-right:220 " ></div>
				<li>身份证号码:
				<li><input type="text" name="idcard" id="idcard" maxlength="18" size="50"/>*<div id="idcard0" style="float: right; margin-right:220 "></div>
				<li>所属维保公司:
				<li><select name="idservice" id="idservice">
				</select>
				<li>从业资格证书编号:
				<li><input type="text" name="licencecode" id="licencecode" maxlength="20" size="50"/>*<div id="licencecode0" style="float: right; margin-right:220 "></div>
				<li>维保人员类别:
				<li><select name="type" id="type"></select>
				<li><input type="hidden" name="idservicer" id="idservicer" />
				<li><input type="hidden" name="status" value="1"/>
				<li><input type="hidden" name="idMifare" id="idMifare"/>
			</ul>
		</form>
	</div>
	<div title="发卡" id="getCard">
		<form action="${path }/servicer/getCard.do" method="post" id="getCardForm">
			<ul>
				<li>请将卡片放置读卡器上,然后点击下面的"发卡"按钮
				<li><input type="button" onclick="icw()" value="发卡"/><span id="cardmessage"></span>
<!-- 				<li>读取卡片序列号 -->
				<li><input type="hidden" name="idMifare" id="idMifare_card"  readonly="readonly"/>
<!-- 				<li>卡片将绑定本维保人员ID -->
				<li><input type="hidden" name="idservicer" id="idservicer_card" readonly="readonly"/>
			</ul>
		</form>
	</div>
	<object id="card" classid="CLSID:145AE2F1-2DEE-4C88-A7DF-B08E6DCDD39E"></object>
</body>
</html>
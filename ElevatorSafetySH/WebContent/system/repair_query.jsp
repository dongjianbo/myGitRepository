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
	
	$("#updateDialog").dialog({
		modal:true,
		autoOpen:false,
		width:500,
		height:400,
		buttons:{
			"确定":function(){
				var form = $("#updateForm");
				//=================检查表单中的字段是否为空 ===================================
				//判断文本框中的值是否为空
			var note=$("#note").val();
			if(note==null||note==""){
				$("#name2").html("<i>批复信息不能为空 !!!</i>");
			}else{
				$("#name2").html("");
					
				//提交表单 
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
	
	$("#detailDialog").dialog({
		modal:true,
		autoOpen:false,
		width:800,
		height:600,
		buttons:{
			"确定":function(){
				var form = $("#updateForm");
			
					
				
					
		   
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

	function form_submit(ck){
		if(ck){
			$("#form1").submit();
		}
	}
	function showUpdate(did){
		$("#rid").val(did);
		$("#updateDialog").dialog("open");
	}
	function showDetail(did){
		//开始查询个人信息
		$.getJSON("${path }/repair_query/detail.do?rid="+did,"rand="+Math.random(),function(d){
			//将查询到的信息放入修改表单中--注意隐藏域中的主键
			console.log(d);
			$("#rids").val(d.rid);
			$("#eids").val(d.eid);
			$("#user_safer").val(d.usersafer);
			$("#upload").val(d.upload);
			$("#notes").val(d.note);
			if(d.repairapprove !=""&&d.repairapprove !=null){
				$("#approver_ack").val(d.repairapprove.approveack);
				$("#approver").val(d.repairapprove.approver);
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
				$("#user1").val(d.repairmaint.user1);
				$("#user2").val(d.repairmaint.user2);
				$("#repair_date").val(d.repairmaint.repairdate);
				$("#image").val(d.repairmaint.image);
				$("#notew").val(d.repairmaint.note);
			}
			
			
			
			
			$("#detailDialog").dialog("open");
			
		});
		
	}
</script>
</head>
<body>
<form action="${path }/repair_query/list.do" method="get" id="form1">

<ul>
	
	<li><h3>列表</h3>
		<input type="radio" name="approve_ark" value="-1" onclick="form_submit(this.checked)"
			<c:if test="${noticeType eq -1}">
				checked="checked"
			</c:if>
		/>全部&nbsp;&nbsp;${count }条
		<input type="radio" name="approve_ark" value="0" onclick="form_submit(this.checked)"
			<c:if test="${noticeType eq 0}">
				checked="checked"
			</c:if>
		/>待批复&nbsp;&nbsp;${count_approve }条
		<input type="radio" name="approve_ark" value="1" onclick="form_submit(this.checked)"
			<c:if test="${noticeType eq 1}">
				checked="checked"
			</c:if>
		/>已批复&nbsp;&nbsp;${count_approved }条
		<input type="radio" name="approve_ark" value="3" onclick="form_submit(this.checked)"
			<c:if test="${noticeType eq 3}">
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
			<td>${n.upload }</td>
			<td>
			<c:choose>
			    <c:when test="${empty n.repairapprove}">
			  		  待批复
			    </c:when>
			    <c:otherwise>
			        ${n.repairapprove.approveType.approve_name}
			    </c:otherwise>
			</c:choose></td>
			<td>${n.repairapprove.approve_date }</td>
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


<div id="updateDialog" style="display: none" title="批复">
	<form action="${path }/repairApprove/update.do" method="post" id="updateForm">
		<ul>
			<li>批复说明:
			<li><textarea rows="5" cols="30" name="note" id="note"></textarea>*<div id="name2" style="float: right; margin-right:220" ><br>
			<li>其他说明:
			<li><textarea rows="5" cols="30" name="note2" id="note2"></textarea><br>
			<li><input type="hidden" name="rid" id="rid" />
		</ul>
	</form>
</div>
<div id="detailDialog" style="display: none" title="详细信息">
	<div title="申请信息">
	<h3>申请信息：</h3>
	<table width="100%" height="100%" id="table" cellspacing="0">
		<tr> 
			<td style="text-align: left;vertical-align: top;">
		        <ul>
		            <li> 维修ID:
					<li><input type="text" id="rids" name="rids" readonly="readonly" size="30" />
		            <li> 电梯ID:
					<li><input type="text" id="eids" name="eids"  size="30" />
					<li> 申报的安全员:
					<li><input type="text" id="user_safer" name="user_safer"  size="30" />
                 </ul>
		     </td>
		     <td style="text-align: left;vertical-align: top;">
		        <ul>
		        	<li> 申报时间:
					<li><input type="text" id="upload" name="upload" readonly="readonly" size="30" />
					<li> 申报内容:
					<li><textarea rows="5" cols="30" name="note" id="notes"></textarea>
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
					<li><input type="text" id="approver" name="approver"  size="30" />
					<li> 批复说明:
					<li><textarea rows="5" cols="30" name="note" id="notep"></textarea>
                 </ul>
		     </td>
		     <td style="text-align: left;vertical-align: top;">
		        <ul>
		        	<li> 批复时间:
					<li><input type="text" id="approve_date" name="approve_date"  size="30" />
					<li> 其他说明:
					<li><textarea rows="5" cols="30" name="note2" id="notep2"></textarea>
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
					<li><input type="text" id="user1" name="user1" readonly="readonly" size="30" />
		            <li> 维保人员2:
					<li><input type="text" id="user2" name="user2"  size="30" />
					<li> 维修时间:
					<li><input type="text" id="repair_date" name="repair_date"  size="30" />
                 </ul>
		     </td>
		     <td style="text-align: left;vertical-align: top;">
		        <ul>
		        	<li> 图像个数:
					<li><input type="text" id="image" name="image" readonly="readonly" size="30" />
					<li> 维修说明:
					<li><textarea rows="5" cols="30" name="note" id="notew"></textarea>
                 </ul>
		     </td>
		</tr>
	</table>
	</div>
</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${path}/css/system.css" rel="stylesheet" type="text/css">
<%-- <link href="${path}/css/table.css" rel="stylesheet" type="text/css"> --%>
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
	<script type="text/javascript">
	var dia;
	$().ready(function(){
		$("#date_test").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date_sign").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date_accident").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		//取电梯编号
		$.getJSON("${path}/elevator/elevatorIdList.do","rand="+Math.random(),function(d){
			 for(var i=0;i<d.length;i++){
	    		  $("#id_elevator").append("<option size='"+15+"' value='"+d[i].id_elevator+"'>"+d[i].id_elevator+"</option>");
	    	  }
		});
		//取检测单位名称
		$.getJSON("${path}/test/list_json.do","rand="+Math.random(),function(d){
			 for(var i=0;i<d.length;i++){
	    		  $("#id_test").append("<option size='"+15+"' value='"+d[i].idtest+"'>"+d[i].name+"</option>");
	    	  }
		});
		//取检验检测类型
		$.getJSON("${path}/test_type_def/list.do","rand="+Math.random(),function(d){
			for(var i=0;i<d.length;i++){
				 
	    		  $("#type_test").append("<option size='"+15+"' value='"+d[i].id_test_type+"'>"+d[i].name+"</option>");
	    	  }
		});
		//检测结论
		$.getJSON("${path}/result_type_def/list.do","rand="+Math.random(),function(d){
			 for(var i=0;i<d.length;i++){
	    		  $("#result").append("<option size='"+15+"' value='"+d[i].id_result_type+"'>"+d[i].name+"</option>");
	    	  }
		});
		//检验人员姓名
		$.getJSON("${path}/tester/id_tester.do","rand="+Math.random(),function(d){
			 for(var i=0;i<d.length;i++){
	    		  $("#id_tester").append("<option size='"+15+"' value='"+d[i].idtester+"'>"+d[i].name+"</option>");
	    	  }
		});
		//事故类别
		$.getJSON("${path}/accident_type_def/list.do","rand="+Math.random(),function(d){
			 for(var i=0;i<d.length;i++){
	    		  $("#type_accident").append("<option size='"+15+"' value='"+d[i].id_accident_type+"'>"+d[i].name+"</option>");
	    	  }
		});
	});
   function ifNull(){
	   var date_test=$("#date_test").val();
	   var date_sign=$("#date_sign").val();
	   var date_accident=$("#date_accident").val();
	   if(date_test==null||date_test==""){
		   
	   }else{
		   if(date_sign==null||date_sign==""){
			   
		   }else{
			   if(date_accident==null||date_accident==""){
				   
			   }else{
				   myform.submit();
			   }
		   }
	   }
   }
	
</script>
</head>
<body>
		<form action="${path }/testreport/insert.do" method="post" name="myform">
		<table width="100%" style="margin-top: 20px;">
		
		        <tr>
		           <td>
		           <ul>
		           <li>电梯编号:
		           <li><select id="id_elevator" name="id_elevator"></select>*
		           <li>检测单位名称:
		           <li><select id="id_test" name="id_test"></select>*
		           <li>检验检测类型:
		           <li><select id="type_test" name="type_test"></select>*
			       <li>报告编号:
			       <li><input type="text" name="number_report" id="number_report" size="39">
		           <li>检验检测时间:
		           <li><input type="text" name="date_test" id="date_test" placeholder="请选择检验检测时间" size="39">*
		           
		           
		            <li>检验结论:
					<li><select id="result" name="result"></select>*
					<li>检验人员姓名:
					<li><select id="id_tester" name="id_tester"></select>*
		            <li>签字者:
					<li><input type="text" name="signer" size="39"/>
					</ul>
		        </td>
		        <td>
		           <ul>
		            <li>报告批准时间:
					<li><input type="text" name="date_sign" id="date_sign" size="42" placeholder="请选择报告批准时间">*<br>
                    <li>主要问题:
					<!-- <input type="text" name="desc" size="50" /> -->
					   <li> <textarea rows="6" cols="30" name="desc"></textarea>
		            <li>事故发生时间:
					<li><input type="text" name="date_accident" id="date_accident" size="42" placeholder="请选择事故发生时间">*<br>
					<li>事故类别:
					<li><select id="type_accident" name="type_accident"></select>*
		            <li>事故处理描述:
				   <!-- <input type="text" name="process_accident" size="50"/> -->
				     <li><textarea rows="6" cols="30" name="process_accident"></textarea><br>
				  </ul>
		        </td>
		        </tr>
		            <tr>
		              <td colspan="2" style="padding-left: 450px;">  <input type="button" value="提交" onclick="ifNull()"/></td>
		             </tr>
		        </table>
		  </form>
</body>
</html>
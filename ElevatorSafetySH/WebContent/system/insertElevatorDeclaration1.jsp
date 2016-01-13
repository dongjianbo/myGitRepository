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
	<script src="${path}/jquery/ui/jquery.ui.datepicker.js"></script><!-- 日期控件的js -->
	<script type="text/javascript">
	var dia;
	$().ready(function(){
		$("#date1").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		$("#date2").datepicker({dateFormat:'yy-mm-dd'});//日期控件
		 $.getJSON("${path}/installer/selectId_installer.do","rand="+Math.random(),function(d){
	    	  //对安装单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_installer").append("<option size='"+50+"' value='"+d[i].idinstaller+"'>"+d[i].name+"</option>");
	    	  }
	       });	
		 $.getJSON("${path}/owner/selectId_owner.do","rand="+Math.random(),function(d){
	    	  //对产权单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_owner").append("<option size='"+50+"' value='"+d[i].idowner+"'>"+d[i].name+"</option>");
	    	  }
	       });	
		 $.getJSON("${path}/user/selectId_user.do","rand="+Math.random(),function(d){
	    	  //对使用单位进行循环
	    	  for(var i=0;i<d.length;i++){
	    		  $("#id_user").append("<option size='"+50+"' value='"+d[i].iduser+"'>"+d[i].name+"</option>");
	    	  }
	       });	
		 //-----------------------------------------------------------
		 $.getJSON("${path }/citylist/list.do","rand="+Math.random(),function(d){
				//将查询到的信息放入表单
				for(var i=0;i<d.length;i++){
				  $("#id_city").append("<option size='"+50+"' value='"+d[i].id_city+"'>"+d[i].name_city+"</option>");
				}
				//根据第一个城市的id查区域
				$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+d[0].id_city,"rand="+Math.random(),function(s){
					for(var i=0;i<s.length;i++){
						  $("#id_district").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
						}
				 //根据第一个区域查询乡镇
				    $.getJSON("${path }/subdistictlist/listById.do?id_city="+d[0].id_city+"&id_distrct="+s[0].id_district,"rand="+Math.random(),function(a){
				    	for(var i=0;i<a.length;i++){
				    		$("#id_subdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
				    	}
				    });
				});
			});
	});
		//选择城市-----------------------------------------------
	function chooseCity(id_city){
		//不同的城市选择不同的id
		$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city,"rand="+Math.random(),function(s){
			document.getElementById("id_district").innerHTML="";
			for(var i=0;i<s.length;i++){
				 $("#id_district").append("<option size='"+50+"' value='"+s[i].id_district+"'>"+s[i].name_district+"</option>");
				}
		//选择区域下面的像乡镇
			 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+s[0].id_district,"rand="+Math.random(),function(a){
				 document.getElementById("id_subdistrict").innerHTML="";	
				 for(var i=0;i<a.length;i++){
						$("#id_subdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
			    	}
			    });
		});
	}
	//选择区域来获取乡镇-------------------------------------
	function choosedistrict(id_district){
		//去城市的id
		var id_city=document.getElementById("id_city").value;
		 $.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_district,"rand="+Math.random(),function(a){
			 document.getElementById("id_subdistrict").innerHTML="";	
			 for(var i=0;i<a.length;i++){
		    		$("#id_subdistrict").append("<option size='"+50+"' value='"+a[i].id_subdistrict+"'>"+a[i].name_subdistrict+"</option>");
		    	}
		    });
	}
</script>
</head>
<body>
		<form action="${path }/elevator/insertTo2.do" method="post">
			     <ul>
					<li>验收检验机构:
					<li><input type="text" name="check_construct" size="50"/>
					<li>验收报告编号:
					<li><input type="text" name="check_construct_code" size="50"/>
					<li>安装单位名称:
					<li><select name="id_installer" id="id_installer"></select>
					<li>产权单位名称:
					<li><select name="id_owner" id="id_owner"></select>
					<li>使用单位名称:
					<li><select name="id_user" id="id_user"></select>
					<li>所属城市:
					<li><select name="id_city" id="id_city" onchange="chooseCity(this.value)"></select>
					<li>所属区县:
					<li><select name="id_district" id="id_district" onchange="choosedistrict(this.value)"></select>
					<li>所属街道乡镇:
					<li><select name="id_subdistrict" id="id_subdistrict"></select>
					<li>电梯所在位置:
					<li><input type="text" name="address" size="50">
					 <li>申报时间:
					<li><input type="text" name="date_declare" size="50" id="date1">
					<li>注册时间:
					<li><input type="text" name="date_register" size="50" id="date2"><input type="submit" value="下一页">
			     </ul>
			     
			  
		</form>
</body>
</html>
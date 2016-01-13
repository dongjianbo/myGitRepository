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
<script type="text/javascript" src="${path }/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$().ready(function(){
		$.getJSON("${path }/citylist/list.do?rand="+Math.random(),function(t){
			$("#city").append("<option value='"+0+"'>"+"-请选择-"+"</option>");
			$(t).each(function(index){
				$("#city").append("<option value='"+this.id_city+"'>"+this.name_city+"</option>");
			});
		});
		$("#city").change(function(){
			var id_city=this.value;
			$.getJSON("${path }/distictlist/listByIdCity.do?id_city="+id_city+"&rand="+Math.random(),function(t){
				$("#distictlist option").remove();
				$("#distictlist").append("<option value='"+0+"'>"+"-请选择-"+"</option>");
				$(t).each(function(index){
					$("#distictlist").append("<option value='"+this.id_district+"'>"+this.name_district+"</option>");
				});
			});
		});
		$("#distictlist").change(function(){
			var id_city=$("#city").val();
			var id_distict=this.value;
			$.getJSON("${path }/subdistictlist/listById.do?id_city="+id_city+"&id_distrct="+id_distict+"&rand="+Math.random(),function(t){
				$("#subdistictlist option").remove();
				$("#subdistictlist").append("<option value='"+0+"'>"+"-请选择-"+"</option>");
				$(t).each(function(index){
					$("#subdistictlist").append("<option value='"+this.id_subdistrict+"'>"+this.name_subdistrict+"</option>");
				});
			});
		});
		$("#subAddress").click(function(){
			var city=$("#city").val();
			var district=$("#distictlist").val();
			var subdistictlist=$("#subdistictlist").val();
			if(city=='0'||district=='0'||subdistictlist=='0'){
				alert("请在下拉框中选择相应的地址");
				return;
			}
			var address=$("#city option:selected").text()+$("#distictlist option:selected").text()+$("#subdistictlist option:selected").text()+$("#address").val();
			window.returnValue=address;
			window.close();
		});
	});
</script>
</head>
<body>
	<div>
		<ul>
			<li>请选择城市
			<li><select id="city"></select>
			<li>请选择县区
			<li><select id="distictlist"></select>
			<li>请选择街道
			<li><select id="subdistictlist"></select>
			<li>请填写详细地址
			<li><input type="text" id="address" size="100"/>
			<li>&nbsp;
			<li><div style="width: 600px" align="right"><button id="subAddress">确定提交</button></div>
		</ul>
		
		
	</div>

</body>
</html>
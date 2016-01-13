<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	
	$().ready(function(){
		//查询电梯注册状态（下拉框的值 ）
		$.getJSON("${path }/register_status_def/list_json.do","rand="+Math.random(),function(d){
			  //将查询到的信息放入修改表单中--注意隐藏域中的主键
			for(var i=0;i<d.length;i++){
				$("#id_register_status").append("<option size='"+50+"' value='"+d[i].id_register_status+"'>"+d[i].name+"</option>");
				
			}
		}); 
		
	});
</script>
</head>
<body>

<form action="" method="post">
电梯注册状态：<select name="id_register_status" id="id_register_status">
      </select>

</form>
</body>
</html>
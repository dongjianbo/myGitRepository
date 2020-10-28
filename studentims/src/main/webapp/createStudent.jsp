<%@page import="table.BanJi"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<title>创建学生信息</title>
</head>
<body>
	<%
		List<BanJi> bjlist=(List<BanJi>)request.getAttribute("bjlist");
	%>
	
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
			<form action="${pageContext.request.contextPath}/teacher/CreateStudentServlet" method="post">
				<div class="form-group">
					<label for="exampleInputEmail1">请选择班级:</label> <select
						class="form-control" name="bjid">
							<%
								for(BanJi bj:bjlist){
							%>
									<option value="<%=bj.getId()%>"><%=bj.getCname() %></option>
							<%		
								}
							%>
						
						</select>
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">请输入学生姓名:</label> <input
						type="text" class="form-control" placeholder="学生姓名唯一" name="sname" maxlength="20" required="required">
				</div>
				<div class="form-group">
					<label for="exampleInputFile">性别:</label> <br> <input
						type="radio" name="ssex" value="男" checked>男 <input type="radio"
						name="ssex" value="女">女
				</div>
				<div class="form-group">
					<label for="exampleInputFile">籍贯:</label> <input type="text"
						class="form-control" name="jiguan" />
				</div>
				<div class="form-group">
					<label for="exampleInputFile">毕业学校:</label> <input type="text"
						class="form-control" name="school" />
				</div>
				<div class="form-group">
					<label for="exampleInputFile">电话:</label> <input type="tel"
						class="form-control" name="tel" required="required"/>
				</div>
				<div class="form-group">
					<label for="exampleInputFile">登录名:</label> <input type="text"
						class="form-control" name="username" placeholder="学生姓名唯一" onblur="checkUname(this.value)" required="required"/>
					<div class="alert alert-warning alert-dismissible fade in hide text-center" role="alert" id="unameMessage">
					</div>
				</div>
				<div class="form-group">
					<label for="exampleInputFile">密码:</label> <input type="text"
						class="form-control" name="password" required="required" value="123456" placeholder="默认密码:123456"/>
					
				</div>
				
				<button type="submit" class="btn btn-danger" disabled="disabled">确定并提交</button>
			</form>
		</div>
	</div> 
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript">
    
    	
    	function checkUname(cname){
    		if(cname.trim()==""){
    			$("#unameMessage").html("请务必填写此项!");
				$("#unameMessage").removeClass("hide");
    		}else{
    			$.post("${pageContext.request.contextPath}/teacher/CheckUnameServlet","uname="+cname,function(j){
        			if(j=="true"){
        				$("#unameMessage").html("此登录名已存在!");
        				$("#unameMessage").removeClass("hide");
        				$("button[type='submit']").attr("disabled",true);
        			}
        			if(j=="false"){
        				$("#unameMessage").addClass("hide");
        				$("button[type='submit']").attr("disabled",false);
        			}
        		});
    		}
    		
    	}
    	
    </script>
</body>
</html>
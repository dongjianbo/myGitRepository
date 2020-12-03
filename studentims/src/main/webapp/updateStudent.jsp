<%@page import="table.Student"%>
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
<title>修改学生信息</title>
</head>
<body>
	<%
		Object obj=request.getAttribute("st");
		Student st=null;
		if(obj!=null){
			st=(Student)obj;
	%>
	
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
			<form action="${pageContext.request.contextPath}/teacher/UpdateStudentServlet" method="post">
				<div class="form-group">
					<label for="exampleInputPassword1">该学生学号是:</label> <input
						type="text" class="form-control" name="sid" readonly="readonly" maxlength="20" required="required"
						value="<%=st.getSid() %>"
						>
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">请输入学生姓名:</label> <input
						type="text" class="form-control" name="sname" placeholder="学生姓名唯一" maxlength="20" required="required"
						value="<%=st.getName() %>"
						>
				</div>
				<div class="form-group">
					<label for="exampleInputFile">性别:</label> <br> 
					<input
						type="radio" name="ssex" value="男" 
						<%=st.getSex().equals("男")?"checked":""%>>男 
					<input type="radio"
						name="ssex" value="女" <%=st.getSex().equals("女")?"checked":""%>>女
				</div>
				<div class="form-group">
					<label for="exampleInputFile">籍贯:</label> <input type="text"
						class="form-control" name="jiguan" 
						value="<%=st.getJiguan() %>"
						/>
				</div>
				<div class="form-group">
					<label for="exampleInputFile">毕业学校:</label> <input type="text"
						class="form-control" name="school" 
						value="<%=st.getSchool() %>"
						/>
				</div>
				<div class="form-group">
					<label for="exampleInputFile">电话:</label> <input type="tel"
						class="form-control" name="tel" required="required"
						value="<%=st.getTel() %>"
						/>
				</div>
				<button type="submit" class="btn btn-danger">确定并提交</button>
			</form>
		</div>
	</div>
	<%
		}
	%>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
	
</body>
</html>
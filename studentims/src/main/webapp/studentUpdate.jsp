<%@page import="table.Student"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>学员信息列表</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="row" style="margin-bottom:50px;">
		<div class="col-md-offset-3 col-md-6 text-center">
			<form class="form-inline" action="${pageContext.request.contextPath}/teacher/ToStudentUpdateForKeyServlet" method="get">
			  <div class="form-group">
			    <label for="exampleInputName2">请输入你要<%
			    	String m=request.getParameter("m");
			    	if(m.equals("xg")){
			    		out.print("修改");
			    	}
			    	if(m.equals("tx")){
			    		out.print("办理退学");
			    	}
			    
			    %>的学员编号或姓名:</label>
			    <input type="text" class="form-control" id="exampleInputName2" name="key" placeholder="学号/姓名">
			    <input type="hidden" name="m" value="<%=m %>"/>
			  </div>
			  <button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-search"></span></button>
			</form>
		</div>
	</div>
	<%
		Object obj=request.getAttribute("slist");
		if(obj!=null){
			List<Student> slist=(List<Student>)obj;
	%>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>学员编号</th>
						<th>姓名</th>
						<th>性别</th>
						<th>籍贯</th>
						<th>毕业学校</th>
						<th>联系电话</th>
						<th>修改学生信息</th>
					</tr>
				</thead>
				<tbody>
	  			<%
		  			for(Student st:slist){
				%>
						<tr>
							<td><%=st.getSid() %></td>
							<td><%=st.getName() %></td>
							<td><%=st.getSex() %></td>
							<td><%=st.getJiguan() %></td>
							<td><%=st.getSchool() %></td>
							<td><%=st.getTel() %></td>
							<td>
							<%
			    				if(m.equals("xg")){
			    			%>
			    			<a href="${pageContext.request.contextPath}/teacher/ToStudentUpdateServlet?sid=<%=st.getSid() %>" class="btn btn-danger">编辑</a>
			    			<%
			    			}
						    	if(m.equals("tx")){
						    %>
						    <a href="${pageContext.request.contextPath}/teacher/StudentExitServlet?sid=<%=st.getSid() %>" class="btn btn-danger">退学办理</a>
						    <%		
						    	}
						    
						    %>
							
							
							</td>
						</tr>
				<%
					}
	  			%>
	  			</tbody>
			</table>	
	
	<%
		}
	%>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</body>
</html>
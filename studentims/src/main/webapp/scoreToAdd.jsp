<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://code.jquery.com/ui/1.10.4/themes/ui-darkness/jquery-ui.css"  rel="stylesheet"/>

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
			<form class="form-inline" action="${pageContext.request.contextPath}/teacher/ToScoreAddForKeyServlet" method="get">
			  <div class="form-group">
			    <label for="exampleInputName2">请输入学员编号或姓名:</label>
			    <input type="text" class="form-control" id="exampleInputName2" name="key" value="${param.key}" placeholder="学号/姓名">
			  </div>
			  <button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-search"></span></button>
			</form>
		</div>
	</div>
	<c:if test="${slist!=null}">
		
	
		<table class="table table-hover">
			<thead>
				<tr>
					<th>学员编号</th>
					<th>所属班级</th>
					<th>姓名</th>
					<th>在档状态</th>
					<th>操作</th>
					
				</tr>
			</thead>
			<tbody>
  				<c:forEach items="${slist}" var="st">
  					<tr>
						<td>${st.sid }</td>
						<td>${st.cname }</td>
						<td>${st.name }</td>
						<td>${st.state==1?"在档":"已退档" }</td>
						<td><a class="btn btn-danger" href="ToScoreAddServlet?sid=${st.sid}">填写成绩</a></td>
					</tr>
  				</c:forEach>
  			</tbody>
		</table>
	</c:if>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery-ui.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    
</body>
</html>
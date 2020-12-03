<%@page import="java.util.Date"%>
<%@page import="table.Student"%>
<%@page import="java.util.List"%>
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
		<div class="col-md-offset-4 col-md-8">
			<form class="form-inline" action="${pageContext.request.contextPath}/teacher/ToStudentListForKeyServlet" method="get">
			  <div class="form-group">
			    <label for="exampleInputName2">请选择年级</label>
			    <select class="form-control" name="grade" >
			    	<%
			    		Date d=new Date();
			    		int year=d.getYear()+1900;
			    		for(int i=year;i>=2018;i--){
			    			%>
			    			<option value="<%=i-2000%>" 
			    				<%
			    					if(request.getParameter("grade").equals((i-2000)+"")){
			    						%>
			    						selected="selected"
			    						<%
			    					}
			    				%>
			    			><%=i%>级
			    			<%
			    		}
			    	%>
			    	<option value="0" 
			    		<%
	    					if(request.getParameter("grade").equals("0")){
	    						%>
	    						selected="selected"
	    						<%
	    					}
	    				%>
			    	>全部年级
			    </select>
			    
			   
			  </div>
			  <div class="form-group">
			    <label for="exampleInputName2">请输入关键字</label>
			    <input type="text" class="form-control" id="exampleInputName2" name="key" value="${param.key}" placeholder="姓名/班级/性别/籍贯">
			    <input type="hidden" name="page" id="inputp" value="1"/>
			  </div>
			  <button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-search"></span></button>
			  <button type="button" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/teacher/ExceportStudentServlet';">导出全部数据</button>
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
						<th>性别</th>
						<th>籍贯</th>
						<th>毕业学校</th>
						<th>联系电话</th>
						<th>登录名</th>
						<th>当前考核分数</th>
						<th>在档状态</th>
					</tr>
				</thead>
				<tbody>
	  				<c:forEach items="${slist}" var="st">
	  					<tr>
							<td>${st.sid }</td>
							<td>${st.cname }</td>
							<td>${st.name }</td>
							<td>${st.sex }</td>
							<td>${st.jiguan }</td>
							<td>${st.school }</td>
							<td>${st.tel }</td>
							<td>${st.uname }</td>
							<td>${st.score }</td>
							<td>${st.state==1?"在档":"已退档" }</td>
						</tr>
	  				</c:forEach>
	  			</tbody>
			</table>
			<nav aria-label="Page navigation" class="text-center">
			  <ul class="pagination">
			  	<c:if test="${p.page>1}">
			  		<li>
				      <a href="javascript:submitPage(${p.page-1 })" aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				      </a>
				    </li>
			  	</c:if>
			    
			    <c:forEach begin="1" end="${p.pages}" var="i">
			    	<li <c:if test="${i==p.page}">class="active"</c:if> ><a href="javascript:submitPage(${i })">${i }</a></li>
			    </c:forEach>
			    <c:if test="${p.page<p.pages}">
				    <li>
				      <a href="javascript:submitPage(${p.page+1 })" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
			    </c:if>
			  </ul>
			</nav>	
	
	</c:if>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    	function submitPage(p){
    		//将页面数据放入form中,然后提交
    		$("#inputp").val(p);
    		$("form").submit();
    	}
    </script>
</body>
</html>
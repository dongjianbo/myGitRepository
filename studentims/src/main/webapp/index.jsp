<%@page import="table.Account"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<base target="content">
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>软件工程学院企业课学员考核信息管理</title>

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
	<%
     	Object obj=session.getAttribute("user");
		Account acc=null;
     	if(obj!=null){
     		acc=(Account)obj;		
     	}
    %>				
	<div class="jumbotron">
		<div class="col-md-offset-1">
			<h2><font style="font-family:华文楷体;">软件工程学院企业课学生考核信息管理</font></h2>
	 		<p><font style="font-family:华文楷体;">请正确使用本系统对学生信息以及考核情况进行管理.</font></p>
		</div>
	 
	</div>
    <nav id="bs-navbar" class="nav nav-tabs bg-default" style="margin-top:-30px">
      <ul class="nav navbar-nav">
      	<% 
        	if(acc.getRole()==2){
        %>
	      	
		            <li><a href="${pageContext.request.contextPath }/student/StudentDetailServlet?sid=${user.sid}">个人信息</a></li>
		            <li><a href="${pageContext.request.contextPath }/student/WeiJiDetailServlet?sid=${user.sid}">违纪记录</a></li>
		            <li><a href="${pageContext.request.contextPath }/student/ScoreDetailServlet?sid=${user.sid}">考试成绩</a></li>
		  
        <%	
        	}
        %>
        <% 
        	if(acc.getRole()==1){
        %>
        
	        <li class="dropdown">
	          <a class="dropdown-toggle" data-toggle="dropdown" href="../getting-started/"><b>学生信息</b><span class="caret"></span></a>
	        	<ul class="dropdown-menu">
	        		<li><a href="${pageContext.request.contextPath}/importStudent.jsp">批量导入学员</a></li>
		            <li><a href="${pageContext.request.contextPath}/teacher/ToCreateStudentServlet">学员信息录入</a></li>
		            <li><a href="${pageContext.request.contextPath}/teacher/ToStudentListForKeyServlet?page=1&key=">学员信息列表</a></li>
		            <li><a href="${pageContext.request.contextPath}/studentUpdate.jsp?m=xg">学员信息修改</a></li>
		            <li role="separator" class="divider"></li>
		            <li><a href="${pageContext.request.contextPath}/studentUpdate.jsp?m=tx">学员退学办理</a></li>
		        </ul>
	        </li>
	        <li>
	          <a class="dropdown-toggle" data-toggle="dropdown"  href="../css/"><b>学分考核</b><span class="caret"></span></a>
	          <ul class="dropdown-menu">
		            <li><a href="${pageContext.request.contextPath}/createWeiJi.jsp">填写违纪记录</a></li>
		            <li><a href="${pageContext.request.contextPath}/weiJiList.jsp">违纪记录查询</a></li>
		            <li role="separator" class="divider"></li>
		            <li><a href="${pageContext.request.contextPath}/teacher/WeijiListServlet?page=1&bjid=0">违纪记录列表</a></li>
		        </ul>
	        </li>
	        <li>
	          <a class="dropdown-toggle" data-toggle="dropdown"  href="../components/"><b>班级管理</b><span class="caret"></span></a>
	        	<ul class="dropdown-menu">
		            <li><a href="${pageContext.request.contextPath}/createBanJi.jsp">班级管理</a></li>
		            <li role="separator" class="divider"></li>
		            <li><a href="${pageContext.request.contextPath}/studentBanJi.jsp">学员调班</a></li>
		        </ul>
	        </li>
	        <li>
	          <a class="dropdown-toggle" href="${pageContext.request.contextPath}/createCourse.jsp" ><b>课程设置</b></a>
	        </li>
	        <li>
	          <a class="dropdown-toggle" data-toggle="dropdown" href="../customize/" ><b>考试成绩</b><span class="caret"></span></a>
	          	<ul class="dropdown-menu">
		            <li><a href="${pageContext.request.contextPath}/scoreToAdd.jsp">考试成绩录入</a></li>
		            <li><a href="${pageContext.request.contextPath}/chartScore.jsp">考试成绩统计</a></li>
		            <li><a href="${pageContext.request.contextPath}/teacher/ToScoreListServlet">考试成绩列表</a></li>
		        </ul>
	        </li>
	        <li>
	        	<a class="dropdown-toggle" data-toggle="dropdown"  href="http://www.youzhan.org" ><b>账号管理</b><span class="caret"></span></a>
	        	<ul class="dropdown-menu">
		            <li><a href="${pageContext.request.contextPath}/accountAdd.jsp">添加账号</a></li>
		            <li><a href="${pageContext.request.contextPath}/teacher/AccountListServlet?key=&page=1">账号禁用与启用</a></li>
		        </ul>
	        </li>
        <%	
        	}
        %>
        <% 
        	if(acc.getRole()==3){
        %>
        
	        <li class="dropdown">
	          <a class="dropdown-toggle" data-toggle="dropdown" href="../getting-started/"><b>学生信息</b><span class="caret"></span></a>
	        	<ul class="dropdown-menu">
	        		<li><a href="${pageContext.request.contextPath}/importStudent.jsp">批量导入学员</a></li>
		            <li><a href="${pageContext.request.contextPath}/teacher/ToCreateStudentServlet">学员信息录入</a></li>
		            <li><a href="${pageContext.request.contextPath}/teacher/ToStudentListForKeyServlet?page=1&key=">学员信息列表</a></li>
		            <li><a href="${pageContext.request.contextPath}/studentUpdate.jsp?m=xg">学员信息修改</a></li>
		            <li role="separator" class="divider"></li>
		            <li><a href="${pageContext.request.contextPath}/studentUpdate.jsp?m=tx">学员退学办理</a></li>
		        </ul>
	        </li>
	        <li>
	          <a class="dropdown-toggle" data-toggle="dropdown"  href="../css/"><b>学分考核</b><span class="caret"></span></a>
	          <ul class="dropdown-menu">
		            <li><a href="${pageContext.request.contextPath}/createWeiJi.jsp">填写违纪记录</a></li>
		            <li><a href="${pageContext.request.contextPath}/weiJiList.jsp">违纪记录查询</a></li>
		            <li role="separator" class="divider"></li>
		            <li><a href="${pageContext.request.contextPath}/teacher/WeijiListServlet?page=1&bjid=0">违纪记录列表</a></li>
		        </ul>
	        </li>
	        <li>
	          <a class="dropdown-toggle" data-toggle="dropdown" href="../customize/" ><b>考试成绩</b><span class="caret"></span></a>
	          	<ul class="dropdown-menu">
		            <li><a href="${pageContext.request.contextPath}/scoreToAdd.jsp">考试成绩录入</a></li>
		            <li><a href="${pageContext.request.contextPath}/chartScore.jsp">考试成绩统计</a></li>
		            <li><a href="${pageContext.request.contextPath}/teacher/ToScoreListServlet">考试成绩列表</a></li>
		        </ul>
	        </li>
	        <li>
	          <a class="dropdown-toggle" data-toggle="dropdown"  href="../components/"><b>班级管理</b><span class="caret"></span></a>
	        	<ul class="dropdown-menu">
		            <li><a href="${pageContext.request.contextPath}/createBanJi.jsp">班级管理</a></li>
		            <li role="separator" class="divider"></li>
		            <li><a href="${pageContext.request.contextPath}/studentBanJi.jsp">学员调班</a></li>
		        </ul>
	        </li>
	        
        <%	
        	}
        %>
      </ul>
      <ul class="nav navbar-nav navbar-right" style="margin-right:20px;">
        <!-- <li><a href="http://mb.bootcss.com" onclick="_hmt.push(['_trackEvent', 'docv3-navbar', 'click', 'themes'])" target="_blank">主题/模板</a></li> -->
        <li><a>你好,	<b><%
        				if(acc!=null){
        					out.print(acc.getTruename());
        				}
        			%></b></a></li>
        
        <li><a href="${pageContext.request.contextPath}/passwordChange.jsp"><b>个人设置</b></a></li>
        <li><a target="_top" href="${pageContext.request.contextPath}/LoginOutServlet"><b>登出</b></a>
      </ul>
    </nav>
    <iframe name="content" style="width:100%;height:700px;border:0;padding-top: 50px;"
    	<c:if test="${user.role==1 or user.role==3}">
    		src="${pageContext.request.contextPath}/teacher/ToStudentListForKeyServlet?page=1&key="
    	</c:if>
    	<c:if test="${user.role==2}">
    		src="${pageContext.request.contextPath }/student/StudentDetailServlet?sid=${user.sid}"
    	</c:if>
    ></iframe>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</body>
</html>
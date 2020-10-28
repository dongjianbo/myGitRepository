<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>软件工程学院企业课学生考核信息管理系统登录</title>

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
  		request.setAttribute("cookien", "");
  		request.setAttribute("cookien", "");
  		Cookie[] cookies=request.getCookies();
  		if(cookies!=null&&cookies.length>0){
  			for(Cookie cook:cookies){
  	  			String name=cook.getName();
  	  			String value=cook.getValue();
  	  			if(name.equals("n")){
  	  				request.setAttribute("cookien", URLDecoder.decode(value, "UTF-8"));
  	  			}
  	  			if(name.equals("p")){
  	  				request.setAttribute("cookiep", URLDecoder.decode(value, "UTF-8"));
  	  			}
  	  		}
  		}
  		
  	%>
    <div class="jumbotron">
    	<div class="col-md-offset-1">
    		<h2><font style="font-family:华文楷体;">软件工程学院企业课学生考核信息管理</font></h2>
    	</div>
	</div>
	<div class="row">
		<div class="col-md-offset-2 col-md-4">
			<img src="images/m.jpg">
		</div>
		<div class="col-md-4">
			<div class="panel panel-default"  style="margin-top:30px;">
			<div class="panel-heading">用户登录</div>
			  <div class="panel-body">
			    <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
				  <div class="form-group">
				    <label for="exampleInputEmail1">用户名</label>
				    <input type="text" class="form-control" name="username" id="username" placeholder="" value="${cookien}">
				  </div>
				  <div class="form-group">
				    <label for="exampleInputPassword1">密码</label>
				    <input type="password" class="form-control" name="password" id="password" placeholder="" value="${cookiep}">
				  </div>
				  <div class="form-group">
				    <label for="yzm">验证码</label>
				    <img id="yzmimage" alt="验证码" src="YanZhengMaServlet">
				    <a href="javascript:changeyzm()">看不清</a>
				    <input type="text" class="form-control" name="yzm" id="yzm" placeholder="">
				    
				  </div>
				  <p class="bg-danger">${em }</p>
				  <button type="submit" class="btn btn-danger col-md-12">登录</button>
				  
				</form>
			  </div>
		  
			</div>
		</div>
	</div>
	<div class="panel panel-default" style="margin-top:150px;">
		<div class="panel-body" align="center">
			<p>黄冈科技职业学院</p>
			<p>软件工程学院</p>
			<p>计算机应用专业</p>
			<p>联系电话:0713-8358008</p>
		</div>
	</div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    	function changeyzm(){
    		$("#yzmimage").attr("src","YanZhengMaServlet?rand="+Math.random());
    	}
    </script>
  </body>
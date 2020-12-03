<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
			<form action="${pageContext.request.contextPath}/teacher/CreateWeiJiServlet" method="post">
				<div class="form-group">
					<label for="exampleInputEmail1">请填写违纪人的姓名或学号:</label> 
					<input type="hidden" name="sid" id="sid"/>
					<input
						type="text" class="form-control" name="sname" id="sname" maxlength="20" required="required" placeholder="姓名或学号"
						onblur="checkSname(this.value)"
						>
					<div class="alert alert-warning alert-dismissible fade in hide text-center" role="alert" id="snameMessage">
					</div>
				</div>
				
				<div class="form-group">
					<label for="exampleInputPassword1">该生所属班级:</label> <input
						type="text" class="form-control" name="bjname" id="bjname"  maxlength="20" readonly="readonly" required="required">
				</div>
				<div class="form-group">
					<label for="exampleInputFile">当前考核分数:</label> <input type="text"
						class="form-control" name="fenshu" id="fenshu" readonly="readonly"/>
				</div>
				<div class="form-group">
					<label for="exampleInputFile">扣除分值:</label> <input type="number"
						class="form-control" value="1" name="koufen" min="1" max="3" />
				</div>
				<div class="form-group">
					<label for="exampleInputFile">违纪时间:</label>
					<input class="form-control timepicker timepicker-default" required="required" name="wjtime" id="wjtime"  type="datetime-local">
				</div>
				<div class="form-group">
					<label for="exampleInputFile">扣分原因:</label> 
					<textarea class="form-control" rows="5" cols="" name="wjyuanyin"></textarea>
				</div>
				<div class="form-group">
					<label for="exampleInputFile">处罚决定人:</label> 
					<input type="text"
						class="form-control"  name="chufaren" value="${user.truename}" readonly="readonly"/>
				</div>
				<button type="submit" class="btn btn-danger" disabled="disabled">确定并提交</button>
			</form>
		</div>
	</div>
	
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery-ui.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    
    	
    	function checkSname(cname){
    		if(cname.trim()==""){
    			$("#snameMessage").html("请务必先填写此项!");
				$("#snameMessage").removeClass("hide");
    		}else{
    			$.getJSON("${pageContext.request.contextPath}/teacher/CheckWJSnameServlet","cname="+cname,function(j){
        			if(j.state=="1"){
        				$("#snameMessage").html("查无此人!");
        				$("#snameMessage").removeClass("hide");
        				$("button[type='submit']").attr("disabled",true);
        			}
        			if(j.state=="2"){
        				$("#snameMessage").html("你输入的信息不够精确,导致查找的人数过多.");
        				$("#snameMessage").removeClass("hide");
        				$("button[type='submit']").attr("disabled",true);
        			}
        			if(j.state=="3"){
        				$("#snameMessage").addClass("hide");
        				var s=j.stlist[0];
        				$("#sid").val(s.sid);
        				$("#sname").val(s.name);
        				$("#bjname").val(s.cname);
        				$("#fenshu").val(s.score);
        				$("button[type='submit']").attr("disabled",false);
        			}
        		});
    		}
    		
    	}
    </script>
</body>
</html>
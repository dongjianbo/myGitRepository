<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath }/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">

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
			<div class="panel panel-default">
			  <div class="panel-heading" id="ptitle">更改个人密码</div>
				  <div class="panel-body">
					<form method="post" action="${pageContext.request.contextPath}/PasswordChangeServlet" onsubmit="return checkform()">
						<div class="form-group">
							<label for="exampleInputPassword1">请输入原密码:</label> 
							<input type="hidden" name="id" value="${user.id}"/>
							<input
								type="password" class="form-control" name="password" id="password" maxlength="20" required="required">
						</div>
						<div class="form-group">
							<label for="exampleInputFile">新密码:</label>
							<input
								type="password" class="form-control" name="newpwd1" id="newpwd1" maxlength="20" required="required">
						</div>
						<div class="form-group">
							<label for="exampleInputFile">确认新密码:</label>
							<input
								type="password" class="form-control" name="newpwd2" id="newpwd2" maxlength="20" required="required">
						</div>
						<div class="text-center">
							<button type="submit" class="btn btn-danger" id="sub">确定并提交</button>
							<div id="message">${error }</div>
						</div>
						
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" tabindex="-1" role="dialog" id="messageDiv">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">删除班级</h4>
	      </div>
	      <div class="modal-body">
	        <p id="delmessage"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-danger" data-dismiss="modal">确定</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath }/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    	function checkform(){
    		var newpwd1=$("#newpwd1").val();
    		var newpwd2=$("#newpwd2").val();
    		if(newpwd1!=newpwd2){
    			$("#message").html("两次新密码输入不一致");
    			return false;
    		}else{
    			$("#message").html("");
    			return true;
    		}
    	}
    </script>
</body>
</html>
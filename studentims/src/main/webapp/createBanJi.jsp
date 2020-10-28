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
		<div class="col-md-8">
			<table class="table  table-hover">
				<thead>
					<tr>
						<th>班级编号</th>
						<th>班级名称</th>
						<th>说明</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
		<div class="col-md-4">
			<div class="panel panel-default">
			  <div class="panel-heading" id="ptitle">添加新班级</div>
				  <div class="panel-body">
					<form method="post">
						<div class="form-group">
							<label for="exampleInputPassword1">请输入班级名称:</label> <input
								type="text" class="form-control" name="cname" id="cname" maxlength="20" required="required">
								<div class="container" id="nameexists"></div>
								<input type="hidden" name="id" id="bjid"/>
						</div>
						<div class="form-group">
							<label for="exampleInputFile">说明:</label> <br>
							<textarea rows="5" class="form-control" name="cinfo" id="cinfo" cols=""></textarea>
						</div>
						<div class="text-center">
							<button type="button" class="btn btn-danger" id="sub">确定并提交</button>
							<div id="message"></div>
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
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath }/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    	$(function(){
    		loadBanJi();
    		toAddBJ();
    	})
    	function loadBanJi(){
    		$("table tbody tr").remove();
    		//获取所有的班级信息
    		$.getJSON("${pageContext.request.contextPath}/teacher/GetBanJILIstServlet","rand="+Math.random(),function(s){
    			$(s).each(function(i,bj){
    				var tr=$("<tr><td>"+bj.id+"</td><td>"+this.cname+"</td><td>"+this.cinfo+"</td></tr>");
    				var td=$("<td></td>");
    				var button=$("<button class='btn btn-primary'>编辑</button>");
    				var button_del=$("<button class='btn btn-danger'>删除</button>");
    				tr.append(td);
    				td.append(button);
    				td.append(button_del);
    				$("table tbody").append(tr);
    				button.click(function(){
    					$("#bjid").val(bj.id);
    					$("#cname").val(bj.cname);
    					$("#cinfo").val(bj.cinfo);
    					$("#sub").unbind('click').click(updateBanJi);
    					$("#sub").text("确定修改");
    					$("#ptitle").text("修改班级");
    				});
    				button_del.unbind('click').click(function(){
    					deleteBanJi(bj.id);
    				});
    			});
    		});
    	}
    	function deleteBanJi(bjid){
    		var param={"bjid":bjid};
    		$.post("${pageContext.request.contextPath}/teacher/DeleteBanJiServlet",param,function(r){
    			if(r=="1"){
    				$("#delmessage").html("删除成功!");
    				$("#messageDiv").modal("show");
    				loadBanJi();
    			}
    			if(r=="0"){
    				$("#delmessage").html("该班级下已拥有学生,不可删除,你应先将该班级下的学生调至其他班.");
    				$("#messageDiv").modal("show");
    				
    				
    			}
    			if(r=="-1"){
    				$("#delmessage").html("发生未知异常!");
    				$("#messageDiv").modal("show");
    			}
    		});
    	}
    	function updateBanJi(){
    		var id=$("form input[name='id']").val();
			if(id.trim()==""){
				return;
			}
    		var cname=$("form input[name='cname']").val();
			if(cname.trim()==""){
				alert("班级名称不能为空!");
				return;
			}
			
    		//表单的值
    		var ser=$("form").serialize();
    		$.post("${pageContext.request.contextPath}/teacher/UpdateBanJiServlet",ser,function(x){
    			if(x==1){
    				toAddBJ();
    				loadBanJi();
    			}
    			if(x==-1){
    				$("#message").html("修改发生未知错误!");
    			}
    		});
    	}
    	function insertBanJi(){
    		var cname=$("form input[name='cname']").val();
			if(cname.trim()==""){
				alert("必须填写班级名称");
				return;
			}
    		//表单的值
    		var ser=$("form").serialize();
    		$.post("${pageContext.request.contextPath}/teacher/InsertBanJiServlet",ser,function(x){
    			if(x==1){
    				$("form input[name='cname']").val("");
    				$("#cinfo").val("");
    				loadBanJi();
    			}
    			if(x==-1){
    				$("#message").html("插入发生未知错误!");
    			}
    		});
    	}
    	function toAddBJ(){
    		$("form input[name='cname']").val("");
			$("#cinfo").val("");
			$("form input[name='id']").val("");
    		$("#sub").unbind('click').click(insertBanJi);
    		$("#sub").text("确定添加");
    		$("#ptitle").text("添加新班级");
    	}
    </script>
</body>
</html>
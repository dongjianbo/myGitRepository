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

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="row">
		<div class="col-md-2 col-md-offset-4 text-left">
			<ul class="list-unstyled">
				<li><h4>姓名:${st.name}</h4>
				<li><h4>班级:${st.cname}</h4>
			</ul>
		</div>
		<div class="col-md-2  text-left">
			<ul class="list-unstyled">
				<li><h2>${st.score}</h2>
			</ul>
		</div>
	</div>
	<hr>
	<h5>历史扣分记录:</h5>
	<c:if test="${wjlist.size()==0}">
		<p class="text-center">暂无违纪记录,请继续保持!</p>
	</c:if>
	<div class="row">
		<c:forEach items="${wjlist}" var="wj">
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						${wj.wjtime}	
					</div>
					<div class="panel-body">
						<ul class="list-unstyled">
							<li>扣除分值:<div class="panel panel-default">${wj.koufen}</div>
							<li>扣分原因:<div class="panel panel-default">${wj.wjyuanyin}</div>
							<li>当前状态:<div class="panel panel-default">${wj.state==1?"未确认":"已确认"}</div>
							<li>处罚人:<div class="panel panel-default">${wj.chufaren}</div>
							<li>情况说明:<textarea rows="5" class="form-control" id="info${wj.id}" cols="" placeholder="有异议请填写说明"
								<c:if test="${wj.state==2}">
									readonly="readonly"
								</c:if>
							>${wj.info}</textarea>
							<li><div class="text-right">
							<c:if test="${wj.state==1}">
								<button class="btn btn-danger" onclick="changeState(${wj.id})">确认</button>
							</c:if>
							</div>
							
						</ul>
					</div>
				</div>
			</div>
		</c:forEach>
		
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    	function changeState(id){
    		var info=$("#info"+id).val();
    		var param={id:id,info:info};
    		$.post("${pageContext.request.contextPath}/student/WeiJiChangeStateServlet",param,function(r){
    			if(r=="Y"){
    				location.reload();
    			}
    			if(r=='N'){
    				alert("发生异常");
    			}
    		});
    	}
    </script>
</body>
</html>
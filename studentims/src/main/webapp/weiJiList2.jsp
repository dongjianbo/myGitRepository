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
	<div class="row" style="margin-bottom:50px;">
		<div class="col-md-offset-3 col-md-6 text-center">
			<form class="form-inline" action="${pageContext.request.contextPath}/teacher/WeijiListServlet" method="get">
			  <div class="form-group">
			    <label for="exampleInputName2">请选择要查看的班级:</label>
			    <select name="bjid" class="form-control">
			    	<option value="0">全部班级</option>
			    	<c:forEach items="${bjlist}" var="bj">
			    		<option value="${bj.id}" 
			    			<c:if test="${bj.id eq param.bjid}">
			    				selected="selected"
			    			</c:if>
			    		>${bj.cname}</option>
			    	</c:forEach>
			    </select>
			  	<input type="hidden" name="page" id="inputp" value="1"/>
			  </div>
			  <button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-search"></span></button>
			</form>
		</div>
	</div>
	<c:if test="${slist!=null and !empty slist}">
		
	
		<table class="table table-hover">
			<thead>
				<tr>
					<th>学员编号</th>
					<th>所属班级</th>
					<th>姓名</th>
					<th>联系电话</th>
					<th>在档状态</th>
					<th>当前考核分数</th>
					<th>查看违纪记录</th>
				</tr>
			</thead>
			<tbody>
  				<c:forEach items="${slist}" var="st">
  					<tr>
						<td>${st.sid }</td>
						<td>${st.cname }</td>
						<td>${st.name }</td>
						<td>${st.tel }</td>
						<td>${st.state==1?"在档":"已退档" }</td>
						<td><b class="text text-danger">${st.score }</b></td>
						<td><a href="${pageContext.request.contextPath }/teacher/WeiJiDetailServlet?sid=${st.sid}" class="btn btn-danger">查看详情</a></td>
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>test longwan</title>
    <script type="text/javascript">
        function fn1() {
        	
		        a=card.WriteUserData(2,3,0)
				alert("write user data result:a="+a)
			}
			
			function fn2(){
				a=card.serial;
				alert(" get serial result:a="+a)
			}
			
			function fn3(){
				a=card.last_error;
				alert("get last error result:a="+a)
			}
        
    </script>
</head>
<body>
    <object id="card" classid="CLSID:145AE2F1-2DEE-4C88-A7DF-B08E6DCDD39E"></object>
    <input type="button" value="write user data" onclick="fn1()" />
	<input type="button" value="get serial" onclick="fn2()" />
	<input type="button" value="last error" onclick="fn3()" />
</body>
</html>

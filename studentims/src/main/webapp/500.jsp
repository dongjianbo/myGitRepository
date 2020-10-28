<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
	<title>500</title>
</head>

<body bgcolor="linen" text="black">
<div style="font-size: 400px; text-align: center;">500</div>
<div style="font-size: 128px; text-align: center; font-variant: small-caps;">EXCEPTION</div>
<div style="text-align: center;">The document you requested has a exception. Please contact the manager!</div>
	<%
		out.println(exception.fillInStackTrace());
	%>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>不支持的协议</title>
<style type="text/css">
div.header {
	margin: 10px auto;
	font-size: 28px;
}
</style>
</head>
<body>
	<div class="header">不支持的协议</div>
	<div class="content"><%=request.getAttribute("message")%></div>
</body>
</html>
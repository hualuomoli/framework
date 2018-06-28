<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>系统运行错误</title>
<style type="text/css">
div.header {
	margin: 10px auto;
	font-size: 28px;
	color: #ab2c36;
}
</style>
</head>
<body>
	<div class="header">系统运行错误</div>
	<div class="content"><%=request.getAttribute("message")%></div>
</body>
</html>
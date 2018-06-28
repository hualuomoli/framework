<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>请求方法不允许</title>
<style type="text/css">
div.header {
	margin: 10px auto;
	font-size: 28px;
}
</style>
</head>
<body>
	<div class="header">请求方法不允许</div>
	<div class="content"><%=request.getAttribute("message")%></div>
</body>
</html>
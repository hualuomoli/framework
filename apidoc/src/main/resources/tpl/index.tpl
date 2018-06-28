<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>API DOC</title>
	<link rel="stylesheet" href="css/index.css" />
</head>
<body>
	<div class="container">
		<ul>
			<#list docs as doc>
			<li><a href="${doc.relativePath}/${doc.method}.html">${doc.title}</a></li>
			</#list>
		</ul>
	</div>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>

<% String name=(String)session.getAttribute("name"); %>

	<h1>Welcome <%= name %></h1>
	
</body>
</html>
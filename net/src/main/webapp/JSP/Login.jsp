<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
</head>
<body>
	<h1>Welcome to AP International Bank</h1>
	<div>
		<form  action="<%= request.getContextPath() %>/ProcessServlet" method="post">
		User Id <input type="number" min=7000 name="userId" required><br>
		Password <input type="password" name="password" required><br>
		<input type="submit" name=action value="Login">
		
	
	<!-- <input type="submit" name="action" value="AdminLogin">
	<input type="submit" name="action" value="CustomerLogin"> -->
	</form> 
	</div>

	<h4>${wrongPassword}</h4>
	

</body>
</html>

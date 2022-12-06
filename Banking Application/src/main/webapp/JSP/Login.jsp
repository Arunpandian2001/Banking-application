<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Login.css">

</head>
<body>
<div class=whole> 
<img id="logo" alt="web" src="<%=request.getContextPath()%>/Images/web.png">

<div class="login">

	<br>
	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post">
		
		<table class=logintable>
		<tr><th id="header">Login</th><td></td></tr>
		<tr>
			<td>User Id </td> <td><input class="input" type="number" min=7000
				name="userId" required ></td></tr>
			<tr><td>Password</td> <td><input class="input" type="password" name="password"
				required ></td></tr>
				<tr><td></td><td><button class="button" type="submit" name=action value="Login">Login</button></td></tr>
			</table>
				
			
	</form>
	
</div>

 </div> 
	

<h4 id="loginmessage">${message}</h4>
	<h4 id="loginerrormessage">${wrongPassword}</h4>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Customer.css">

</head>
<body>

<h1>CHANGE PASSWORD</h1>

<div class="deposit">
<form action="<%=request.getContextPath()%>/ProcessServlet" method="get" target="customerFrame">
<table class="deposittable">
<tr><th>Old Password</th><td><input type="text" name="oldpassword" required></td></tr>
<tr><th>New Password</th><td><input type="password" name="newpassword" required ></td></tr>
<tr><th>Re-Enter Password</th><td><input type="password" name="reenterpassword" required></td></tr>
</table>

<button class="profilebutton" name="action" value="customerchangepassword">Submit</button>

</form>
</div>
<div id="message">${message}</div>
</body>
</html>
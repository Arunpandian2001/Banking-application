<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin profile</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Customer.css">

</head>
<body>

<form action="">

<%@ page import="pojo.UserPojo" %>
	<%
	UserPojo pojo=(UserPojo)request.getAttribute("userDetails"); %>
	
	<h1>PROFILE</h1>
	<div id="message">${message}</div>
	<table class="centertable">
	<tr><th>Name</th><td><%=pojo.getName()%></td></tr>
	<tr><th>User Id</th><td><%=pojo.getId()%></td></tr>
	<tr><th>D.O.B</th><td><input type="date" name="dob" readonly value="<%=pojo.getDob()%>"></td></tr>
	<tr><th>Mobile Number</th><td><input type="text"name="mobile" value="<%=pojo.getMobile()%>"></td></tr>
	<tr><th>E-mail</th><td><input type="text" name="email" value="<%=pojo.getEmail()%>"></td></tr>
	<tr><th>Address</th><td><input type="text" name="address" value="<%=pojo.getAddress()%>"></td></tr>
	</table>
	
</form>
<button class="profilebutton" value="save"  name="action" >Save</button>
	
	
</body>
</html>
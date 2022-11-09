<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer profile</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Customer.css">

</head>
<body>

<form action="<%= request.getContextPath()%>/ProcessServlet" method="get" target="customerFrame">

<%@ page import="pojo.CustomerPojo "%>
	<%
	
	CustomerPojo pojo = (CustomerPojo) request.getAttribute("userDetails");
	%>
	<h1 style="text-align:center;justify-content:center;">PROFILE</h1>
	
	<table class="centertable">
	<tr><th>Name</th><td><%=pojo.getName()%></td></tr>
	<tr><th>User Id</th><td><%=pojo.getId()%></td></tr>
	<tr><th>D.O.B</th><td><input type="date" name="dob" readonly value="<%=pojo.getDob()%>"></td></td></tr>
	<tr><th>Mobile Number</th><td><input type="text"name="mobile" value="<%=pojo.getMobile()%>"></td></tr>
	<tr><th>E-mail</th><td><input type="text" name="email" value="<%=pojo.getEmail()%>"></td></tr>
	<tr><th>Aadhar Number</th><td><%=pojo.getAadhar()%></td></tr>
	<tr><th>PAN Number</th><td><%=pojo.getPanNumber()%></td></tr>
	<tr><th>Address</th><td><input type="text" name="address" value="<%=pojo.getAddress()%>"></td></tr>
	</table>
	</form>	
	<button style="margin-left:920px;"class="profilebutton" value="save"  name="action" >Save</button>
${message}

</body>
</html>
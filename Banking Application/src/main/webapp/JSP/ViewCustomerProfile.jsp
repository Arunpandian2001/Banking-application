<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer profile</title>
</head>
<body>

	<%@ page import="pojo.CustomerPojo "%>
	<%
	
	CustomerPojo pojo = (CustomerPojo) request.getAttribute("userDetails");
	%>
	<h1><%=pojo.getName()%></h1>
	<h2><%=pojo.getId()%></h2>
	<h3>
		D.O.B :
		<%=pojo.getDob()%></h3>
	<h3>
		Mobile Number:
		<%=pojo.getMobile()%></h3>
	
	<h3>
		E-mail:
		<%=pojo.getEmail()%></h3>
	
	<h3>
		Aadhar Number :<%=pojo.getAadhar()%></h3>
	<h3>
		PAN Number :<%=pojo.getPanNumber()%></h3>
	<h3>
		Address :<%=pojo.getAddress()%></h3>


	<button>Edit</button>

</body>
</html>
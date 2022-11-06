<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer profile</title>
</head>
<body>

<form action="<%= request.getContextPath()%>/ProcessServlet" method="get" target="customerFrame">

<%@ page import="pojo.CustomerPojo "%>
	<%
	
	CustomerPojo pojo = (CustomerPojo) request.getAttribute("userDetails");
	%>
	<h1><%=pojo.getName()%></h1>
	<h2><%=pojo.getId()%></h2>
	<h3>
		D.O.B :<input type="date" name="dob" value="<%=pojo.getDob()%>"><button value="save"  name="action">Save</button>
	<h3>
		Mobile Number:<input type="text"name="mobile" value="<%=pojo.getMobile()%>">	<button value="save"  name="action">Save</button>
		</h3>
	
	<h3>
		E-mail:<input type="text" name="email" value="<%=pojo.getEmail()%>">	<button value="save"  name="action">Save</button>
		</h3>
	
	<h3>
		Aadhar Number :"<%=pojo.getAadhar()%>"
		</h3>
	<h3>
		PAN Number :"<%=pojo.getPanNumber()%>"
		</h3>
	<h3>
		Address :<input type="text" name="address" value="<%=pojo.getAddress()%>">	<button name="action" value="save">Save</button>
		</h3>



</form>	
${message}

</body>
</html>
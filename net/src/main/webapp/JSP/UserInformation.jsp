<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer information</title>
</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ page import="java.util.Map" import="pojo.CustomerPojo"%>

	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post" target="adminFrame">
		<label>User id </label> <input type="number" min=7001
			name="customerid"> <br>
		<button name="action" value="searchuser">Search</button>

	</form>
	<table>
		<tr>
			<th>User id</th>
			<th>Name</th>
			<th>DOB</th>
			<th>MOBILE</th>
			<th>EMAIL</th>
			<th>Address</th>
			<th>Aadhar number</th>
			<th>PAN number</th>
			<th>Status</th>


		</tr>
	<c:forEach var="element" items="${customerdetails}">
		<tr>
		<td>${element.getCustomerId()}</td>
		<td>${element.getName()}</td>
		<td>${element.getDob()}</td>
		<td>${element.getMobile()}</td>
		<td>${element.getEmail()}</td>
		<td>${element.getAddress()}</td>
		<td>${element.getAadhar()}</td>
		<td>${element.getPanNumber()}</td>
		<td>${element.getStatus()}</td>
		
		</tr>
	</c:forEach>

	</table>
${message}

</body>


</html>
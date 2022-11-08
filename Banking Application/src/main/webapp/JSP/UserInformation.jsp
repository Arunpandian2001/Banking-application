<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer information</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Admin.css">
	<style type="text/css"> 
	.hide{
	visiblity:hidden;
		display:none;
	
	}
	</style>
</head>
<body>

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ page import="java.util.Map" import="pojo.CustomerPojo"%>
${message}

	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post" target="adminFrame">
		<div class="searchbar"><label>Customer id </label> <input type="number" min=7001	name="customerid" placeholder="Enter Customer id"> 
		<button name="action" value="searchuser"><i class="fa fa-search" ></i>Search</button></div>

	</form>
	<table class="centertable ${hidetable}" >
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

</body>


</html>
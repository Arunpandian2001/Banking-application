<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Customer.css">
<style type="text/css"> 
	.hide{
	display:none;
	}
	</style>
</head>
<body>

<% String name=(String)session.getAttribute("name"); %>

	<h1 style="text-align:center;justify-content:center;">Welcome <%= name %></h1>
	
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<table class="centertable ${hidetable}">
<thead>
<tr>
<th>Account number</th>
<th>Balance</th>
<th>Status</th>

</tr>
</thead>
<tbody>
<c:forEach var="element" items="${accountmap}">
	<tr>
	<td> ${element.value.getAccountNumber()} </td>
		<td>${element.value.getBalance()}</td>
		<td>${element.value.getStatus()}</td>
	
	</tr>

</c:forEach>
</tbody>
</table>
	
	
</body>
</html>
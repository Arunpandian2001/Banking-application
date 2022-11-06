<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>

<% String name=(String)session.getAttribute("name"); %>

	<h1>Welcome <%= name %></h1>
	
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<table>
<tr>
<th>Account number</th>
<th>Balance</th>
<th>Status</th>

</tr>

<c:forEach var="element" items="${accountmap}">
	<tr>
	<td> ${element.value.getAccountNumber()} </td>
		<td>${element.value.getBalance()}</td>
		<td>${element.value.getStatus()}</td>
	
	</tr>

</c:forEach>

</table>
	
	
</body>
</html>
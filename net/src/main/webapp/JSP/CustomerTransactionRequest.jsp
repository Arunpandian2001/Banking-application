<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pending Requests</title>
</head>
<body>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@  page import="java.util.Map" %>
	${message}

	<table>

		<tr>
		<th>Account number</th>
			<th>Reference id</th>
			<th>Amount</th>
			<th>Requested time</th>
			<th>Status</th>

		</tr>
		<c:forEach var="element" items="${requestmap}">
	<tr>
		<td> ${element.value.getAccountNumber()} </td>
		<td> ${element.value.getReferenceId()} </td>
	<td>${element.value.getAmount()}</td>
	<td><jsp:useBean id="day" class="java.util.Date"/>
	<c:set target="${day}" property="time" value="${element.value.getRequestedTime()}"></c:set>${day}
		</td>
		<td>${element.value.getStatus()}</td>
	
	</tr>

</c:forEach>
		
	</table>

</body>
</html>
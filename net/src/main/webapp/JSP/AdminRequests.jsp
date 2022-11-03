<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Available requests | Admin</title>
</head>
<body>

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<%@ page import="pojo.RequestPojo"%>

	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post" target="adminFrame">
		<label>User id </label> <input type="number" min=7001
			name="customerid" required> <br> <label>Account
			number </label> <input type="number" min=60000000 name="accountnumber">
		<br>

		<button name="action" value="accepttransaction">Accept</button>
		<button name="action" value="rejecttransaction">Reject</button>


	</form>
	
		${message}
	
	<table>
		<tr>
			<th>CUSTOMER_ID</th>
			<th>ACCOUNT_NUMBER</th>
			<th>REFERENCE_ID</th>
			<th>AMOUNT</th>
			<th>REQUESTED TIME</th>
			<th>PROCESSED TIME</th>
			<th>STATUS</th>



		</tr>
		<c:forEach var="element" items="${pendingrequestmap}">
			<tr>
				<td>${element.value.getCustomerId()}</td>
				<td>${element.value.getAccountNumber()}</td>
				<td>${element.value.getReferenceId()}</td>
				<td>${element.value.getAmount()}</td>
				<td>${element.value.getRequestedTime()}</td>
				<td>${element.value.getProcessdeTime()}</td>
				<td>${element.value.getStatus()}</td>


			</tr>
		</c:forEach>

	</table>



</body>
</html>
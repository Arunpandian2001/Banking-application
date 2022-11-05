<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transaction requests</title>
</head>
<body>

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<%@ page import="pojo.TransactionPojo"%>

	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post" target="adminFrame">
		<label>User id </label> <input type="number" min=7001
			name="customerid" required placeholder="Enter Customer Id"> <br> <label>Account
			number </label> <input type="number" min=60000000 name="accountnumber" placeholder="Enter Account number">
		<br>
		<button name="action" value="searchtransaction">Search</button>

	</form>
	<table>
		<tr>
			<th>REFERENCE_ID</th>
			<th>CUSTOMER_ID</th>
			<th>ACCOUNT_NUMBER</th>
			<th>SECONDARY_ACCOUNT</th>
			<th>TRANSACTION_ID</th>
			<th>TRANSACTION_TYPE</th>
			<th>MODE_OF_TRANSACTION</th>
			<th>AMOUNT</th>
			<th>TIME</th>
			<th>CLOSING_BALANCE</th>
			<th>STATUS</th>

		</tr>
		<c:forEach var="element" items="${transactiondetails}">
			<tr>
				<td>${element.value.getReferenceId()}</td>
				<td>${element.value.getCustomerId()}</td>
				<td>${element.value.getAccountNumber()}</td>
				<td>${element.value.getSecondary()}</td>
				<td>${element.value.getTransactionId()}</td>
				<td>${element.value.getTransactionTypes()}</td>
				<td>${element.value.getMode()}</td>
				<td>${element.value.getAmount()}</td>
				<td><jsp:useBean id="day" class="java.util.Date" /> <c:set
						target="${day}" property="time"
						value="${element.value.getTimeInMillis()}"></c:set>
				<fmt:formatDate value="${day}" pattern="dd-MM-yyyy hh:mm:ss "/>
						</td>
				<td>${element.value.getClosingBalance()}</td>
				<td>${element.value.getStatus()}</td>

			</tr>
		</c:forEach>

	</table>

	${message}



</body>
</html>
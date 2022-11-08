<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transaction requests</title>
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
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<%@ page import="pojo.TransactionPojo"%>

	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post" target="adminFrame">
		<label>User id </label> <input type="number" min=7001
			name="customerid"  placeholder="Enter Customer Id" value="<%=request.getAttribute("searchid")%>"> <br> 
			
			<button name="action" value="displayaccounttransaction">Specific account</button>
			
			<label>Account Number</label><select name="Accounts" id="Accounts">
			<option value="" hidden="hidden">Select account</option>
			<c:forEach var="element" items="${accountlist}">
			<option value="${element}">${element}</option>
			</c:forEach>
			</select><br>
			
		
		<button name="action" value="searchtransaction">Search</button>

	</form>
	<table class="transactiontable ${hidetable}">
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
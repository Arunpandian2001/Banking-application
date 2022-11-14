<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transaction requests</title>
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
	<h1>TRANSACTION DETAILS</h1>
	<%@ page import="pojo.TransactionPojo"%>

	<div class="search">
	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post" target="adminFrame">
		<label>User id </label> <input type="number" min=7001
			name="customerid"  placeholder="Enter Customer Id" value="<%=request.getAttribute("searchid")%>"> &nbsp;
			
			<button id="specificbutton" name="action" value="displayaccounttransaction">Specific account</button>&nbsp;
			
			<div class="specific ${hidedropdown}"><select name="Accounts" id="Accounts">
			<option value="" hidden="hidden">Select account</option>
			<c:forEach var="element" items="${accountlist}">
			<option value="${element}">${element}</option>
			</c:forEach>
			</select></div>
			
		
		<button id="search" name="action" value="searchtransaction">Search</button>

	</form>
	</div>
	<table class="transactiontable ${hidetable}">
		<thead><tr>
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

		</tr></thead>
		<tbody><c:forEach var="element" items="${transactiondetails}">
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
		</c:forEach></tbody>

	</table>

<div id="message">${message}</div>	



</body>
</html>
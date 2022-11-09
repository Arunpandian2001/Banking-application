<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transaction details</title>

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Customer.css">
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
	<h1 style="text-align:center;justify-content:center;">TRANSACTION DETAILS</h1>
<div id="transaction">
	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post">
		
		<label>Accounts</label>
		<select name="Accounts" id="Accounts">
						<option value="" hidden="hidden">Select account number</option>
		
			<c:forEach var="element" items="${accountlist}">
				<option value="${element}">${element}</option>
			</c:forEach>
		</select>
		<button name="action" value="transactiondetails">Search</button>
		

		
	</form>
</div>
<div id="message">
${message}
</div>

	<table class="transactiontable ${hidetable }">
		<thead><tr>
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
		</thead>
<tbody>
	<c:forEach var="element" items="${transaction}">
			<tr>
				<td>${element.value.getAccountNumber()}</td>
				<td><c:if test="${element.value.getSecondary()==0}">-</c:if>
				<c:if test="${element.value.getSecondary()>0}">${element.value.getSecondary()}</c:if></td>
				<td>${element.value.getTransactionId()}</td>
				<td>${element.value.getTransactionTypes()}</td>
				<td>${element.value.getMode()}</td>
				<td>${element.value.getAmount()}</td>
				<td><jsp:useBean id="day" class="java.util.Date" />
  			  <c:set target="${day}" property="time" value="${element.value.getTimeInMillis()}"/> 
  			  <fmt:formatDate value="${day }" pattern="dd-MM-yyyy hh:mm:ss "/>
  			  </td>
  			   <td>${element.value.getClosingBalance()}</td>
				<td>${element.value.getStatus()}</td>
			</tr>
		</c:forEach>
</tbody>
	

	</table>

</body>
</html>
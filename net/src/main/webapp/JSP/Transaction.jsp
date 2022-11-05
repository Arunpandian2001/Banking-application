<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transaction details</title>
</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<h1>Transaction details</h1>

	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post">
		<label> Accounts</label> <select name="Accounts" id="Accounts">
						<option value="" hidden="hidden">Select account number</option>
		
			<c:forEach var="element" items="${accountlist}">
				<option value="${element}">${element}</option>
			</c:forEach>
		</select><br>

		<button name="action" value="transactiondetails">Proceed</button>
	</form>

${message}
	<table>
		<tr>
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

		<c:forEach var="element" items="${transaction}">
			<tr>
				<td>${element.value.getAccountNumber()}</td>
				<td>${element.value.getSecondary()}</td>
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

	</table>

</body>
</html>
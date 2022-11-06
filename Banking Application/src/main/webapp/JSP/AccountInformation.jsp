<!DOCTYPE html><!-- Admin  -->
<html>
<head>
<meta charset="UTF-8">
<title>Accounts info</title>
</head>
<body>

	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post" target="adminFrame">
		<label>Customer id </label> <input type="number" min=7001
			name="customerid"  placeholder="Enter Customer Id" value="<%=request.getAttribute("searchid")%>"> <br>
			<button name="action" value="displayaccount">Specific account</button>
			
			<label>Account Number</label><select name="Accounts" id="Accounts">
			<option value="" hidden="hidden">Select account</option>
			<c:forEach var="element" items="${accountlist}">
			<option value="${element}">${element}</option>
			</c:forEach>
			</select><br>
				
		<button name="action" value="accountsearch">Search</button>
	</form>
	<table>
		<tr>
		<th>CUSTOMER_ID</th>
			<th>ACCOUNT_NUMBER</th>
			<th>ACCOUNT_TYPE</th>
			<th>BALANCE</th>
			<th>BRANCH</th>
			<th>STATUS</th>
			
		</tr>
	<c:forEach var="element" items="${accountdetails}">
		<tr>
		<td>${element.getCustomerId()}</td>
		<td>${element.getAccountNumber()}</td>
		<td>${element.getAccountType()}</td>
		<td>${element.getBalance()}</td>
		<td>${element.getBranch()}</td>
		<td>${element.getStatus()}</td>
			
		</tr>
	</c:forEach>

	</table>
	
	${message}
	
</body>
</html>
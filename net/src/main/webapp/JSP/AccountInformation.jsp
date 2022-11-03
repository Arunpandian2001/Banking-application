<!DOCTYPE html><!-- Admin  -->
<html>
<head>
<meta charset="UTF-8">
<title>Accounts info</title>
</head>
<body>

	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ page  import="pojo.Accounts_pojo"%>

	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post" target="adminFrame">
		<label>User id </label> <input type="number" min=7001
			name="customerid" required> <br>
			<label>Account number </label> <input type="number" min=60000000
			name="accountnumber" > <br>
		<button name="action" value="accountsearch">Search</button>
<!-- 		<button name="action" value="allaccount">Show all</button>
 -->
	</form>
	<table>
		<tr>
			<th>ACCOUNT_NUMBER</th>
			<th>ACCOUNT_TYPE</th>
			<th>BALANCE</th>
			<th>BRANCH</th>
			<th>STATUS</th>
			
		</tr>
	<c:forEach var="element" items="${accountdetails}">
		<tr>
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
<!DOCTYPE html>
<!-- Admin  -->
<html>
<head>
<meta charset="UTF-8">
<title>Accounts info</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/CSS/Admin.css">
<style type="text/css">
.hide {
	visiblity: hidden;
	display: none;
}
</style>
</head>
<body>


	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1>ACCOUNT DETAILS</h1>
	<div class="search">
		<form action="<%=request.getContextPath()%>/ProcessServlet"	method="post" target="adminFrame">
			<label>Customer id </label> <input type="number" min=7001
				name="customerid" value="<%=request.getAttribute("searchid")%>">&nbsp;
			<button id="specificbutton" name="action" value="displayaccount">Specific
				account</button>&nbsp;

			<div class="specific ${hidedropdown}"><select name="Accounts" id="Accounts">
				<option value="" hidden="hidden">Select account</option>
				<c:forEach var="element" items="${accountlist}">
					<option value="${element}">${element}</option>
				</c:forEach>
			</select></div>

			<button id="search" name="action" value="accountsearch">Search</button>
		</form>

	</div>
	<table class="centertable ${hidetable}">
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

	<div id="message">${message}</div>


</body>
</html>
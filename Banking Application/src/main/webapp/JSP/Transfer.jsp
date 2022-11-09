<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Money transfer</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Customer.css">

</head>
<body>

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h1 style="text-align:center;justify-content:center;">MONEY TRANSFER</h1>

	<div id="transfer">
	
	<form action="<%= request.getContextPath() %>/ProcessServlet" method="post">
	<table class="deposittable">
	<tr>
	<th>Accounts</th><td><select name="Accounts" id="Accounts">
		<option value="" hidden="hidden">Select Account</option>
				<c:forEach var="element" items="${accountlist}">
					<option value="${element}">${element}</option>
				</c:forEach>
			</select></td>
	</tr>
	<tr><th>Receiver account number</th><td><input type="number" min="60000000" name="receiver" required></td></tr>
	<tr><th>Amount</th><td><input type="number" min="1" name="amount" required placeholder="Enter amount"></td></tr>
	<tr><th>Password</th><td><input type="password" name="password" required placeholder="Enter password"></td></tr>
	
	</table>
	
	
	<button name="action" value="transfer">Proceed</button>
	
	
	</form>
	
	<div id="message">
	${message}
	</div>
	<div id="errormessage">${receivermessage}</div>
	
	
	</div>
</body>
</html>
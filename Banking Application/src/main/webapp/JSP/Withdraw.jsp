<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Withdraw</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Customer.css">

</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<h1>WITHDRAW</h1>

	<div class="deposit">

		<form action="<%= request.getContextPath() %>/ProcessServlet"
			method="post">
			<table class="deposittable">
			<tr><th> Accounts</th>
			<td><select name="Accounts" id="Accounts">
				<option value="" hidden="hidden">Select account number</option>
				<c:forEach var="element" items="${accountlist}">
					<option value="${element}">${element}</option>
				</c:forEach>
			</select> </td></tr>
			<tr><th>Amount</th>
			<td><input type="number" min=1
				name="amount" min="1" required placeholder="Enter Amount"></td></tr>
				
			</table>
			<button class="profilebutton" name="action" value="withdraw">Withdraw</button>
		
		</form>
	</div>
		<div id="${msgtype}">${message}</div>
	
</body>
</html>
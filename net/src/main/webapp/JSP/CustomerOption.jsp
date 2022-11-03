<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="<%= request.getContextPath() %>/ProcessServlet" method="get" target=customerFrame>
	<button name="action" value=customer>Home</button>
	</form>
	<br>
	<br>
	<br>
	<br>
	<form action="<%= request.getContextPath() %>/ProcessServlet" method="get" target=customerFrame>

		<button name="action" value=CustomerProfile>Profile</button><br>
		<button name="action" value=CustomerAccounts>Account Details</button><br>
		<button name="action" value=ToDeposit>Deposit</button><br>
		<button name="action" value=ToWithdraw>Withdraw</button><br>
		<button name="action" value=ToTransfer>Transfer</button><br>
		<button name="action" value=ToTransactionDetails>Transaction details</button><br>
		<button name="action" value=CustomerTransactionRequests>Pending Transaction Requests</button><br>
		
	</form>
	<form action="<%= request.getContextPath() %>/ProcessServlet" method="get" target="_parent">
			<button name="action" value=logout>Logout</button><br>
	
	</form>
	
</body>
</html>
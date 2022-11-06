<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer</title>
</head>
<body>
<!-- <iframe src="JSP/CustomerOption.jsp" height="900px"></iframe>
 -->
 
 <form action="<%= request.getContextPath() %>/ProcessServlet" method="get" target=customerFrame>
	<button name="action" value=customer>Home</button>&emsp;
	<button name="action" value=CustomerProfile>Profile</button>&emsp;
		<button name="action" value=CustomerAccounts>Account Details</button>&emsp;
		<button name="action" value=ToDeposit>Deposit</button>&emsp;
		<button name="action" value=ToWithdraw>Withdraw</button>&emsp;
		<button name="action" value=ToTransfer>Transfer</button>&emsp;
		<button name="action" value=ToTransactionDetails>Transaction details</button>&emsp;
		<button name="action" value=CustomerTransactionRequests>Pending Transaction Requests</button>&emsp;
		<button name="action" value=ToCustomerAccountRequests>Account activation Request</button>&emsp;
	
	</form>
	
	<form action="<%= request.getContextPath() %>/ProcessServlet" method="get" target="_parent">
			<button name="action" value=logout >Logout</button>&emsp;
	
	</form>
	
	 <iframe src="JSP/Customer.jsp" name="customerFrame" height ="900px" width="100%"  ></iframe>
	 



</body>
</html>
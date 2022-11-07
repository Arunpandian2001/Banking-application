<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer</title>
</head>
<body>


	<div id="options">
		<form action="<%=request.getContextPath()%>/ProcessServlet"
			method="get" target=customerFrame>
			<ul>
				<li><div class="menu"><button name="action" value=customer>Home</button></div></li>
				<li><div class="menu"><button name="action" value=CustomerProfile>Profile</button></div></li>
				<li><div class="menu"><button name="action" value=CustomerAccounts>Account
						Details</button></div></li>
					<li><button name="action" value=ToDeposit>Deposit</button><div class=menu></div></li>
				
					<li>	<div class=menu><button name="action" value=ToWithdraw>Withdraw</button></div></li>
				
					<li>	<div class=menu><button name="action" value=ToTransfer>Transfer</button></div></li>
				
					<li>	<div class=menu><button name="action" value=ToTransactionDetails>Transaction
						details</button></div></li>
				
					<li>	<div class=menu><button name="action" value=CustomerTransactionRequests>Pending
						Transaction Requests</button></div></li>
						<li><div class=menu><button name="action" value=ToCustomerAccountRequests>Account activation Request</button></div>
					<li>
					<div class=menu><button name="action" value=ToChangePasswordCustomer>Change
						Password</button></div></li>
				
			</ul>
	</form>

	
	<div>
	<form action="<%=request.getContextPath()%>/ProcessServlet"
				method="get" target="_parent">
				<ul>
			<li><button name="action" value=logout>Logout</button>
	
				</ul>
	</form>
	</div>
	 </div>
	 <iframe src="JSP/Customer.jsp" name="customerFrame" height="900px"
		width="100%"></iframe>
	 



</body>
</html>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin menu</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="get" target=adminFrame>
		<button name="action" value=admin>Home</button>
	</form>
	<br>
	<br>
	<br>
	<br>

	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="get" target=adminFrame>

		<button name="action" value=AdminProfile>Profile</button>
		<br>
		<button name="action" value=UserInformation>User
			Details</button>
		<br>
		<button name="action" value=AccountInformation>Account
			details</button>
		<br>
		<button name="action" value=AdminTransaction>Transaction
			details</button>
		<br>
		<button name="action" value=PendingWithdrawRequests>Approve Withdraw requests</button>
		<br>
		
		<button name="action" value=ToCreateCustomer>Create customer</button>
		<br>
		
		<button name="action" value=ToCreateAccount>Create Account</button>
		<br>
		
		<button name="action" value=ToProcessAccountActivation>Account activation</button>
		<br>

	</form>
	<form action="../ProcessServlet" method="get" target=_parent>
		<button name="action" value=logout>Logout</button>
		<br>

	</form>


</body>
</html>
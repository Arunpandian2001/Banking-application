<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
</head>
<body>
<!-- <iframe src="JSP/AdminOption.jsp" height="900px"></iframe>    <!-- jsp include tag -->

<div >
<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="get" target=adminFrame ">
		<button name="action" value=admin>Home</button>
		&emsp;
		
		<button name="action" value=AdminProfile>Profile</button>
		&emsp;
		
		<button name="action" value=UserInformation>User
			Details</button>
		&emsp;
		<button name="action" value=AccountInformation>Account
			details</button>
		&emsp;
		<button name="action" value=AdminTransaction>Transaction
			details</button>
		&emsp;
		<button name="action" value=PendingWithdrawRequests>Approve Withdraw requests</button>
		&emsp;
		
		<button name="action" value=ToCreateCustomer>Create customer</button>
		&emsp;
		
		<button name="action" value=ToCreateAccount>Create Account</button>
		&emsp;
		
		<button name="action" value=ToProcessAccountActivation>Account activation</button>
		&emsp;
	</form>

	<form action="<%=request.getContextPath()%>/ProcessServlet" method="get" target=_parent>
		<button name="action" value=logout >Logout</button>
		&emsp;

	</form>
</div>

<iframe src="JSP/Admin.jsp" name="adminFrame" height="907px" width=100%></iframe>
</body>
</html>
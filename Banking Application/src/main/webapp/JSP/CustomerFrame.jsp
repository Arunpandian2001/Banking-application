<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/CSS/CustomerMenu.css">
</head>
<body style="overflow: hidden">
	<header>
		<div class="container">
			<div class="menu-1">
				<img src="<%=request.getContextPath()%>/Images/web.png" width="120%"
					height="120%">
			</div>
			<div class="menu-2">
				<h1
					STYLE="text-align: center; justify-content: center; color: #2b6199;">AP
					INTERNATIONAL BANK</h1>

				<nav>
					<form action="<%=request.getContextPath()%>/ProcessServlet"
						method="get" target=customerFrame>
						<ul class="menu">
							<li><button class="menubutton" name="action" value=customer>Home</button></li>
							<li><button class="menubutton" name="action"
									value=CustomerProfile>Profile</button></li>
							<li><button class="menubutton" name="action"
									value=CustomerAccounts>Account Details</button></li>
							<li><button class="menubutton" name="action" value=ToDeposit>Deposit</button></li>

							<li>
								<button class="menubutton" name="action" value=ToWithdraw>Withdraw</button>
							</li>

							<li>
								<button class="menubutton" name="action" value=ToTransfer>Transfer</button>
							</li>

							<li>
								<button class="menubutton" name="action"
									value=ToTransactionDetails>Transaction details</button>
							</li>

							<li>
								<button class="menubutton" name="action"
									value=CustomerTransactionRequests>Pending Transaction</button>
							</li>
							<li><button class="menubutton" name="action"
									value=ToCustomerAccountRequests>Account activation</button>
							<li>
								<button class="menubutton" name="action"
									value=ToChangePasswordCustomer>Change Password</button>
							</li>

						</ul>
					</form>
				</nav>
			</div>
			<div class="menu-3">
				<form action="<%=request.getContextPath()%>/ProcessServlet"
					method="get" target="_parent">
					<ul>
						<li><button class="logoutbutton" name="action" value=logout>Logout</button>
					</ul>
				</form>
			</div>
		</div>
	</header>


	<iframe src="JSP/Customer.jsp" name="customerFrame" height="765px"
		width="99%"></iframe>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Menu.css">
<body style=overflow:hidden>
</head>
<body>
<header>
<div class=container>
<div class="menu-1"><img  src="<%=request.getContextPath()%>/Images/web.png" width="120%" height="120%"></div>
<div class="menu-2"><nav>
<h1 STYLE="text-align:center;justify-content:center;color:green">AP INTERNATIONAL BANK</h1>
<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="get" target=adminFrame ">
		<ul class="menu">
		<li><button class="menubutton" name="action" value=admin>Home</button></li>
		<li><button class="menubutton" name="action" value=AdminProfile>Profile</button></li>
		<li><button class="menubutton" name="action" value=UserInformation>User
			Details</button></li>
		<li><button class="menubutton" name="action" value=AccountInformation>Account
			details</button></li>
		<li></li>
		<li><button class="menubutton" name="action" value=AdminTransaction>Transaction
			details</button></li>
		<li><button class="menubutton" name="action" value=PendingWithdrawRequests>Approve Withdraw requests</button></li>
		<li><button class="menubutton" name="action" value=ToCreateCustomer>Create customer</button></li>
		<li><button class="menubutton" name="action" value=ToCreateAccount>Create Account</button></li>
		<li><button class="menubutton" name="action" value=ToProcessAccountActivation>Account activation</button></li>
		<li><button class="menubutton" name="action" value=ToChangePasswordAdmin>Change password</button></li>
		</ul>
	</form>
</nav>
</div>
<div class="menu-3">
<form action="<%=request.getContextPath()%>/ProcessServlet" method="get" target=_parent>
	<ul>
	<li><button class="logoutbutton" name="action" value=logout >Logout</button></li>
	</ul>
	</form>
</div>
</div>
</header>

<iframe src="JSP/Admin.jsp" name="adminFrame" height="765px"
		width="99%"></iframe>
</body>
</html>	

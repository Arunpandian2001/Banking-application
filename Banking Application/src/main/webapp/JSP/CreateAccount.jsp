<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create account</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/CSS/Admin.css">

</head>
<body>
	<h1>CREATE ACCOUNT</h1>
	<div class="deposit">

		<form action="<%=request.getContextPath()%>/ProcessServlet"
			method="get">
			<table class="deposittable">
				<tr>
					<th>User Id</th>
					<td><input type="number" name="customerid" required min=7001></td>
				</tr>
				<tr>
					<th>Account type</th>
					<td><select name="account type" id="account type">
							<option value="" hidden="hidden">Select account</option>
							<option value="SAVINGS ACCOUNT">Savings account</option>
							<option value="CURRENT ACCOUNT">Current account</option>
							<option value="SALARY ACCOUNT">Salary account</option>
							<option value="FIXED DEPOSIT">Fixed deposit</option>
							<option value="RECURRING DEPOSIT">Recurring deposit</option>
					</select></td>
				</tr>
				<tr>
					<th>Branch</th>
					<td><select name="branch" id="branch">
							<option value="MADURAI">Madurai</option>
							<option value="KARAIKUDI">Karaikudi</option>
					</select></td>
				</tr>
				<tr>
					<th>Minimum Balance</th>
					<td><input type="number" required name="minimum balance"
						value="500" readonly></td>
				</tr>

			</table>

			<button class="profilebutton" name="action" value="CreateAccount">Proceed</button>


		</form>
	</div>
	<div id="message">${message}</div>

</body>
</html>
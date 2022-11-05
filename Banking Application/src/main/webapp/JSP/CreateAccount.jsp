<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create account</title>
</head>
<body>

	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="get">

			<label>Customer id : </label><input type="number" name="customerid" min=7001 placeholder="Enter customer id" required><br>
		<label>Account type :</label><select name="account type"
			id="account type">
			<option value="SAVINGS ACCOUNT">Savings account</option>
			<option value="CURRENT ACCOUNT">Current account</option>
			<option value="SALARY ACCOUNT">Salary account</option>
			<option value="FIXED DEPOSIT">Fixed deposit</option>
			<option value="RECURRING DEPOSIT">Recurring deposit</option>
		</select><br> 
		<label>Branch :</label><select name="branch" id="branch">
			<option value="MADURAI">Madurai</option>
			<option value="KARAIKUDI">Karaikudi</option>
		</select><br>
		<label>Minimum Balance</label><input type="number" required name="minimum balance" value="500" readonly><br>

		<button name="action" value="CreateAccount">Proceed</button>


	</form>
	${message}

</body>
</html>
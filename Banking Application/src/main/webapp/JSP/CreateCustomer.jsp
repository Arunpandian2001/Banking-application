<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create customer</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Admin.css">

</head>
<body>
<h1>CREATE CUSTOMER</h1>
<div class="deposit">

<form action="<%= request.getContextPath()%>/ProcessServlet" method="get">
<table class="deposittable">
<tr><th>Name</th><td><input type="text" name="name" required ></td></tr>
<tr><th>Date of Birth</th><td><input type="date" name="dob" required ></td></tr>
<tr><th>Mobile number</th><td><input type="tel" name="mobile" required ></td></tr>
<tr><th>E-mail</th><td><input type="email" name="email" required ></td></tr>
<tr><th>Address</th><td><input type="text" name="address" required ></td></tr>
<tr><th>Aadhar Number</th><td><input type="number" name="aadhar" required ></td></tr>
<tr><th>PAN Number</th><td><input type="text" name="pan" required ></td></tr>
<tr><th>Account type</th><td><select name="account type"
			id="account type">
				<option value="" hidden="hidden">Select account</option>
						<option value="SAVINGS ACCOUNT">Savings account</option>
			<option value="CURRENT ACCOUNT">Current account</option>
			<option value="SALARY ACCOUNT">Salary account</option>
			<option value="FIXED DEPOSIT">Fixed deposit</option>
			<option value="RECURRING DEPOSIT">Recurring deposit</option>
		</select></td></tr>
<tr><th>Branch</th><td><select name="branch" id="branch">
			<option value="MADURAI">Madurai</option>
			<option value="KARAIKUDI">Karaikudi</option>
		</select></td></tr>
<tr><th>Minimum Balance</th><td><input type="number" required name="minimum balance" value="500" readonly></td></tr>

</table>

<button class="profilebutton" name="action" value="CreateCustomer" >Proceed</button>


</form>
</div>
<div id="errormessage">${errormessage}</div>
<div id="message">${message}</div>


</body>
</html>
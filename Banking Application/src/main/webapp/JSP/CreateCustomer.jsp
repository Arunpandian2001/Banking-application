<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create customer</title>
</head>
<body>

<form action="<%= request.getContextPath()%>/ProcessServlet" method="get">

<label>Name :</label><input type="text" name="name" placeholder="Enter name"  required ><br>
<label>Date of Birth :</label><input type="date" name="dob" placeholder="Enter Date of Birth"  required ><br>
<label>Mobile number :</label><input type="tel" name="mobile" placeholder="Enter Mobile number"  required ><br>
<label>E-mail :</label><input type="email" name="email" placeholder="Enter E-mail id"  required ><br>
<label>Address :</label><input type="text" name="address" placeholder="Enter Address(District)"  required ><br>
<label>Aadhar Number :</label><input type="number" name="aadhar" placeholder="Enter Aadhar number"  required ><br>
<label>PAN Number :</label><input type="text" name="pan" placeholder="Enter PAN Card number"  required ><br>

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

<button name="action" value="CreateCustomer" >Proceed</button>


</form>
${errormessage}

${message}

</body>
</html>
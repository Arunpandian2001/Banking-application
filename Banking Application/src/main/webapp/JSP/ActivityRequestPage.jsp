<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Request Account Activation</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/CSS/Customer.css">

</head>
<body>

	<h1>REQUEST ACTIVATION</h1>


	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	
	<div class="deposit">
		<form action="<%=request.getContextPath()%>/ProcessServlet"
			method="post">
			<table class="deposittable">
				<tr>
					<th>Accounts</th>
					<td><select name="Accounts" id="Accounts">
							<option value="" hidden="hidden">Select account</option>
							<c:forEach var="element" items="${accountlist}">
								<option value="${element}">${element}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>Message</th>
					<td><textarea rows="5" cols="50" name="reqmessage"
							id="reqmessage"></textarea></td>
				</tr>
			</table>

			<button class="profilebutton" name="action" value="submitrequest">Submit</button>
		</form>
	</div>
<div id="message">${message}</div>
	<div id="errormessage">${errormessage}</div>


</body>
</html>

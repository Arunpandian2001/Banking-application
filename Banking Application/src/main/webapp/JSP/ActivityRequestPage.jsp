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

	<h1 style="text-align: center; justify-content: center;">Request
		Activation</h1>


	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<div id="message">${message}</div>
	<div id="errormessage">${errormessage}</div>

	<div id="requesttable">
		<form action="<%=request.getContextPath()%>/ProcessServlet"
			method="post">
			<table class="centertable">
				<tr>
					<th>Accounts</th>
					<td><select name="Accounts" id="Accounts">
							<option value="" hidden="hidden">Select account number</option>
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

			<button name="action" value="submitrequest">Submit</button>


		</form>


	</div>


</body>
</html>

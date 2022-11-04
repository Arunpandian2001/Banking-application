<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Money transfer</title>
</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<form action="<%= request.getContextPath() %>/ProcessServlet" method="post">
	<div>
		<label> Accounts</label> <select name="Accounts" id="Accounts">
				<c:forEach var="element" items="${accountlist}">
					<option value="${element}">${element}</option>
				</c:forEach>
			</select><br>
	</div>
	<div>
		<label>Receiver account number</label> <input type="number" min="60000000" name="receiver" required>
	</div>
	<div>
		<label>Amount</label><input type="number" min="1" name="amount" required placeholder="Enter amount">
	</div>
	<div><label>Password</label><input type="password" name="password" required placeholder="Enter password"></div>
	<div>
	<button name="action" value="transfer">Proceed</button>
	
	</div>
	</form>
	
	${message}
</body>
</html>
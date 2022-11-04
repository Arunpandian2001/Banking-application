<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Deposit / Withdraw</title>
</head>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<div>

		<form action="<%= request.getContextPath() %>/ProcessServlet"
			method="post">
			<label> Accounts</label> <select name="Accounts" id="Accounts">
				<c:forEach var="element" items="${accountlist}">
					<option value="${element}">${element}</option>
				</c:forEach>
			</select><br> <label>Amount</label><input type="number" min=1
				name="amount" min="1" required placeholder="Enter Amount"><br>
			<button name="action" value="deposit">Proceed</button>
		</form>

		${message}

	</div>
</body>
</html>
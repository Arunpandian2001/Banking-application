<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Request Activation</h1>

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<form action="<%= request.getContextPath() %>/ProcessServlet"
			method="post">
			<label> Accounts </label> <select name="Accounts" id="Accounts">
				<option value="" hidden="hidden">Select account number</option>
				<c:forEach var="element" items="${accountlist}">
					<option value="${element}">${element}</option>
				</c:forEach></select>
		<label>Message</label>
		
		<textarea rows="5" cols="50" name="reqmessage" id="reqmessage"></textarea><br>
			
		<button name="action" value="submitrequest">Submit</button>
		
		
		</form>

${message}
${errormessage}
	
</body>
</html>
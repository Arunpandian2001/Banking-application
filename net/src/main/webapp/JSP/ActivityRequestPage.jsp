<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

${message}

	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post" target="adminFrame">
		
		
		<label>User id </label> <input type="number" min=7001
			name="customerid" placeholder="Enter Customer Id"> <br>
			
			
			
			
		<label>Account number </label> <input type="number" min=60000000
			name="accountnumber" placeholder="Enter Account Number"> <br>

	</form>

</body>
</html>
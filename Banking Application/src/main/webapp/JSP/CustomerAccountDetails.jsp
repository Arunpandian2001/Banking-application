<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<table>
<tr>
<th>Account number</th>
<th>Account type</th>
<th>Balance</th>
<th>Branch</th>
<th>Status</th>

</tr>

<c:forEach var="element" items="${accountmap}">
	<tr>
	<td> ${element.value.getAccountNumber()} </td>
	<td>${element.value.getAccountType()}</td>
		<td>${element.value.getBalance()}</td>
		<td>${element.value.getBranch()}</td>
		<td>${element.value.getStatus()}</td>
	
	</tr>

</c:forEach>

</table>
</body>
</html>
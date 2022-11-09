<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Admin.css">
	<style type="text/css"> 
	.hide{
	visiblity:hidden;
	display:none;
	}
	</style>
</head>
<body>

<%
	String name=(String)session.getAttribute("name");
%>
	<h1 style="justify:center;text-align:center">Welcome <%= name %></h1>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<%@ page import="pojo.RequestPojo"%>
<table class="centertable ${hidetable}">
		<tr>
			<th>CUSTOMER_ID</th>
			<th>ACCOUNT_NUMBER</th>
			<th>REFERENCE_ID</th>
			<th>AMOUNT</th>
			<th>REQUESTED TIME</th>
			<th>STATUS</th>
			<th>ACTIONS</th>
			
		</tr>



		<c:forEach var="element" items="${pendingrequestmap}">

			<form action="<%=request.getContextPath()%>/ProcessServlet"
				method="post" target="adminFrame">
				<input type="hidden" value="${element.value.getReferenceId()}"
					name="referenceid">

				<tr>


					<td>${element.value.getCustomerId()}</td>
					<td>${element.value.getAccountNumber()}</td>
					<td>${element.value.getReferenceId()}</td>
					<td>${element.value.getAmount()}</td>
					<td><jsp:useBean id="requestedday" class="java.util.Date" /> <c:set
							target="${requestedday}" property="time"
							value="${element.value.getRequestedTime()}"></c:set>
		<fmt:formatDate value="${requestedday}" pattern="dd-MM-yyyy hh:mm:ss "/>
					</td>
					
					<td>${element.value.getStatus()}</td>
					<td><button name="action" class="acceptbutton" value="acceptinhome">Accept</button>
					<button name="action" class="rejectbutton" value="rejectinhome">Reject</button></td>


				</tr>

			</form>

		</c:forEach>
	</table>
	${message }
	
</body>
</html>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Available requests | Admin</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/Admin.css">

<style type="text/css"> 
	.hide{
	visiblity:hidden;
		display:none;
	
	}
	</style>
</head>
<body>

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<%@ page import="pojo.RequestPojo"%>

<h1>TRANSACTION REQUESTS</h1>
	<div class="request">
	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post" target="adminFrame">
		
		<label>User id </label> <input type="number" min=7001
			name="customerid" > 
		<select name="accountnumber" id="accountnumber" >
		<option hidden="hidden" value=""  > Select account number</option>
		
		<c:forEach var="element" items="${accountmap}">
		<option value="${element.key}" >${element.key}</option>
		</c:forEach>
		</select>
		<button id="accept" name="action" value="accepttransaction">Accept</button>
		<button id="reject" name="action" value="rejecttransaction">Reject</button>
		
	</form>
	</div>
	<table class="centertable">
		<thead><tr>
			<th>CUSTOMER_ID</th>
			<th>ACCOUNT_NUMBER</th>
			<th>REFERENCE_ID</th>
			<th>AMOUNT</th>
			<th>REQUESTED TIME</th>
			<th>PROCESSED TIME</th>
			<th>STATUS</th>
			<th>ACTION</th>

		</tr></thead>

		<tbody><c:forEach var="element" items="${pendingrequestmap}">

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
					<td><c:if test="${element.value.getProcessdeTime()>0}">
					<jsp:useBean id="processedday" class="java.util.Date" /> <c:set
							target="${processedday}" property="time"
							value="${element.value.getProcessdeTime()}"></c:set>${processedday}
									<fmt:formatDate value="${processedday}" pattern="dd-MM-yyyy hh:mm:ss "/>
					</c:if>
					</td>
					<td>${element.value.getStatus()}</td>
					<td><button class="acceptbutton" name="action" value="acceptintable">Accept</button>
					<button class="rejectbutton" name="action" value="rejectintable">Reject</button></td>


				</tr>

			</form>

		</c:forEach></tbody>
	</table>
<div id="message">${message}</div>
</body>
</html>
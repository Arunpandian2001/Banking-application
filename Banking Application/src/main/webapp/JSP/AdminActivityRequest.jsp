<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<%@ page import="pojo.ActivityPojo"%>

	${message}


	<form action="<%=request.getContextPath()%>/ProcessServlet"
		method="post" target="adminFrame">
		
		<label>Request Id</label> <input type="number" min=1
			name="requestid" placeholder="Enter Request Id"> <br>
		<button value="adminaccountactive" name="action">Activate </button>		
			<!-- <button value="adminaccountinactive" name="action">DeActivate </button> -->			
		<button value="adminaccountactiveall" name="action">Activate all</button>		
			<!-- <button value="adminaccountinactiveall" name="action">DeActivate all</button> -->			
		
	</form>

	<table>
		<tr>
		<th>REQUEST_ID</th>
			<th>ACCOUNT_NUMBER</th>
			<th>REQUEST MESSAGE</th>
			<th>REQUESTED TIME</th>
			<th>PROCESSED TIME</th>
			<th>STATUS</th>

		</tr>

		<c:forEach var="element" items="${pendingrequestmap}">

			<form action="<%=request.getContextPath()%>/ProcessServlet"
				method="post" target="adminFrame">
				<input type="hidden" value="${element.value.getRequestId()}"
					name="requestid">

				<tr>

					<td>${element.value.getRequestId()}</td>
					<td>${element.value.getAccountNumber()}</td>
					<td>${element.value.getMessage()}</td>
					<td><jsp:useBean id="requestedday" class="java.util.Date" /> <c:set
							target="${requestedday}" property="time"
							value="${element.value.getRequestedTime()}"></c:set>
		<fmt:formatDate value="${requestedday}" pattern="dd-MM-yyyy hh:mm:ss "/>
					<td><c:if test="${element.value.getProcessedTime()>0 }">
					<jsp:useBean id="processedday" class="java.util.Date" /> <c:set
							target="${processedday}" property="time"
							value="${element.value.getProcessedTime()}"></c:set>
		<fmt:formatDate value="${processedday}" pattern="dd-MM-yyyy hh:mm:ss "/>					
					</c:if></td>
					<td>${element.value.getStatus()}</td>
					<td><button name="action" value="acceptactivityintable">Activate</button></td>
				<!-- <td><button name="action" value="rejectactivityintable">Deactivate</button></td> -->	


				</tr>

			</form>

		</c:forEach>
	</table>



</body>
</html>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>

<%
	String name=(String)session.getAttribute("name");
%>
	<h1>Welcome <%= name %></h1>

	<!-- <h2>
		Pending Request Details
		</h1>
		<table>

			<tr>
				<th>Customer id</th>
				<th>Account number</th>
				<th>Referenece id</th>
				<th>Amount</th>
				<th>Requested time</th>
				<th>Processed time</th>
				<th>Status</th>

			</tr>

			<tr>
				<td>7001</td>
				<td>60000000</td>
				<td>AP1666728612491</td>
				<td>500</td>
				<td>1666728612491</td>
				<td>1666728671793</td>
				<td>Pending</td>

			</tr>

			<tr>
				<td>7001</td>
				<td>60000000</td>
				<td>AP1666741025085</td>
				<td>50</td>
				<td>1666728612491</td>
				<td>1666728671793</td>
				<td>Pending</td>

			</tr>

			<tr>
				<td>7001</td>
				<td>60000000</td>
				<td>AP1666741197938</td>
				<td>1000</td>
				<td>1666728612491</td>
				<td>1666728671793</td>
				<td>Pending</td>

			</tr>
		</table> -->
</body>
</html>

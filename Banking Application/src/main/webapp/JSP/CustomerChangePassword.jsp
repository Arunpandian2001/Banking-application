<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>Change Password</h1>

<form action="<%=request.getContextPath()%>/ProcessServlet" method="get" target="_parent">


<label>Old Password</label><input type="text" name="oldpassword" required><br>
<label>New Password</label><input type="text" name="newpassword" required ><br>
<label>Re-Enter Password</label><input type="text" name="reenterpassword" required><br>



<button name="action" value="customerchangepassword">Submit</button>

</form>
${message}
</body>
</html>
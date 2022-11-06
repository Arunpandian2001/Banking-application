<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin profile</title>
</head>
<body>

<form action="">

<%@ page import="pojo.UserPojo" %>
	<%
	UserPojo pojo=(UserPojo)request.getAttribute("userDetails"); %>
	
	<h1><%=pojo.getName()%></h1>
	<h2><%=pojo.getId()%></h2>
	<h3>
		D.O.B :<input type="text"name="dob" value="<%=pojo.getDob()%>"><button value="admin_save"  name="action">Save</button>
		</h3>
	<h3>
		Mobile Number:<input type="text"name=mobile value="<%=pojo.getMobile()%>"><button value="admin_save"  name="action">Save</button>
		</h3>

	<h3>
		E-mail:<input type="text"name="email" value="<%=pojo.getEmail()%>"><button value="admin_save"  name="action">Save</button>
		</h3>

	<h3>
		Address :<input type="text" name="address" value="<%=pojo.getAddress()%>"><button value="admin_save" name="action">Save</button>
		</h3>
	
</form>
	${message}
	
</body>
</html>
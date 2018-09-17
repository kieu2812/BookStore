<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>HTTP Status 403 - Access is denied</title>
</head>
<body>
	<h2>HTTP Status 403 - Access is denied</h2>
	<c:choose>
		<c:when test="${empty username}">
			<h3>You do not have permission to access this page!</h3>
		</c:when>
		<c:otherwise>
			<h2>Username : ${username}</h2>
			<h2 style="color:red;">You do not have permission to access this page!</h2>
		</c:otherwise>
	</c:choose> 
	<a href="${pageContext.request.contextPath}/login">Login Page</a>
</body>
</html>
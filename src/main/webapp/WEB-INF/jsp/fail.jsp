<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fail</title>
<link href="<c:url value='/resources/css/main.css' />" rel="stylesheet" />
</head>
<body>
	<div class="errorMessage">
		<c:if test="${not empty message }">
			<h3> ${message}</h3>
		</c:if>
	</div>
	
</body>
</html>
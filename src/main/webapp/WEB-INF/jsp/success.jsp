<%@page import="com.perscholas.model.ShoppingCart"%>
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>

 <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

 <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">  
 <link  href="<c:url value='/resources/css/main.css' />"  rel="stylesheet"/>

	<title>Successful Login</title>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		
		<c:if test="${ not empty message}">
			<div class="successMessage"><h3>${message }</h3></div>
		</c:if>
	</div> <%-- Container --%>		
	<jsp:include page="footer.jsp"></jsp:include>


	  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>  
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>



</html>

	

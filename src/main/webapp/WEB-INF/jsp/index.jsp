<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>

<link href="<c:url value="/resources/css/bootstrap.min.css"/>"  rel="stylesheet"/>

<c:url value="/resources/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

</head>
<body>


	<div class="container">

		<div class="starter-template">
			<h1 ><img src="<c:url value="/resoures/images/978-1787285651.jpg"/>"/></h1>
			<h1 ><img src="<c:url value="file:///E:/images/978-1787285651.jpg"/>"/></h1>
			<h1 ><img src="<c:url value="E:\images\978-1787285651.jpg"/>"/></h1>
			
		</div>

	</div>
	<!-- /.container -->

	<script type="text/javascript" 	src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	<script type="text/javascript" 	src="<c:url value="/resources/js/jquery.min.js"/>"></script>	

</body>

</html>
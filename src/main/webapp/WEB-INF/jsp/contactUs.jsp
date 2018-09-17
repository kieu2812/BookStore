<%@page import="com.perscholas.model.ShoppingCart"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<%-- <link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<link
	href="${pageContext.request.contextPath}/resources/css/main.css' />"
	rel="stylesheet" /> --%>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link href="<c:url value='/resources/css/main.css' />" rel="stylesheet" />

<title>Basic Theme</title>
<style>
	body{
		background-image:url("/images/bg-01.jpg");
	}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<div class="panel panel-warning" style="width: 500px; margin:0 auto;">
			<div class="panel-heading">
				<div class="titleForm"> Feel Free to Contact Us</div>
			</div>
		<div class="panel-body">
		<form:form id="contact-form" method="post" action="contactus/sendEmail" modelAttribute="contact">
			
			
			<div class="controls" >

				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<form:label path="firstName">First Name *</form:label> <form:input path="firstName"
								type="text"  class="form-control"
								placeholder="Please enter your firstname *" required="required"
								data-error="Firstname is required."/>
							<div class="errorMessage"></div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<form:label path="lastName">Last Name *</form:label> <form:input
								type="text" path="lastName"
								class="form-control" placeholder="Please enter your lastname *"
								required="required" data-error="Lastname is required."/>
							<div class="errorMessage"></div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<form:label path="email">Email *</form:label> <form:input
								type="email" path="email" class="form-control"
								placeholder="Please enter your email *" required="required"
								data-error="Valid email is required."/>
							<div class="errorMessage"></div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<form:label path="subject">Subject *</form:label> <form:input path="subject"
								type="text" class="form-control"
								placeholder="Please enter your subject *" required="required"
								data-error="Subject is required."/>
							<div class="errorMessage"></div>
						</div>
					</div>
				</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<form:label path="message">Message *</form:label>
								<form:textarea path="message" name="message" class="form-control"
									placeholder="Message for me *" rows="6" required="required"
									data-error="Please, leave us a message."></form:textarea>
								<div class="errorMessage"></div>
							</div>
						</div>
						<div class="col-md-12">
							<input type="submit" class="btn btn-success btn-send"
								value="Send message">
						</div>
					</div>
	
				</div>
		</form:form>
		</div>
	</div>
	</div>
	<%-- Container --%>
	<jsp:include page="footer.jsp"></jsp:include>
	<%--        <script src="<c:url value='/resources/js/jquery.min.js'/>" > </script>
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>" > </script>
   
   --%>
	<%-- 	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script> --%>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Forgot Password</title>

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link href="<c:url value='/resources/css/main.css' />" rel="stylesheet" />

</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">


		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		<div class="row">
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<div class="panel panel-info">
						<div class="panel-heading">
							<h2 class="text-center titleForm">Forgot Password</h2>
						</div>
						<div class="panel-body">
							<div class="text-center">
								<h3>
									<i class="fa fa-lock fa-4x"></i>
								</h3>
								
								<p>You can reset your password here.</p>
								<div class="panel-body">

									<form name="loginForm"
										action="${pageContext.request.contextPath}/sendemailforgotpass"
										method="POST">
										<fieldset>
											<div class="form-group">
												<label for="inputHorizontalWarning" class="col-form-label">Enter
													you email</label>



											</div>
											<div class="form-group">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-envelope color-blue"></i></span> <input
														type="email" class="form-control" name="email" required
														placeholder="example@gmail.com"
														oninvalid="setCustomValidity('Please enter a valid email address!')"
														onchange="try{setCustomValidity('')}catch(e){}" />
												</div>
											</div>
											<div class="form-group">

												<input class="btn btn-primary btn-block" type="submit"
													name="submit" value="Send to my email" />


											</div>
										</fieldset>
									</form>

							</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
	<%--
    	<script 		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script 		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script> --%>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
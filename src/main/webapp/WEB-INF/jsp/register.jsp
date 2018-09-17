<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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

<title>Registration</title>
	<script type="text/javascript">
		function validateForm(regForm){
			event.preventDefault();
			var err= document.getElementById("errorsMessage");
			console.log("validateForm() is call");
			if(document.getElementById("password").value!= document.getElementById("confirmPassword").value){
				err.style.display="block";
				err.value="Password and confirm password doesn't match";
				document.getElementById("confirmErr").textContent="Password and confirm password doesn't match"
				console.log("Password and confirm password doesn't match");
				//alert("Password and confirm password doesn't match");		
				return false;
			}
			
			return true;
		}
	</script>
</head>
<body>
	
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<div class="panel panel-success"  style="width:65%; margin: 0 auto;">
			<div class="panel-heading" align="center">
				<h4>
					<b><font color="black" style="font-family: fantasy;">
							Registration</font> </b>
				</h4>
			</div>
			
		
		<div class="panel-body">
			<div id="errorsMessage" class="errorMessage">
				
			</div>
			<c:if test="${not empty errors}">
				<div class="errorMessage" style="text-align: center;">${errors}</div>
			</c:if>
		
	
			<div class="form-group">
				<form:form id="regForm" modelAttribute="customerForm"
					action="registerProcess" method="post">
					<!--  onsubmit="return validateForm(regForm)" -->
					<div class="form-group row">
						<div class="col-md-4">
							<form:label path="customer.firstName"  class="col-form-label">First Name</form:label>
						
						</div>
						<div class="col-md-8">
							<form:input path="customer.firstName" pattern="^[a-zA-Z][a-zA-Z\\s]+$" required="true" class="form-control" />
							<form:errors path="customer.firstName" class="errorMessage"/>
						</div>
	
					</div>
	
					<div class="form-group row">
						<div class="col-md-4">
							<form:label path="customer.lastName"  class="form-label">Last Name</form:label>						
						</div>
						<div class="col-md-8">
							<form:input path="customer.lastName" pattern="^[a-zA-Z][a-zA-Z\\s]+$" required="true" class="form-control" />
							<form:errors path="customer.lastName"  class="errorMessage"/>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-md-4">
							<form:label path="customer.address"  class="form-label">Address</form:label>
						</div>
						<div class="col-md-8">
							<form:input path="customer.address" pattern="^[0-9]+[a-zA-Z\\s]+$" required="true" class="form-control" />
							<form:errors path="customer.address"  class="errorMessage"/>
						</div>
	
					</div>
	
					<div class="form-group row">
						<div class="col-md-4">
							<form:label path="customer.city">City</form:label>
						
						</div>
						<div class="col-md-8">
							<form:input path="customer.city" pattern="^[a-zA-Z\\s]+$" required="true" class="form-control" />
								<form:errors path="customer.city"  class="errorMessage"/>
						</div>
	
					</div>
					<div class="form-group row">
						<div class="col-md-4">
							<form:label path="customer.state">State</form:label>
						</div>
						<div class="col-md-8">
							<form:select path="customer.state" required="true"  items="${states}"  class="form-control" >							
							</form:select>							
							<form:errors path="customer.state"  class="errorMessage"/>
						</div>
	
					</div>
										<div class="form-group row">
						<div class="col-md-4">
							<form:label path="customer.zipcode">Zip Code</form:label>
						</div>
						<div class="col-md-8">
							<form:input path="customer.zipcode" required="true" maxlength="7" type="text" onkeypress='return event.charCode >= 48 && event.charCode <= 57' class="form-control" />
							<form:errors path="customer.zipcode"  class="errorMessage"/>
						</div>
	
					</div>
					
					<div class="form-group row">
						<div class="col-md-4">
							<form:label path="customer.email">Email</form:label>
						</div>
						<div class="col-md-8">
							<form:input path="customer.email" required="true" type="email" class="form-control" />
							<form:errors path="customer.email"  class="errorMessage"/>
						</div>
					
					</div>
					
					<div class="form-group row">
						<div class="col-md-4">
							<form:label path="password">Password</form:label>
						</div>
						<div class="col-md-8">
							<form:password path="password" pattern="[0-9a-zA-Z\\s]+$" required="true" maxLength="15" class="form-control" />
							<form:errors path="password"  class="errorMessage"/>
						</div>
					</div>
					
					<div class="form-group row">
						<div class="col-md-4">
							<label>Confirm Password</label>
						</div>
						<div class="col-md-8">
							<input type="password" id="confirmPassword"  pattern="[0-9a-zA-Z\\s]+$"  required maxLength="15" class="form-control" />
							
						</div>
						<div  class="errorMessage" id="confirmErr"></div>
					</div>
					<div class="form-group row">
						<div class="col-md-6">
						<form:button id="register" name="register" class="btn btn-success">Register</form:button>
						</div>
						<div class="col-md-6">
						<form:button id="cancel" name="cancel" class="btn btn-warning">Cancel</form:button>
						</div>
					</div>
				</form:form>
			</div>
			<%-- form --%>
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
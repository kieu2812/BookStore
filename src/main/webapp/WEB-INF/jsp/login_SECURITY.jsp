<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<html>
<head>
	<title>Login Page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"> 
	<link  href="<c:url value='/resources/css/main.css'/>"  rel="stylesheet"/>	
	<script type="text/javascript">
		function resetForm(){
			$('#username').val("");			
			$('#password').val("");
		}
	</script>
	<style type="text/css">
		body{
			background-image: url("<c:url value='/images/library-bg2.jpg'/>");
			background-repeat: no-repeat;
			
		}
	</style>
</head>
<body onload='document.loginForm.username.focus();'>

	<jsp:include page="header.jsp"></jsp:include>
	
	<div id="login-box">
  
		<h1 class="titleForm"> Login Form</h1>	
	

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		
		<form name='loginForm'
			action="login" method='POST'>
     
                 <div class="form-group">
                     
                     <label for="inputHorizontalWarning" class="col-form-label" >User Name</label> 
                     
                    
                 </div>
                 <div class="form-group">
                 	
                     
                    	 <input type="email" name="username" id="username"  class="form-control form-control-warning" id="inputHorizontalWarning" placeholder="name@example.com"/>
                 
                 </div>
                 <div class="form-group ">
                     <label for="inputHorizontalWarning" class="col-form-label"  >Password</label> 
                    
                 </div>
             	 <div class="form-group">
                     	<input type="password"  name="password"  id="password" class="form-control form-control-warning" maxlength="15"  />
                </div>
             	<a href="${pageContext.request.contextPath}/forgot-password">Forgot password?</a>

                 <input class="btn btn-primary btn-block" type="submit" name="submit" value="Sign In"/>
                 <input class="btn btn-primary btn-block" type="button" name="cancel" value="Cancel" onclick="resetForm()"/>
            
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			
		</form>
             
	</div>
	
   <jsp:include page="footer.jsp"></jsp:include>
   
   	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>  
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
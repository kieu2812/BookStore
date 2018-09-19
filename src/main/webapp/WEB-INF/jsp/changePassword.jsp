<%@page import="com.perscholas.model.ShoppingCart"%>
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
 <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">  
      <link  href="<c:url value='/resources/css/main.css' />"  rel="stylesheet"/>

	<title>Change password</title>
<script type="text/javascript">
		function validateForm(regForm){
			
			var list =  document.getElementsByTagName("SPAN");
			console.log("Length = "+ list.length);
			for(var i=0;i< list.length;i++){
				list[i].innerHTML="";
			}
			var oldPass= document.getElementById("password");
			var newPass =document.getElementById("newPass");
			var confirmPass= document.getElementById("confirmPass");	
			if(oldPass.value== newPass.value){
				document.getElementById("confirmPass").value="Old Password and new password must be different";

				
			}
			
			
			if(newPass.value!= confirmPass.value){

				
				document.getElementById("confirmErr").innerHTML="Password and confirm password doesn't match"

				return false;
			}
			
			return true;
		}
		function resetForm(){
			var list =  document.getElementsByTagName("SPAN");
			console.log("Length = "+ list.length);
			for(var i=0;i< list.length;i++){
				list[i].innerHTML="";
			}
			
			
			document.getElementById("userName").value="";
			document.getElementById("password").value="";
			document.getElementById("newPass").value="";
			document.getElementById("confirmPass").value="";
		}
	</script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<div id="login-box">
			<form:form id='changePassForm' modelAttribute="userChange"
			action="${pageContext.request.contextPath}/changePass" method='POST' onsubmit="return validateForm(this);">
	
				<div class="titleForm"> Change Password</div>
     			<c:if test="${not empty errors }">
	     			<div id="errorsMessage" class="errorMessage">
						${errors}	     			
	     			</div>
     			</c:if>
                 <div class="form-group row has-warning">
                     
                     <label for="inputHorizontalWarning" class="col-sm-4 col-form-label" >User Name</label> 
                     
                     <div class="col-sm-8">
                     
                    	 <form:input type="email" path="userName"  class="form-control form-control-warning"   required="true" placeholder="name@example.com"/>
                    	 <form:errors path="userName" class="errorMessage"/>
                 	</div>
                 </div>
                 <div class="form-group row has-warning">
                     <label for="inputHorizontalWarning" class="col-sm-4 col-form-label"  >Old Password</label> 
                     <div class="col-sm-8">
                     	<form:input type="password" path="password"  class="form-control form-control-warning"   required="true" pattern="[0-9A-Za-z]{6,15}"
                     		title="Password must be between 6 and 15. Must include numberic and alphabet !!"
                     	  />
                     	<form:errors path="password"  class="errorMessage"/>
                     </div>
                 </div>
             	                 <div class="form-group row has-warning">
                     <label for="inputHorizontalWarning" class="col-sm-4 col-form-label"  >New Password</label> 
                     <div class="col-sm-8">
                     	<form:input type="password"  path="newPass" class="form-control form-control-warning"  required="true" pattern="[0-9A-Za-z]{6,15}"  
                     	                     		title="New Password must be between 6 and 15. Must include numberic and alphabet !!"
                     	 />
                     	<form:errors path="newPass"  class="errorMessage"/>
                     </div>
                 </div>
             	                 <div class="form-group row has-warning">
                     <label for="inputHorizontalWarning" class="col-sm-4 col-form-label"  >Confirm New Password</label> 
                     <div class="col-sm-8">
                     	<form:input type="password"  path="confirmPass" class="form-control form-control-warning"   required="true" pattern="[0-9A-Za-z]{6,15}" 
                     		                     		title="Confirm Password must be between 6 and 15. Must include numberic and alphabet !!"
                     		
                     	 />
                     	<form:errors path="confirmPass"  class="errorMessage"/>
                     </div>
                     <div id="confirmErr" class="errorMessage"></div>
                 </div>
             	

                 <input class="btn btn-primary" type="submit" name="submit" value="Change password"/>
                   <input class="btn btn-primary" type="button" name="cancel" value="Cancel" onclick="resetForm()"/>
            
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
             </form:form>
             
		
	
	
	
	
	
	</div>
	
	
	
	
	</div> <%-- Container --%>		
	<jsp:include page="footer.jsp"></jsp:include>
	<%--        <script src="<c:url value='/resources/js/jquery.min.js'/>" > </script>
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>" > </script>
   
   --%>
<%-- 	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script> --%>

	  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>  
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import ="java.time.LocalDate"
	%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Add New Card for Payment</title>
	 <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">  
     <link  href="<c:url value='/resources/css/main.css' />"  rel="stylesheet"/>
</head>
<script type="text/javascript">
	function validateData(form){
		var month = form.expireMonth;
		var year = form.expireYear;
		var date = new Date();
		//get last date in the expire month and year.
		var lastExpireDate = new Date(year.value, month.value, 0);
		//get last date in current month and year 
		var lastCurrentDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	

		var error= document.getElementById("error");
		if(lastExpireDate<lastCurrentDay){
			
			error.innerHTML="Expire Date must be greater than current date. This card is expired.";
			return false;
		}
		return true;
	}
	
</script>


<body>
	
	<jsp:include page="header.jsp"></jsp:include>
	<br>
	<div class="container">
		<div class="panel panel-success">
			<div class="panel-heading" align="center">
				<h4>
					<b><font color="black" style="font-family: fantasy;">Add
							Customer Card Form</font> </b>
				</h4>
			</div>
			
		<c:if test="${not empty errors }">
			<div class="errorMessage">${ errors }</div>

		</c:if>
		<div class="errorMessage" >
			<span id="error"></span>
		</div>
			<div class="panel-body" style="width:80%; margin: 0 auto;">
				<form:form id="formCustomerCard" action="${pageContext.request.contextPath}/card/addCardProcess"  modelAttribute="customerCard" onsubmit="return validateData(this);"
						method="POST">
				 <div class="form-group row">
						<div class="col-sm-3">
		              		 <form:label path="cardNumber" >Card Number</form:label>  
		                </div>            
                         <div class="col-sm-9">                              
                         	<form:input class="form-control" required="true"  path="cardNumber" maxlength="20" type="text" onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
                        	<form:errors path="cardNumber"  class="errorMessage"/>
                         </div>                
		         </div>
                   <div class="form-group row">
                   		<div class="col-sm-3">
                                <form:label path="holderName">Holder Name</form:label>                                
                         </div>
                         <div class="col-sm-9">
                         <!--  pattern="\w*" -->
                                <form:input class="form-control" required="true"   path="holderName" />
                                <form:errors path="holderName"  class="errorMessage"/>       
                         </div>
                    </div><%-- 
					<div class="form-group  row">
						<div class="col-sm-3">
							<form:label path="expireDate">Expire Date</form:label>
						</div>
						<div class="col-sm-9">
							<form:input class="form-control" path="expireDate" />
						</div>
					</div>
					
					 --%>
					<div class="form-group  row">
						<div class="col-sm-3">
							<form:label path="expireMonth">Expire Month</form:label>
						</div>
						<div class="col-sm-9">
							<form:input class="form-control" required="true"  type="number" min="1" max="12" path="expireMonth" />
							<form:errors path="expireMonth"  class="errorMessage"/>
						</div>
					</div>
					<div class="form-group  row">
						<div class="col-sm-3">
							<form:label path="expireYear">Expire Year</form:label>
						</div>
						<div class="col-sm-9">
							<form:input class="form-control" required="true"  type="number" min="<%=LocalDate.now().getYear()%>"   max="<%=(LocalDate.now().getYear()+3)%>" path="expireYear" />
							<form:errors path="expireYear"  class="errorMessage"/>
						</div>
					</div>
					 <div class="form-group  row">
                            <div class="col-sm-3">
                                <form:label path="securityCode">Security Code</form:label>
                            </div>
                            <div class="col-sm-9">                                
                                <form:input class="form-control" required="true"  pattern="[0-9]{3,6}" path="securityCode" />
                                <form:errors path="securityCode"  class="errorMessage"/>       
                            </div>           		
                     </div>
						
					<div class="form-group row">	
					<div class="col-sm-6">
						<button id="submit" name="submit"
							
							class="form-control btn btn-success">
							<b>Submit</b>
						</button>
					</div>
					<div class="col-sm-6">
						<button id="cancel" name="cancel"
									class="form-control btn btn-warning">
							<b>Cancel</b>
						</button>
						
					</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	
	    	  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>  
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script>
	/* var security = document.getElementById("securityCode");

	security.addEventListener("input", function (event) {
		console.log("validate security code is call");
	  if (security.validity.typeMismatch) {
	    security.setCustomValidity("Security code must be number with length between 3 and 6");
	  } else {
	    security.setCustomValidity("");
	  }
	}); */
</script>
</body>
</html>
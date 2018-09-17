<%@page import="com.perscholas.model.ShippingAddress"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
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

<title>Shipping Address</title>
	<% ShippingAddress spadd= (ShippingAddress) request.getAttribute("shippingAddress"); 
	%>
<script>
	function validatorForm(form){
		//event.preventDefault();
		console.log("ValidateorForm is call")
/* 		var checkbox= document.getElementById("cbtheSame");
		var address = document.getElementById("address");
		var city = document.getElementById("city");
		var zipcode= document.getElementById("zipcode");
		var state =  document.getElementById("state"); 
*/
		var checkbox= form.cbtheSame;
		var address = form.address;
		var city = form.city;
		var zipcode= form.zipcode;
		var state =  form.state;
		// if checkbox is checked, then new address must be difference address with customer address
		if(!checkbox.checked){
			 var isTheSamAdd = address.value.trim() == "<%= spadd.getAddress()%>"; 
			 var isTheSamCity =  city.value.trim() == "<%= spadd.getCity()%>";
			 var isTheSamZipcode =  zipcode.value.trim()== "<%= spadd.getZipcode()%>";
			 var isTheSamState =  state.value.trim()== "<%= spadd.getState()%>";
			 if(isTheSamAdd && isTheSamCity && isTheSamZipcode && isTheSamState){
				 var error = document.getElementById("errors")
				 error.display="block";
				 error.value="The new address must be difference address with customer address";
				 return false;
			 }
		}
		return true;
	}
	function doResetForm(checkboxElem) {
		
		var address = document.getElementById("address");
		var city = document.getElementById("city");
		var zipcode= document.getElementById("zipcode");
		var state =  document.getElementById("state");
		  if (!checkboxElem.checked) {
			console.log("check box is unchecked");
			address.value="";
			city.value="";
			zipcode.value="";
			address.disabled = false;
			city.disabled = false;
			zipcode.disabled = false;
			state.disabled = false;
			address.focus();
			return;
			
		  } else {
			  console.log("check box is checked");
		  	  address.value= "<%= spadd.getAddress()%>" ;
			  city.value= "<%= spadd.getCity()%>";
			  zipcode.value= "<%= spadd.getZipcode()%>";
			  state.value= "<%= spadd.getState()%>";
			  address.disabled = true;
			  city.disabled = true;
			  zipcode.disabled = true;
			  state.disabled = true;
		  }
	}
	
</script>
</head>
<body >

	<jsp:include page="header.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="panel panel-success" >
			<div class="panel-heading" align="center">
				<h4>
					<b><font color="black" style="font-family: fantasy;">Shipping
							address</font> </b>
				</h4>
			</div>

			<c:if test="${not empty errors}">
				<div class="errorMessage">${errors }</div>
			</c:if>
			<span class="errorMessage" id="errors"></span>
			<div class="panel-body" align="center">

				<div class="container " style="margin-top: 5%;">

					<div class="panel panel-success" style="max-width: 45%;"
						align="left">

						<div class="panel-body">
							<form:form id="shipForm"
								action="${pageContext.request.contextPath}/billingAddress"
								modelAttribute="shippingAddress" method="POST" onsubmit="return validatorForm(this);">
								
							
									
									
								<input type="checkbox" name="cbtheSame" id="cbtheSame" checked value="checked" onchange="doResetForm(cbtheSame)"/>The same customer address
                       		 <div class="form-group">
									<form:label path="address">Address</form:label>
									<form:input class="form-control" required="true" path="address" disabled="true"
										/>
								</div>

								<div class="form-group">
									<form:label path="city">City</form:label>
									<form:input class="form-control" required="true" path="city" disabled="true"
									/>

								</div>

								<div class="form-group">
									<form:label path="state">State</form:label>
									
									<form:select path="state" required="true"  items="${states}" disabled="true" class="form-control" >							
									</form:select>
								</div>

								<div class="form-group">
									<form:label path="zipcode">Zip Code</form:label>
									<form:input class="form-control" required="true" maxlength="7" disabled="true"
										type="text"
										onkeypress='return event.charCode >= 48 && event.charCode <= 57'
										path="zipcode" />
								</div>

								<form:button id="submit" name="submit"
									style="width: 100%; font-size:1.1em;"
									class="btn btn-large btn btn-success btn-lg btn-block">
									<b>Use this address</b>
								</form:button>

							</form:form>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
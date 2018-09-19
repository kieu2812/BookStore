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

<title>Shopping Cart Confirmation</title>
<style>
input {
	disable: true;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<%
		double total =0;
	%>
		<h1 class="title">Shopping Cart Confirmation</h1>
		<c:if test="${not empty errors }">
			<div>${ errors }</div>

		</c:if>
			<!-- modelAttribute="myCart"> -->

			<%-- CUSTOMER INFORMATION ADDRESS --%>
			<c:if test="${not empty myCart.customer}">
				<div class="container">
					<div class="panel panel-info">
						<div class="panel panel-heading">
							<h3>Customer information:</h3>
						</div>
						<div class="panel panel-body">
							<div class="row">
								<div class="col-sm-4">Name:</div>
								<div class="col-sm-6">${ myCart.customer.firstName} &nbsp;
									${myCart.customer.lastName}</div>
							</div>
							<div class="row">
								<div class="col-sm-4">Address:</div>
								<div class="col-sm-6">${ myCart.customer.address } &nbsp;
									${ myCart.customer.city } &nbsp; ${ myCart.customer.state }</div>
							</div>
							<div class="row">
								<div class="col-sm-4">Email:</div>
								<div class="col-sm-6">${myCart.customer.email}</div>
							</div>
						</div>
					</div>
				</div>

			</c:if>


			<%-- CARD FOR PAYMENT --%>
			<c:if test="${not empty myCart.customerCard }">
				<div class="container">
					<div class="panel panel-info">
						<div class="panel-heading">Payment method:</div>
						
						<div class="panel-body">
							<div class="row">
								<div class="col-sm-4">Card Number </div>
								<div class="col-sm-6">*****${myCart.customerCard.get4LastDigits() } </div>
							</div>
							<div class="row">
								<div class="col-sm-4">Expire date:</div>
								<div class="col-sm-6">${myCart.customerCard.expireMonth} / ${myCart.customerCard.expireYear}</div>
							</div>
							<div class="row">
								<div class="col-sm-4">Card Holder:</div>
								<div class="col-sm-6">${myCart.customerCard.holderName} </div>
							</div>
							 
							
						</div>

					</div>
				</div>
			</c:if>

			<%-- SHIPPING ADDRESS FORM --%>
			<c:if test="${not empty myCart.shippingAddress}">
			<div class="container">
				<div class="panel panel-info">
					<div class="panel panel-heading">Shipping Information</div>
					<div class="panel panel-body">
						<div class="row">
							<div class="col-sm-4">Shipping Address:</div>
							<div class="col-sm-6">${myCart.shippingAddress.address}</div>
						</div>

						<div class="row">
							<div class="col-sm-4">Shipping City:</div>
							<div class="col-sm-6">${myCart.shippingAddress.city}</div>
						</div>

						<div class="row">
							<div class="col-sm-4">Shipping State:</div>
							<div class="col-sm-6">${myCart.shippingAddress.state }</div>
						</div>
					</div> <%-- panel body --%>
				</div><%-- panel info --%>
			</div> <%-- container SHIPPING ADDRESS FORM  --%>
			
		</c:if>
	<%-- SHOPPING CART ITEMS --%>
	<c:if test="${not empty myCart.cartItems }">
		<% int i=0; %>
		<h2>Your Shopping cart</h2>
		<table class="table  table-hover">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Book Name</th>
					<th scope="col">Unit price</th>
					<th scope="col">Quantity</th>
					<th scope="col">Sub Total</th>
				</tr>
			<thead>
				<c:forEach var="cartItem" items="${myCart.cartItems}">
					<c:set var="price" value="${cartItem.value.bookInfo.price}" />
					<c:set var="quantity" value="${cartItem.value.quantity }" />
					<% i++; %>
					<tr>
						<th scope="row"><%= i %></th>
						<td><a
							href="<c:url value="/book/getBook?bookId=${cartItem.key }" />"
							style="text-decoration: none;">${cartItem.value.bookInfo.name }</a>
						</td>
						<td><c:out value="${ price}" /></td>
						<td><c:out value="${ quantity}" /></td>
						<td><fmt:formatNumber type="number" value="${price*quantity}" />
						</td>
					</tr>
					<c:set var="total" value="${total +cartItem.value.getAmount() }" />
				</c:forEach>


				<tr>
					<td colspan="5" style="text-align: right; font-weight: bold;">
						Total:<fmt:formatNumber type="number" value="${ total }" />
					</td>
				</tr>
		</table>
		<br />

		<div class="form-row text-center">
			<%-- <a href="<c:url value="/cart/checkout" />" class="link-btn "> Check Out</a> --%>
			<form:form
				action="${pageContext.request.contextPath}/cart/saveOrder"
				method="POST">
				<input type="submit" class="btn btn-success" value="Check Out" />
				
			</form:form>
		</div>

	</c:if>



	<br />
	
</div>
	<jsp:include page="footer.jsp"></jsp:include>

	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

</body>


</html>
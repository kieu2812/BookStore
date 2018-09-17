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

<title>Order Detail</title>
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
		<h1 class="title">Order Detail</h1>
		<c:if test="${not empty errors }">
			<div>${ errors }</div>

		</c:if>
		<c:if test="${not empty messages }">
			<div>${ messages }</div>

		</c:if>
		
			<!-- modelAttribute="myCart"> -->

			<%-- CUSTOMER INFORMATION ADDRESS --%>
			<c:if test="${not empty orderInfo}">
				<div class="container">
					<div class="panel panel-info">
						<div class="panel panel-heading">
							<h3>Customer information:</h3>
						</div>
						<div class="panel panel-body">
							<div class="row">
								<div class="col-sm-4">Name:</div>
								<div class="col-sm-6">${ orderInfo.customerName} 	</div>
							</div>
							<div class="row">
								<div class="col-sm-4">Address:</div>
								<div class="col-sm-6">${ orderInfo.customerAddress } 	</div>
							</div>
							
						</div>
					</div>
				</div>

			</c:if>


			<%-- CARD FOR PAYMENT --%>
			<c:if test="${not empty orderInfo.cardNumber }">
				<div class="container">
					<div class="panel panel-info">
						<div class="panel-heading">Payment method:</div>
						
						<div class="panel-body">
							<div class="row">
								<div class="col-sm-4">Card Number</div>
								<div class="col-sm-6">${orderInfo.cardNumber}</div>
							</div>
						</div>

					</div>
				</div>
			</c:if>

			<%-- SHIPPING ADDRESS FORM --%>
			<c:if test="${not empty orderInfo.shippingAddress}">
			<div class="container">
				<div class="panel panel-info">
					<div class="panel panel-heading">Shipping Information</div>
					<div class="panel panel-body">
						<div class="row">
							<div class="col-sm-4">Shipping Address:</div>
							<div class="col-sm-6">${orderInfo.shippingAddress}</div>
						</div>

					</div> <%-- panel body --%>
				</div><%-- panel info --%>
			</div> <%-- container SHIPPING ADDRESS FORM  --%>
			
		</c:if>
	<%-- ORDER DETAIL ITEMS --%>
	<c:if test="${not empty orderDetails }">
	
		<h2> Order Detail</h2>
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
				<c:forEach var="orderDetail" items="${orderDetails}" varStatus="theCount">
										
					<tr>
						<th scope="row">${theCount.index+1}</th>
						<td>${orderDetail.bookName }</td>
						<td><c:out value="${ orderDetail.quantity}" /></td>
						<td><c:out value="${ orderDetail.unit_price}" /></td>
						<td><fmt:formatNumber type="number" value="${orderDetail.amount}" />
						</td>
					</tr>
					
				</c:forEach>


				<tr>
					<td colspan="4" style="text-align: right; font-weight: bold;">
						Total:
					</td>
					<td><fmt:formatNumber type="number" value="${ orderInfo.amount }" /></td>
				</tr>
		</table>
		<br />



	</c:if>

	<div class="center-align">
		<form method="GET" action="${pageContext.request.contextPath}/customer/showMyOrder">
			<input type="hidden" name="page" value="${page }"/>
			<input type="hidden" name="custId" value="${custId }"/>
			<input type="submit" class="btn btn-info" value="Back to your orders">
		</form>	
	</div>

	<br />
	
</div>
	<jsp:include page="footer.jsp"></jsp:include>

	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

</body>


</html>
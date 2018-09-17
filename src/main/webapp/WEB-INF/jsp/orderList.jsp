<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
	import="java.util.Date"
%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
        <title>My book store</title>
     <%--    <link  href="<c:url value='/resources/css/bootstrap.min.css' />"  rel="stylesheet"/>
     --%>
      <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">  
      <link  href="<c:url value='/resources/css/main.css' />"  rel="stylesheet"/>
      
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        
		<div class="container-fluid">   
			<c:if test="${ empty paginationOrders }">
				<div class="errorMessage">No data</div>
			</c:if>
			<div class="titleForm">My Order</div>
			<c:if test="${not empty paginationOrders}">
				<div class="container">
					
					<table class="table  table-hover">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">Order Number</th>
								<th scope="col"> CARDNUMBER</th>
								<th scope="col">Shipping Address</th>
								<th scope="col">Create Date</th>
								<th scope="col">Amount</th>
								<th scope="col">View Detail</th>
							</tr>
						<thead>
							<c:if test="${ not empty paginationOrders}">
								<c:forEach var="orderInfo" items="${paginationOrders.list}"
									varStatus="theCount">

									<tr>
										<th scope="row">${theCount.index +1}</th>

										<td><c:out value="${ orderInfo.id}" /></td>
										<td><c:out value="${ orderInfo.cardNumber}" /></td>
										<td><c:out value="${ orderInfo.shippingAddress}" /></td>
										<td><fmt:formatDate value="${orderInfo.createDate}"  />	</td>
										<td >
											<fmt:formatNumber value="${orderInfo.amount }"/></td>
										<td><a href="${pageContext.request.contextPath}/customer/${custId}/order/${orderInfo.id}?page=${currentPage }"
											>View
											</a>
									</tr>
									<c:set var="total" value="${total +orderInfo.amount }" />
								</c:forEach>
								<tr style="font-weight: bold;">
									<td colspan="5" style="text-align: right;">Total:</td>
									<td colspan="1" style="text-align: left;"><fmt:formatNumber
											type="number" value="${ total }" /></td>
									<td></td>
								</tr>
							</c:if>
					</table>
					<br />


					<%-- Display Pagination Pages--%>
					<c:if test="${paginationOrders.totalPages > 1}">

						<div class="center-align">
							<nav aria-label="Page navigation">
								<ul class="pagination pagination-lg ">
									<c:forEach items="${paginationOrders.navigationPages}"
										var="page">
										<c:if test="${page != -1 }">
											<li class="page-item">
											<a class="page-link" href="${pageContext.request.contextPath}/customer/showMyOrder?page=${page }&custId=${custId}"> ${page } </a>
											</li>
										</c:if>
										<c:if test="${page == -1 }">
											<li class="page-item"><a class="page-link" href="#">...</a></li>
										</c:if>

									</c:forEach>
								</ul>
							</nav>
						</div>

					</c:if>

				</div>
				<%-- Container --%>
			</c:if>
			


        </div>
        
        <!-- DIV CONTAINER -->
         <jsp:include page="footer.jsp"></jsp:include>
<%--        <script src="<c:url value='/resources/js/jquery.min.js'/>" > </script>
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>" > </script>
   
   --%>
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>  
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>  
</html>
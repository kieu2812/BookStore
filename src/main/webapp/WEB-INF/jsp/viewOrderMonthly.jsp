<%@page import="java.time.LocalDate"%>
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

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link href="<c:url value='/resources/css/main.css' />" rel="stylesheet" />

<title>Admin Report Monthly</title>

<script type="text/javascript">
	function getReport(pageValue){
		//alert('Page= ' +pageValue);
		document.getElementById("page").value=pageValue;
		document.getElementById("monthlyForm").submit();
	}
</script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<fmt:parseNumber var = "currentPage" value='<%=request.getParameter("page") %>'/>
	<c:if test="${ empty currentPage  || currentPage==0}"> <c:set var="currentPage" value="1"/> </c:if>
	<fmt:parseNumber var = "reqMonth" value='<%=request.getParameter("month") %>'/>
	<fmt:parseNumber var = "reqYear" value='<%=request.getParameter("year") %>'/>


	<div class="panel-info">
		<div class="panel-heading">View Orders By Month
		</div>
		
		<div class="panel-body">

			<form method="POST" id="monthlyForm">
					<input type="hidden"  id="page" name="page"/>
				<div class="form-group  row">
					<div class="col-sm-2">
						<label>Month</label>
					</div>
					<div class="col-sm-2">
						<input class="form-control" name="month" id="month"
							required type="number" min="1" max="12"
							value="${reqMonth }" />
					</div>
					<div class="col-sm-2">
						<label>Year</label>
					</div>
					<div class="col-sm-2">
						<input class="form-control" name="year" id="year" required
							type="number" min="2018" max="<%=LocalDate.now().getYear()%>"
							value="${reqYear }" />
					</div>
					<div class="col-sm-4">
						<input type="submit" class="btn btn-info" value="Get Report">
					</div>
				</div>

			<c:if test="${not empty errors }">
				<div class="errorMessage">${ errors }</div>

			</c:if>

			<%
				double total = 0;
			%>
			<c:if test="${ empty paginationOrders }">
				<div class="errorMessage">No data</div>
			</c:if>
			<c:if test="${not empty paginationOrders}">
				<div class="container">

					<table class="table  table-hover">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">Order Number</th>
								<th scope="col">Customer Name</th>
								<th scope="col">Create Date</th>
								<th scope="col">Amount</th>
								<th scope="col">View Detail</th>
							</tr>
						<thead>
							<c:if test="${ not empty paginationOrders}">
								<c:forEach var="orderReport" items="${paginationOrders.list}"
									varStatus="theCount">

									<tr>
										<th scope="row">${theCount.index +1}</th>

										<td><c:out value="${ orderReport.id}" /></td>
										<td><c:out value="${ orderReport.customerName}" /></td>
										<td><fmt:formatDate value="${orderReport.createDate }" />	</td>
										<td >
											<fmt:formatNumber value="${orderReport.amount }"/></td>
										<td><a href="${pageContext.request.contextPath}/order/${orderReport.id}?page=${currentPage }&month=${reqMonth }&year=${reqYear }"
											>View
											</a>
									</tr>
									<c:set var="total" value="${total +orderReport.amount }" />
								</c:forEach>
								<tr style="font-weight: bold;">
									<td colspan="4" style="text-align: right;">Total:</td>
									<td colspan="2" style="text-align: left;"><fmt:formatNumber
											type="number" value="${ total }" /></td>
								</tr>
							</c:if>
					</table>
					<br />


					<%-- Display Pagination Pages--%>
					<c:if test="${paginationOrders.totalPages > 1}">

						<div  class="center-align">
							<nav aria-label="Page navigation">
								<ul class="pagination pagination-lg ">
									<c:forEach items="${paginationOrders.navigationPages}" var="page">
										<c:if test="${page != -1 }">
											
											<li class="page-item">
											
												<a href="#" class="page-link"  onclick="getReport(${page})"> ${page }</a>
												
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
			
			
			</form>
			<%-- <c:if test="${not empty paginationBooks}" >   --%>
		</div><!-- <div class="panel-body"> -->
	</div>
	
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
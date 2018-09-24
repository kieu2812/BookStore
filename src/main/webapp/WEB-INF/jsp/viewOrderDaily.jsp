<%@page import="java.util.Date"%>
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
<link href="<c:url value='/resources/css/main.css'/>" rel="stylesheet" />

<title>Order Report Daily</title>
<script type="text/javascript" language="javascript">
function GetDateTime() {

    var param1 = new Date();
    document.getElementById('dateReport').value = param1;

}
</script>
</head>
<body onload="GetDateTime();">
<%-- 	<fmt:formatDate  value="<%=new Date()%>"  
                type="date" 
                pattern="mm/dd/yyyy"
                var="currentDay"/> --%>
	<jsp:include page="header.jsp"></jsp:include>

	<fmt:parseNumber var = "currentPage" value='<%=request.getParameter("page") %>'/>
	<c:if test="${ empty currentPage  || currentPage==0}"> <c:set var="currentPage" value="1"/> </c:if>
	<fmt:parseNumber var = "date" value='<%=request.getParameter("date") %>'/>


	<div class="panel-info">
		<div class="panel-heading">View Orders By Day
		</div>

		<div class="panel-body">

			<form method="POST">
				<div class="form-group  row">
					<div class="col-sm-4">
						<label>Choose date</label>
					</div>
					<div class="col-sm-5">
						<input class="form-control" name="date" id="dateReport" required type="date"  pattern="\d{1,2}/\d{1,2}/\d{4}" placeholder="mm/dd/yyyy"	/>
					</div>
					<div class="col-sm-3">
						<input type="submit" id="btnReport" class="btn btn-info" value="Get Report">
					</div>
				</div>

			<c:if test="${not empty errors }">
				<div class="errorMessage">${ errors }</div>

			</c:if>

			<%
				double total = 0;
			%>
<%-- 			<c:if test="${ empty paginationOrders }">
				<div class="errorMessage">No data</div>
			</c:if> --%>
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
										<td><a href="${pageContext.request.contextPath}/order/${orderReport.id}?page=${currentPage }"
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

						<div >
							<nav aria-label="Page navigation">
								<ul class="pagination pagination-lg ">
									<c:forEach items="${paginationOrders.navigationPages}"
										var="page">
										<c:if test="${page != -1 }">
											<li class="page-item">
											<input class="page-link" name="page" id="${page }" type ="submit" value="${page }"/>
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
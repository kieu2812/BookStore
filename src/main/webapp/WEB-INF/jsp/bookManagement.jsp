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

<title>Book Management</title>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">
		<!--  SEARCH BAR -->
		<div class="form-group row">
			<form:form action="${pageContext.request.contextPath}/book/searchBook" method="POST">

				<div class="col-sm-10">
					<input id="searchWord" type="text" class="form-control"
						name="searchWord" placeholder="Enter book titile or category" />
				</div>
				<div class="col-sm-2">
					<input class="btn btn-primary" type="submit" value="Search" />
				</div>

			</form:form>
		</div>

		<!-- LIST BOOK -->

		<div class="titleForm">Book Management</div>
		<div> <a  class="btn btn-info" href="<c:url value='/admin/book/showBookForm'/>">Add Book</a></div>
		<c:if test="${not empty paginationBooks}">

			<div class="container">
				<c:forEach var="bookInfo" items="${paginationBooks.list}">
					<div class="panel"
						style="width: 80%; border: 1px solid pink; margin: 10px auto; background-color: #e9f4de;">
						<div class="row">
							<div class="col-sm-4">
								<%-- hardCoverPath --%>
								<a href="<c:url value='/book/getBook?bookId=${bookInfo.id}'/>">
									<img alt="Book's image"
									src="<c:url value="/images/${bookInfo.hardCoverPath}"/>"
									width="200" height="200">
								</a>
							</div>
							<div class="col-sm-5">
								<div>
									<a href="<c:url value='/book/getBook?bookId=${bookInfo.id}'/>">${bookInfo.name}
									</a>
								</div>
								<div>
									<label> Price: </label>
									<fmt:formatNumber type="currency" value="${bookInfo.price }" />

								</div>
								<div>
									<c:if test="${bookInfo.qtyInStock==0}">
										<label> Out of stock: </label>

									</c:if>
									<c:if test="${bookInfo.qtyInStock>0}">
										<label> In stock: </label>
					    				${bookInfo.qtyInStock}
				    				</c:if>
								</div>
								

						
							</div>
							<div class="col=sm-3">
								<div style="margin: 10px auto;">
									<a  class="btn btn-info" href="<c:url value='/admin/book/updateBook?bookId=${bookInfo.id}'/>">Update</a>
									<a   class="btn btn-danger" href="<c:url value='/admin/book/deleteBook?bookId=${bookInfo.id}'/>">Delete</a>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
				
				<%-- Display Pagination Pages--%>
	<%--     		<c:if test="${paginationBooks.totalPages > 1}">
					<div class="page-navigator">
						<c:forEach items="${paginationBooks.navigationPages}" var="page">
							<c:if test="${page != -1 }">
								<a href="book?page=${page}" class="nav-item">${page}</a>
							</c:if>
							<c:if test="${page == -1 }">
								<span class="nav-item"> ... </span>
							</c:if>
						</c:forEach>
	
					</div> 
				</c:if>--%>
			<c:if test="${paginationBooks.totalPages > 1}">

			<div class="center-align">
				<nav aria-label="Page navigation">
				  <ul class="pagination pagination-lg ">
					<c:forEach items="${paginationBooks.navigationPages}" var="page">
						<c:if test="${page != -1 }">
							<li class="page-item"><a class="page-link" href="book?page=${page}">${page }</a></li>
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
		</c:if>








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
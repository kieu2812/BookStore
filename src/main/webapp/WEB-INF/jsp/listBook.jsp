<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<c:if test="${not empty message}">
		<div class="message">
			<h3>${message}</h3>
		</div>
	</c:if>

	<c:if test="${not empty paginationBooks}">

		<div class="container">
			<c:forEach var="bookInfo" items="${paginationBooks.list}">
				<div class="panel"
					style="width: 80%; border: 1px solid pink; margin: 10px auto; background-color: #e9f4de;">
					<div class="row">
						<div class="col-sm-5">
							<%-- hardCoverPath --%>
							<a href="<c:url value='/book/getBook?bookId=${bookInfo.id}'/>">
								<img alt="Book's image"
								src="<c:url value="/images/${bookInfo.hardCoverPath}"/>"
								width="200" height="200">
							</a>
						</div>
						<div class="col-sm-7">
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
							<div>
								<label> Rating: </label>

								<fmt:formatNumber type="number" maxFractionDigits="1"
									value="${bookInfo.rating }" />

							</div>
							<div>
								<label> Total Review </label> ${bookInfo.totalReview }

							</div>
							<br />

							<div>
							
								<c:if test="${bookInfo.qtyInStock>0}">
									<a class="btn btn-success"
										href="<c:url value='/addCart?bookId=${bookInfo.id}'/>">Add
										To Cart</a>
								</c:if>
							
							</div>
							<br/>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<div>
									<div>
										<a class="btn btn-info"
											href="<c:url value='/admin/book/updateBook?bookId=${bookInfo.id}'/>">Update</a>
										<a class="btn btn-danger"
											href="<c:url value='/admin/book/deleteBook?bookId=${bookInfo.id}'/>">Delete</a>
									</div>

								</div>
							</sec:authorize>
						</div>
					</div>
				</div>
			</c:forEach>

			<br />

			<%-- Display Pagination Pages--%>
			<c:if test="${paginationBooks.totalPages > 1 && empty categoryId}">

				<div style="margin: 0 auto; text-align: center;">
					<nav aria-label="Page navigation">
						<ul class="pagination pagination-lg ">
							<c:forEach items="${paginationBooks.navigationPages}" var="page">
								<c:if test="${page != -1 }">
									<li class="page-item"><a class="page-link"
										href="list?page=${page}">${page }</a></li>
								</c:if>
								<c:if test="${page == -1 }">
									<li class="page-item"><a class="page-link" href="#">...</a></li>
								</c:if>

							</c:forEach>
						</ul>
					</nav>
				</div>

			</c:if>

			<%-- Display Pagination Pages for category form--%>
			<c:if
				test="${paginationBooks.totalPages > 1 && not empty categoryId}">

				<div class="center-align">
					<nav aria-label="Page navigation">
						<ul class="pagination pagination-lg ">
							<li class="page-item"><a class="page-link" href="#">Page</a></li>
							<c:forEach items="${paginationBooks.navigationPages}" var="page">
								<c:if test="${page != -1 }">
									<li class="page-item"><a class="page-link"
										href="${categoryId}?page=${page}">${page }</a></li>
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
	<%-- <c:if test="${not empty paginationBooks}" >   --%>

	<jsp:include page="footer.jsp"></jsp:include>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>



</html>
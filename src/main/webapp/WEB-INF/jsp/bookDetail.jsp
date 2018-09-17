<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Book Detail</title>
<%-- <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet"> --%>
	<link rel="stylesheet"
		href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<link href="<c:url value='/resources/css/main.css' />" rel="stylesheet" />
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<c:out value="${custId }"/>
		<c:if test="${not empty bookDetail}">

			<div class="card">
				<div class="card-header">
					<h2 class="titleForm">${bookDetail.book.name}</h2>
				</div>

				<div class="card-image-top" style="text-align: center;">
					<%-- hardCoverPath --%>
					<a href="#"> <img alt="Book's image"
						src="<c:url value="/images/${bookDetail.book.hardCoverPath}"/>"
						class="image-reponsive">
					</a>
				</div>
				<br />
				<div class="card-body">
					<div>
						<label> Price: </label>
						<fmt:formatNumber type="currency"
							value="${bookDetail.book.price }" />
					</div>

					<div>
						<label> In stock: </label> ${bookDetail.book.qtyInStock}

					</div>
					<div>
						<label> Rating: </label> ${bookDetail.book.rating }
					</div>

					<div>
						<label> Total Review </label> ${bookDetail.book.totalReview }

					</div>
					<div>
						<label>Author :</label> ${bookDetail.author }
					</div>
					<div>
						<label>Publisher :</label> ${bookDetail.publisher }
					</div>
					<div>
						<label>Category :</label> ${bookDetail.category }
					</div>
					<div>
						<label>ISBN - 10:</label> ${bookDetail.book.ISBN_10}
					</div>
					<div>
						<label>ISBN - 13:</label> ${bookDetail.book.ISBN_13}
					</div>
					<div>
						<label>Pages: </label> ${bookDetail.book.pages}
					</div>
					<div>
						<label>Description:</label><br />
						<p style="padding: 5px;">${bookDetail.book.description}</p>

					</div>
					<br />
					
					 		<div>
							
								<c:if test="${bookInfo.qtyInStock>0}">
									<a class="btn btn-success"	href="<c:url value='/addCart?bookId=${bookDetail.book.id}'/>">AddTo Cart</a>
								</c:if>
							
							</div>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<div>
									<div style="margin: 30px;">
										<a class="btn btn-info"
											href="${pageContext.request.contextPath}/admin/book/updateBook?bookId=${bookDetail.book.id}">Update</a>
										<a class="btn btn-danger"
											href="${pageContext.request.contextPath}/admin/book/deleteBook?bookId=${bookDetail.book.id}">Delete</a>
									</div>

								</div>
							</sec:authorize>
							

				</div>
			</div>
		</c:if>

	</div>


	<div class="container-fluid">
		<div class="panel">
			<div class="card">
			
				<c:if test="${not empty isBought }">
					<!-- Allow for customer write review -->
					<form action="${pageContext.request.contextPath}/customer/writeReview" method="POST"  >
						<div class="row">
							<div class="col-sm-3">Your Rating:</div>
							<div class="col-sm-9">
								<!-- 	<div class="rating">
							<span>&#9734;</span><span>&#9734;</span><span>&#9734;</span><span>&#9734;</span><span>&#9734;</span>
						</div> -->
								<div>
									<fieldset class="rating">
									<input type="radio" name="rating"  value="5" />
											<label class="full" title="Awesome - 5 stars"></label> 
										
										 <input type="radio" name="rating"  value="4" />
										 		<label class="full" title="Pretty good - 4 stars"></label> 
									
										 <input type="radio" name="rating" value="3" />
										 	<label class="full" title="Meh - 3 stars"></label> 
									
										<input type="radio" name="rating"  value="2" />
										 	<label class="full" title="Kinda bad - 2 stars"></label> 
									
										 <input type="radio" name="rating"  value="1" />
										 	<label class="full"  title="Sucks big time - 1 star"></label> 
									
							
									</fieldset>
								</div>
							</div>
						</div>
						<div class="row">
					
							<div class="col-sm-10">
							<%-- 	<form:textarea  path="comments" rows="5" cols="100"/> --%>
								<textarea name="comments" rows="5" cols="100" maxlength="300" required ></textarea>
							</div>
							<input type="hidden" name="bookid" value="${bookDetail.book.id}"/>			
							<input type="submit" class="col-sm-1 btn btn-primary"
								style="margin: 20px;" value="Post" />
						</div>
					</form>
				</c:if>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="panel">
			<c:if test="${ empty reviews }">
  				No review
  			</c:if>
			<c:if test="${not empty reviews }">
				<div  style="color: orange; text-align:center;"><label class="card-title " >Review: </label></div>
				<c:forEach var="review" items="${reviews}">
					<div class="card">
						
						<div class="card-title"><strong>${review.posterName }</strong></div>
						<div class="card-body">
							<div class="card=item">
								Posted Date: <fmt:formatDate value="${review.createDate }" />  &nbsp; &nbsp; Rating:${review.rating }
							</div>
							
							<div class="card-text">Comment:</div>
							<c:if test="${custId!=review.posterid}">
								<div class="card-text" >
								
									${review.comments }	
								 </div>
							</c:if>
							 <div>
							 
							<c:if test="${custId==review.posterid}">
								<form action="${pageContext.request.contextPath}/customer/updateReview" method="POST">
										<div>
											<input type="radio" name="rating" value="1" /> 
											<input	type="radio" name="rating" value="2" />
											 <input type="radio" name="rating" value="3" />
												 <input type="radio" name="rating" value="4" />
												 <input type="radio" name="rating" value="5" />
										</div>
										<textarea name="updateComment" rows="4" cols="100"> 	${review.comments }	 </textarea>
									<input type="hidden" name="bookid" value="${bookDetail.book.id}"/>
									<input type="hidden" name="id" value="${review.id}"/>
									<input type="submit" class="btn btn-warning" value="Update"/>
									<a href="${pageContext.request.contextPath}/customer/deleteReview/${bookDetail.book.id}/${review.id}" class="btn btn-warning">Delete </a>
								</form>
							</c:if>
							</div>	
						</div>
					</div>
				</c:forEach>
			</c:if>


		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
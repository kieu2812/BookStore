<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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

<title>Book form</title>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
	
		<div class="panel panel-success">
			<div class="panel-heading" align="center">
				<h4>
					
					<b><font color="black" style="font-family: fantasy;">Book Information
							</font> </b>
				</h4>
			</div>
			<c:if test="${not empty error }">
				<div class="errorMessage">${error }</div>
			</c:if>		
			<div class="panel-body" align="center">
				<div class="container " style="margin-top: 10%; margin-bottom: 10%;">
					<div class="panel panel-success" style="max-width: 80%;"
						align="left">

						<div class="form-group" style="padding: 10px;">
							<form:form id="addBookForm" modelAttribute="bookForm"
								action="${pageContext.request.contextPath}/admin/book/updateProcess" method="post"
								enctype="multipart/form-data">
								<div class="form-group">
									<form:label path="book.name"> Book Name</form:label>
									<form:input class="form-control" required="true"  path="book.name" />
									<form:errors class="errorMessage" path="book.name" />
									
								</div>
								

								<div class="form-group">
									<form:label path="file">Picture Hard Cover</form:label>
									<c:if test="${not empty bookForm.book.hardCoverPath}">
							   			<img alt="Book's image"  width="300px;" src="<c:url value="/images/${bookForm.book.hardCoverPath}"/>" width="300">
							   		</c:if>									
									<form:input type="file" path="file"  class="form-control"
										accept=".jpg,.jpeg,.png" />
									<form:errors type="file" path="file" class="errorMessage"
										accept=".jpg,.jpeg,.png" />

								</div>


								<div class="form-group">
									<form:label path="book.ISBN_10">ISBN 10</form:label>
									<form:input class="form-control" required="true"  path="book.ISBN_10" />
									<form:errors  path="book.ISBN_10" class="errorMessage"/>
								</div>

								<div class="form-group">
									<form:label path="book.ISBN_13">ISBN 13</form:label>
									<form:input class="form-control" required="true"  path="book.ISBN_13" />
									<form:errors  path="book.ISBN_13" class="errorMessage" />
								</div>

								<div class="form-group">
									<form:label path="book.publisherId">Publisher</form:label>
									<form:select class="form-control" required="true"  path="book.publisherId">
										<form:options items="${publisherList}"></form:options>
									</form:select>
									<form:errors path="book.publisherId" class="errorMessage"/>								
									
								</div>
								
								<div class="form-group">
									<form:label path="book.categoryId">Category</form:label>
									<form:select class="form-control"  required="true" path="book.categoryId">
										<form:options items="${categoryList}"></form:options>
									</form:select>
									<form:errors path="book.categoryId" class="errorMessage"/>
								</div>
								<div class="form-group">
									<form:label path="invoicePrice">Invoice Price</form:label>
									<form:input class="form-control" required="true"  path="invoicePrice" />
									<form:errors path="invoicePrice" class="errorMessage"/>	
								</div>

								<div class="form-group">
									<form:label path="book.price">Selling Price</form:label>
									<form:input class="form-control" required="true"  path="book.price" />
									<form:errors path="book.price" class="errorMessage"/>										
								</div>

								<div class="form-group">
									<form:label path="book.description">Description</form:label>
									<form:textarea class="form-control" required="true" rows="10"  path="book.description" />
									<form:errors path="book.description" class="errorMessage"/>	
								</div>
								<div class="form-group">
									<form:label path="book.pages">Pages</form:label>
									<form:input class="form-control" required="true"  path="book.pages" />
									<form:errors path="book.pages" class="errorMessage"/>	
								</div>

								<div class="form-group">
									<form:button id="submit" name="submit"
										style="width: 45%; font-size:1.1em; "
										class="btn btn-large btn btn-success btn-lg float-left">
										<b>Submit</b>
									</form:button>
									<form:button id="cancel" name="cancel"
										style="width: 45%; font-size:1.1em;"
										class="btn btn-large btn btn-success btn-lg float-right">
										<b>Cancel</b>
									</form:button>
									
								</div>
								<!-- This action may be is "add" or "update"  -->
								<div class="form-group">
									<form:hidden path="action" />
									<form:hidden path="book.id" />
									<form:hidden path="book.hardCoverPath" />
								</div>							
								
								
							</form:form>
						</div>
						<%-- form --%>
					</div>
				</div>
			</div>

		</div>
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
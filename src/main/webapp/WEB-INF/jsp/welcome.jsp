<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
      <link  href="<c:url value='/resources/css/main.css' />"  type="text/css" rel="stylesheet"/>
      
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
   
	
<%--     	<c:if test="${empty categories }" >
    		<c:out value="Category is empty" />
    	</c:if> --%>
   
   
 <c:if test="${not empty paginationBooks}" >

            <div class="container">
             	
             <div class="form-group row">
                   <form:form action="book/searchBook" method="POST">
                   			
	                   		<div class="col-sm-10">
	                   			<input id="searchWord" type="text" class="form-control"  name="searchWord" placeholder="Enter book titile or category"/>	
							</div>
							<div class="col-sm-2">
								<input class="btn btn-primary" type="submit" value="Search"/>
							</div>	                   		                  		
                   		
                   </form:form>
                </div>
         </div>
         
        
        <%-- LIST ALL BEST SELLER BOOK --%>
		<div class="container">      
        <c:if test="${not empty paginationBooks}" >
        	   <div><h1 class="panel panel-heading"> The best seller</h1> </div>
           
	    	<c:forEach var="bookInfo" items="${paginationBooks.list}">
	    	<div class="panel" style="width: 80%; border: 1px solid pink; margin: 10px auto; background-color: #e9f4de; ">
				<div class="row">
				<div class="col-sm-5">
				<%-- hardCoverPath --%>
    				<a href="<c:url value='/book/getBook?bookId=${bookInfo.id}'/>">
    				<c:url value="/images/${bookInfo.hardCoverPath}" var="pathImage"/>
    				 <img alt="Book's image" src="${pathImage}" width="200" height="200" > </a>
    			</div>
    			<div class="col-sm-7">
    			<div >
    				<a href="<c:url value='/book/getBook?bookId=${bookInfo.id}'/>">${bookInfo.name}  </a>
    			 </div>
    			<div>
    				<label> Price: </label>
    				<fmt:formatNumber type="currency" value="${bookInfo.price }"/>
    				
    			</div>
    			<div>
    				<label> In stock: </label>
    				${bookInfo.qtyInStock}
    				
    			</div>
    			<div>
    				<label> Rating: </label>
    				${bookInfo.rating }
    				
    			</div>
    			<div>
    				<label> Total Review </label>
    				${bookInfo.totalReview }
    				
    			</div>
    			<br/>
    			
    			<div>
    			
 							<div>
						
								<c:if test="${bookInfo.qtyInStock>0}">
									<a class="btn btn-success"
										href="<c:url value='/addCart?bookId=${bookInfo.id}'/>">Add
										To Cart</a>
								</c:if>
						
							</div>
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
    		</div>
	    	</c:forEach>
    	
  	 	</c:if>
  	 	
  	 	<%-- Display Pagination Pages--%>
    	<c:if test="${paginationBooks.totalPages > 1}">

			<div style="margin:0 auto; text-align:center;">
				<nav aria-label="Page navigation">
				  <ul class="pagination pagination-lg ">
					<c:forEach items="${paginationBooks.navigationPages}" var="page">
						<c:if test="${page != -1 }">
							<li class="page-item"><a class="page-link" href="?page=${page}">${page }</a></li>
						</c:if>
						<c:if test="${page == -1 }">
							<li class="page-item"><a class="page-link" href="#">.....</a></li>
						</c:if>
	
					   </c:forEach>
				  </ul>
				</nav>
			</div>
				
		</c:if>
  	 	</div> <!-- DIV CONTAINER -->
    </c:if>  <%-- <c:if test="${not empty paginationBooks}" >   --%>
         <jsp:include page="footer.jsp"></jsp:include>
<%--        <script src="<c:url value='/resources/js/jquery.min.js'/>" > </script>
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>" > </script>
   
   --%>
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>  
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>  
</html>
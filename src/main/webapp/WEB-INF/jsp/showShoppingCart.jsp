<%@page import="com.perscholas.model.ShoppingCart"%>
<%@ taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
		 
	<%--  <link  href="<c:url value='/resources/css/bootstrap.min.css' />"  rel="stylesheet"/> --%>

	  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">  
	  	 <link  href="<c:url value='/resources/css/main.css' />"  rel="stylesheet"/>
	  
	 
</head>
<body>
    	<jsp:include page="header.jsp"></jsp:include>

<div class="container-fluid" style="width:90%;">
	<% //ShoppingCart myCart = (ShoppingCart) request.getSession().getAttribute("shoppingCart");
		double total =0;
	%>
	<h1 class="titleForm">My Shopping Cart</h1>
	
	<c:if test="${ empty myCart.cartItems }" >
		<div> Your Cart is empty.</div>
		<a href="<c:url value="/" />"> Go Back Home</a>
    </c:if>
    
    <c:if test="${not empty errors }" >
		<div class="errorMessage"> ${ errors }</div>
		
    </c:if>
    <c:if test="${not empty myCart.cartItems }" >
    	
    	<c:forEach var="cartItem" items="${myCart.cartItems}" varStatus="theCount">
    		<div class="card">
    		<c:set var="price" value="${cartItem.value.bookInfo.price}" />
    		<c:set var="quantity" value="${cartItem.value.quantity }" />
    		<form:form action="updateCart" method="POST">
  			<div class="row">
  				<div class="col-sm-4">
				<%-- hardCoverPath --%>
    				<a href="<c:url value='/book/getBook?bookId=${cartItem.key}'/>">
    				 <img alt="Book's image" src="<c:url value="/images/${cartItem.value.bookInfo.hardCoverPath}"/>" width="200" height="200" >
    				</a>
    			</div>
    	 		<div  class="col-sm-8">
    	
		    		<div ><a href="<c:url value="/book/getBook?bookId=${cartItem.key }" />">${cartItem.value.bookInfo.name }</a> </div>
		    		
		    		<div>Price: &nbsp;  <c:out value="${ price}" /></div>
		    		<br/>
		    		<div> 
			    	
			    		 In Stock:&nbsp; ${cartItem.value.bookInfo.qtyInStock}
			    		
			    		
			    	</div>
		    		<div> 
			    	
			    		 Quantity: &nbsp; <input type="number" value="${quantity}" name="quantity" min="1" max="${cartItem.value.bookInfo.qtyInStock}" /> 
			    		<input type="hidden" value="${ cartItem.key}" name="bookId" />
			    		
			    	</div>
			    	
			    	<br/>
		    		<div style="weight-font: bold; color: blue;">SubTotal: &nbsp; <fmt:formatNumber type = "number" value="${price*quantity}"/></div>
		    		 
		    		<div>
		    			<input type="submit" class="btn btn-warning" value="Update" />
		    			<a  href="<c:url value="/deleteCart?bookId=${ cartItem.key}" />" class="btn btn-danger link-btn" > Delete </a> 
		    		</div>
		    	</div> <!-- col-sm-8 -->
    	
    		</div> <!-- row -->
    			
    			</form:form>
    		  <c:set var="total" value="${total +cartItem.value.getAmount() }" />
    	</div>
    	</c:forEach>
    
    <%-- 	<div> Total:<fmt:formatNumber type = "number" value="${ total}" /></div> --%>
    
    	<br/>
       	<div  class="form-row ">
       		
       		 <div class="totalRow"> Total: &nbsp; <fmt:formatNumber type = "number" value="${ total }" /></div>
       	</div> 
    	
 		<br/>
    	<div class="form-row text-center">
    		<%-- <a href="<c:url value="/cart/checkout" />" class="link-btn "> Check Out</a> --%>
    		<form action="${pageContext.request.contextPath}/cart/checkLogin" method="GET">
    			<input type="submit" class="btn btn-success" value="Process to Check Out"/>
    		</form>
    	</div>
    
    </c:if>
    
</div>
<br/>
    	<jsp:include page="footer.jsp"></jsp:include>

	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

</body>



</html>
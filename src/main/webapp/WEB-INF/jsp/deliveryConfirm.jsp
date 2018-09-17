<%@page import="com.perscholas.model.ShoppingCart"%>
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>

 <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

 <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">  
      <link  href="<c:url value='/resources/css/main.css' />"  rel="stylesheet"/>

	<title>Shopping Cart Confirmation</title>
<script type="text/javascript">
	function setShippingAddress(){
		var thesame = document.getElementById("sameAddress").value;
		if(!thesame.checked){
			document.getElementById("shippingAddress.address").value="";
			document.getElementById("shippingAddress.city").value="";
			document.getElementById("shippingAddress.state").value="";
		}
	}
</script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
	<%
		double total =0;
	%>
	<h1 class="title">Shopping Cart Confirmation</h1>
    <c:if test="${not empty errors }" >
		<div> ${ errors }</div>
		
    </c:if>
    <form:form action="/cart/checkout" method="POST"> 
    <!-- modelAttribute="myCart"> -->
    <%-- CUSTOMER INFORMATION ADDRESS --%>
    <c:if test="${not empty myCart.customer}">
    	<div class="container">
    		<h3>Customer information: </h3>
	    	<ul>
	    		<li> Name: ${ myCart.customer.firstName} &nbsp; ${myCart.customer.lastName}  </li>
	    		<li> Address:   ${ myCart.customer.address }   &nbsp; ${ myCart.customer.city } &nbsp; ${ myCart.customer.state }  </li>
	    		<li> Email: ${myCart.customer.email}
	    	</ul>
    	</div>
    
    
    </c:if>
    
    <%-- CARD FOR PAYMENT --%>
    <c:if test="${not empty mapCard }">
    	<form:select path="customerCardID">
    		<form:options items="${mapCard}"/>
    	</form:select>
    </c:if>
    
    <%-- SHIPPING ADDRESS FORM --%>
    
   <%--  <form:radiobutton path="sameAddress" value="T"> The same address</form:radiobutton>  --%>
    <%--
    
    <form:radiobutton path="sameAddress" value="F"> New address </form:radiobutton> 
    <form:label  path="shippingAddress.address" > Shipping Address: </form:label> 
    <form:input path="shippingAddress.address"  id="shippingAddress.address" value="${myCart.customer.address }"/> 
    
    <form:label  path="shippingAddress.city"> Shipping City: </form:label> 
    <form:input path="shippingAddress.city"  id="shippingAddress.city" value="${myCart.customer.city }"/> 
    
    <form:label  path="shippingAddress.state"> Shipping State: </form:label> 
    <form:input path="shippingAddress.state" id="shippingAddress.state" value="${myCart.customer.state }"/> 
    --%>
    
    <form:form id="addShippingAddressForm" modelAttribute="confirmForm" action="/addShippingAddressForm" method="post">
    	<form:radiobutton path="sameAddress" value="1" label = "Same address"/>
    	<form:radiobutton path="sameAddress" value="0" label = "New address"/>
    	
    	<br/>
    	<c:if test="${confirmForm.sameAddress == '1'}">
    		Shipping Address: ${myCart.customer.address }<br/>
    		Shipping City: ${myCart.customer.city }<br/>
    		Shipping State: ${myCart.customer.state }
    	</c:if>
    
    	<c:if test="${confirmForm.sameAddress == '0'}">
    		<form:label  path="shippingAddress.address" > Shipping Address: </form:label> 
	    	<form:input path="shippingAddress.address"/> 
	    	
	    	<form:label  path="shippingAddress.city"> Shipping City: </form:label> 
	    	<form:input path="shippingAddress.city"/>
	    	
	    	<form:label  path="shippingAddress.state"> Shipping State: </form:label> 
	    	<form:input path="shippingAddress.state"/>
    	</c:if>
    	 
    </form:form>
    
    <%-- SHOPPING CART ITEMS --%>
    <c:if test="${not empty myCart.cartItems }" >
     <% int i=0; %>
    	<h2>Your Shopping cart</h2>
    	<table  class="table  table-hover">
    		<thead>
    		<tr>
    			<th scope="col"> # </th>
	    		<th scope="col"> Book Name </th>
	    		<th scope="col"> Unit price </th>
	    		<th scope="col"> Quantity </th>
	    		<th scope="col"> Sub Total </th>
    		</tr>
    		<thead>
    	<c:forEach var="cartItem" items="${myCart.cartItems}">
    		<c:set var="price" value="${cartItem.value.bookInfo.price}" />
    		<c:set var="quantity" value="${cartItem.value.quantity }" />
  			<% i++; %>
    		<tr>
    			<th scope="row" > <%= i %> </th>
	  			<td>
			    		<a href="<c:url value="/book/getBook?bookId=${cartItem.key }" />">${cartItem.value.bookInfo.name }</a> 
			   </td>
			   <td> 		
			    		 <c:out value="${ price}" />
			   </td>
			   <td>
				    		 <c:out value="${ quantity}" />
			   </td>    	
			   <td>
			    	<fmt:formatNumber type = "number" value="${price*quantity}"/>
				</td>		    	
			</tr>
    			   <c:set var="total" value="${total +cartItem.value.getAmount() }" />
    	</c:forEach>
    
    
    	<tr>
    	   <td  colspan="5" style="text-align: right; font-weight: bold;">
    	    Total:<fmt:formatNumber type = "number" value="${ total }" />
    	   </td>
    	</tr> 
    	</table>
 		<br/>
 		
    	<div class="form-row text-center">
    		<%-- <a href="<c:url value="/cart/checkout" />" class="link-btn "> Check Out</a> --%>
    		<form action="cart/checkout" method="GET">
    			<input type="submit" class="btn btn-success" value="Check Out"/>
    		</form>
    	</div>
    
    </c:if>
   </form:form> 
    
</div>
<br/>
    	<jsp:include page="footer.jsp"></jsp:include>

	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

</body>
	

</html>
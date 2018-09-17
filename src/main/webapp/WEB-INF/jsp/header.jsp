<%@ page import="com.perscholas.model.Category, java.util.List" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header>
 
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Kieu's BookStore</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/book/list" >All Books</a></li>
      
      
	  <li class="dropdown">	  	
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Categories<span class="caret"></span></a>        
        <ul class="dropdown-menu">
        	<c:if test="${not empty categories}" >
	        	<c:forEach var="category" items="${categories}" >
	          		<li><a href="${pageContext.request.contextPath}/book/category/${category.id}">${category.name}</a></li>
	 			</c:forEach>
 			</c:if> 
        </ul>
      </li>
    
    <sec:authorize access="hasRole('ROLE_USER')">
	  <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Customer Services<span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="${pageContext.request.contextPath}/customer/showMyOrder">My Ordered</a></li>
          <li><a href="${pageContext.request.contextPath}/showChangePass">Change Password</a></li>
        </ul>
      </li>
    </sec:authorize>
    
    <sec:authorize access="hasRole('ROLE_ADMIN')">
		<li class="dropdown">
	       <a class="dropdown-toggle" data-toggle="dropdown" href="#">Admin Portal<span class="caret"></span></a>
	       <ul class="dropdown-menu">
	         <li><a href="${pageContext.request.contextPath}/admin/book">Book Management</a></li>	     
	         <li><a href="${pageContext.request.contextPath}/admin/order/monthly">Orders By Month</a></li>
	         <li><a href="${pageContext.request.contextPath}/admin/order/daily">Orders By Date</a></li>
	       </ul>
		</li>
    </sec:authorize>
    
    <li><a href="${pageContext.request.contextPath}/showMyCart">Shopping Cart</a></li>
    
    <c:if test="${empty pageContext.request.userPrincipal.name }">      
    	<li><a href="${pageContext.request.contextPath}/customer/showSignUp">Sign Up</a></li>
    	<li><a href="${pageContext.request.contextPath}/login">Login</a></li>
	</c:if>
	
	
	<c:if test="${not empty pageContext.request.userPrincipal.name }">		
		<li> <a href="${pageContext.request.contextPath}/logout">Logout</a> </li>
	</c:if>  	

    </ul>
  </div>
</nav>
</header>
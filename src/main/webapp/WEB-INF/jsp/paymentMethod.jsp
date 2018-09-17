<%@page import="com.perscholas.model.ShoppingCart, java.util.Date"%>
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

<title>Payment Method Page</title>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<%
		double total =0;
	%>
		<h1 class="title">Payment Method</h1>
		<c:if test="${not empty errors }">
			<div>${ errors }</div>

		</c:if>
		
		 <jsp:useBean id="today" class="java.util.Date" />
   		 <b>
   		 
   		 </b>
   		 
		<form action="${pageContext.request.contextPath}/cart/shoppingConfirm" method="POST">


			<%-- CARD FOR PAYMENT --%>

			<c:if test="${empty mapCards }">
				<h3>You don't have any card to process check out. Please enter
					new card information.</h3>

			</c:if>
			<c:if test="${not empty mapCards }">
				<div class="panel panel-success">
		    	 	<div class="panel panel-heading">Choose one card for payment  </div>
		    	 	<div class="panel panel-body">
		    	 		<div class="card">
		    	 			<ul class="list-group list-group-flush">
						    	<c:forEach var="card" items="${mapCards}" varStatus="loop">
						    		<c:if test="${loop.index==0}">
								      <c:set var="defaultChecked" value="checked"/>
  									</c:if>
						    		
									<li class="list-group-item" style="height: 100px;">	
											<div class="col-sm-4">
												Card Number: 
											</div> 
										<c:choose>
											<c:when test="${card.expireDate lt today }">
										       <c:set var="disableValue" value="disabled"/>
										       
											</c:when>
											<c:otherwise>
												 <c:set var="disableValue" value=""/>
										       
											</c:otherwise>
										</c:choose>
							
											<div class="col-sm-8">
											
												<input type="radio" value="${card.cardId}" id="${loop.index}"
													name="cardId" ${defaultChecked} ${disableValue}/>  ******${card.get4LastDigits()}
											</div>	
											<div class="col-sm-4">
												Holder Name:
											</div>
											<div class="col-sm-8">
												  ${card.holderName}
											</div>	
											<input type="hidden" name="isOldCard" value="true"/>
						    				
						    					<div class="col-sm-4">
												Expire:
												
											</div>
											<div class="col-sm-8">
												<b> ${card.expireMonth} / ${card.expireYear} </b>
												  
												
											</div>	
											<input type="hidden" name="isOldCard" value="true"/>
						    				
						    		</li>
						    		<br/>
						    	</c:forEach>
				    		</ul>
				    	</div>
		    		</div>
		    	</div>
			</c:if>

			<div class="form-group row">
			<div class="col-sm-5">

				<input type="submit" class="btn btn-success" value="Use this card to check out" />

			</div>
			<br/>
			<div class="col-sm-5">
				<a href="${pageContext.request.contextPath}/card/showCard"
					 class="btn btn-info"> Pay by new card </a>
			</div>
			</div>
			<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		</form>
	</div>
	<br />
	<jsp:include page="footer.jsp"></jsp:include>

	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

</body>


</html>
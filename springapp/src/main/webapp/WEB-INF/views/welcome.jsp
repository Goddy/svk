<%@ include file="header.jsp"%>


<h3>Message : ${message}</h3>	
	<h3>Username : ${username}</h3>	
 
	<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>

<%@ include file="footer.jsp"%>
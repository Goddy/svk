<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../header.jsp"%>


<form:form action="." modelAttribute="Account">
<div>E-mail adres: <form:input path="email" /></div>
<div>Voornaam: <form:input path="firstName" /></div>
<div>Naam: <form:input path="lastName" /></div>
<div>Wachtwoord: <form:password path="password" /></div>
<div>Bevestig wachtwoord: <form:password path="confirmPassword" /></div>

<div><input type="submit" value="Registreer" /></div>
</form:form>
                    
<%@ include file="../footer.jsp"%>
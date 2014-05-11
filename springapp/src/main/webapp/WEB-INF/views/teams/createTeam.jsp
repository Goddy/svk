<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>
<form:form action="createTeam.html" cssClass="form-horizontal" role="form" modelAttribute="form">
    <tag:formField path="teamName" title="label.teamName" label="label.teamName" optional="false" type="input"/>
    <tag:formField path="address" title="label.street" label="label.street" optional="false" type="input"/>
    <tag:formField path="postalCode" title="label.postalCode" label="label.postalCode" optional="false" type="input"/>
    <tag:formField path="city" title="label.city" label="label.city" optional="false" type="input"/>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default"><spring:message code="button.add"/></button>
        </div>
    </div>
</form:form>
<%@ include file="../jspf/footer.jspf" %>

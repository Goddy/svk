<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../header.jsp"%>

<form:errors path="*" cssClass="result">
    <div><spring:message code="error.global" /></div>
</form:errors>

<form:form action="register" modelAttribute="Account" class="default_form">
    <table>
        <tr><td><spring:message code="label.email" /></td><td><form:input path="email" cssErrorClass="error"/><form:errors path="email" htmlEscape="false"/></td></tr>
        <tr><td><spring:message code="label.firstName" /></td><td><form:input path="firstName" cssErrorClass="error"/><form:errors path="firstName" htmlEscape="false" /></td></tr>
        <tr><td><spring:message code="label.lastName" /></td><td><form:input path="lastName" cssErrorClass="error"/><form:errors path="lastName" htmlEscape="false" /></td></tr>
        <tr><td><spring:message code="label.password" /></td><td><form:password path="password" cssErrorClass="error"/><form:errors path="password" htmlEscape="false" /></td></tr>
        <tr><td><spring:message code="label.confirmPassword" /></td><td><form:password path="confirmPassword" cssErrorClass="error"/><form:errors path="confirmPassword" htmlEscape="false" /></td></tr>

        <tr><td colspan="2"><input type="submit" value='<spring:message code="button.submit"/>' /></td></tr>
    </table>
</form:form>

<%@ include file="../footer.jsp"%>
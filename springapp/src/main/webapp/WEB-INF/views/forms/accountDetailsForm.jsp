<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../header.jsp"%>

<form:errors path="*" cssClass="result">
    <div><spring:message code="error.global" /></div>
</form:errors>


<table>
    <tr>
        <td align="top-left"><spring:message code="text.userdetails"/></td>
        <td align="left">
            <form:form action="update_details" modelAttribute="Account" class="default_form">
                <table>
                    <tr><td><spring:message code="label.email" /></td><td><form:input path="username" cssErrorClass="error"/><form:errors path="username" htmlEscape="false"/></td></tr>
                    <tr><td><spring:message code="label.firstName" /></td><td><form:input path="firstName" cssErrorClass="error"/><form:errors path="firstName" htmlEscape="false" /></td></tr>
                    <tr><td><spring:message code="label.lastName" /></td><td><form:input path="lastName" cssErrorClass="error"/><form:errors path="lastName" htmlEscape="false" /></td></tr>
                    <tr><td colspan="2"><input type="submit" value='<spring:message code="button.update"/>' /></td></tr>
                </table>
            </form:form>
        </td>
    </tr>

</table>


    <%@ include file="../footer.jsp"%>



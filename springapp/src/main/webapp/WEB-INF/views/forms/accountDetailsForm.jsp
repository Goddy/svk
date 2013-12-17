<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../header.jsp"%>

<form:errors path="*" cssClass="result">
    <div><spring:message code="error.global" /></div>
</form:errors>

<table>
    <tr>
        <td align="left"><spring:message code="text.userdetails"/></td>
        <td align="right">
            <form:form action="update_details.do" modelAttribute="Account" class="default_form">
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
<table>
    <tr>
        <td align="left"><spring:message code="text.changepassword"  /></td>
        <td align="right">
            <form:form action="change_password.do" class="default_form" modelAttribute="Password">
                <table>
                    <tr><td><spring:message code="label.oldpassword" /></td><td><form:input path="oldPassword" cssErrorClass="error"/><form:errors path="oldPassword" htmlEscape="false"/></td></tr>
                    <tr><td><spring:message code="label.password" /></td><td><form:input path="newPassword" cssErrorClass="error"/><form:errors path="newPassword" htmlEscape="false" /></td></tr>
                    <tr><td><spring:message code="label.confirmPassword" /></td><td><form:input path="confirmPassword" cssErrorClass="error"/><form:errors path="confirmPassword" htmlEscape="false" /></td></tr>
                    <tr><td colspan="2"><input type="submit" value='<spring:message code="button.update"/>' /></td></tr>
                </table>
            </form:form>
        </td>
    </tr>
</table>


    <%@ include file="../footer.jsp"%>



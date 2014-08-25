<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf"%>

<form:form action="register" modelAttribute="Account" cssClass="form-horizontal">
    <tag:formField path="username" label="label.email" title="label.email" type="input" optional="false"/>
    <tag:formField path="firstName" label="label.firstName" title="label.firstName" type="input" optional="false"/>
    <tag:formField path="lastName" label="label.lastName" title="label.lastName" type="input" optional="false"/>
    <tag:formField path="password" label="label.password" title="label.password" type="password" optional="false"/>
    <tag:formField path="confirmPassword" label="label.confirmPassword" title="label.confirmPassword" type="password" optional="false"/>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button id="btnSubmit" type="submit" class="btn btn-primary"><spring:message code='button.submit'/></button>
            <button id="btnReset" type="reset" class="btn btn-info"><spring:message code='button.reset'/></button>
        </div>
    </div>
</form:form>

<%@ include file="../jspf/footer.jspf"%>
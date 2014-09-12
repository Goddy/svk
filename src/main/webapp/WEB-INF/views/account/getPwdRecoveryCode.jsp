<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf"%>
<%@ include file="../jspf/resultMessage.jspf"%>

<h2><spring:message code="title.activation.code"/></h2>
<div class="panel panel-default">
    <div class="panel-body">
<form:form action="getPwdRecoveryCode.html" modelAttribute="form" cssClass="form-horizontal">
    <form:hidden path="newCode"/>
    <tag:formField path="email" label="label.email" title="label.email" type="input" optional="false"/>
    <c:if test="${form.newCode == false}">
    <tag:formField path="code" label="label.code" title="label.code" type="input" optional="false"/>
    <tag:formField path="newPassword" label="label.password" title="title.password" type="password" optional="false"/>
    <tag:formField path="repeatNewPassword" label="label.confirmPassword" title="label.confirmPassword" type="password" optional="false"/>
    </c:if>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button id="btnSubmit" type="submit" class="btn btn-primary"><spring:message code='button.submit'/></button>
            <button id="btnReset" type="reset" class="btn btn-info"><spring:message code='button.reset'/></button>
        </div>
    </div>
</form:form>
    </div>
    </div>

<%@ include file="../jspf/footer.jspf"%>

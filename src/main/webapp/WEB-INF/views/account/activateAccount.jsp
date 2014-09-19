<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<h2><spring:message code="title.activate.account"/></h2>
<%@ include file="../jspf/resultMessage.jspf" %>
<div class="panel panel-default">
    <div class="panel-body">
        <div class="alert alert-info"><spring:message code="text.activation" arguments="${account.fullName}"/></div>
        <form:form action="activateAccount.html" modelAttribute="form" cssClass="form-horizontal">
            <form:hidden path="accountId"/>
            <tag:formField path="sendEmail" label="label.sendEmail" title="title.sendEmail" type="checkbox" optional="false"/>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="btnSubmit" type="submit" class="btn btn-primary"><spring:message code='button.submit'/></button>
                </div>
            </div>
        </form:form>
</div>
    </div>
<%@ include file="../jspf/footer.jspf" %>

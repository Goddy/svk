<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="jspf/header.jspf" %>

<div class="alert alert-info"><spring:message code="info.contact"/></div>
<%@ include file="jspf/resultMessage.jspf" %>
<form:form modelAttribute="form" cssClass="form-horizontal">
    <tag:formField path="email" label="label.email" title="label.email" type="text" optional="false"/>
    <tag:formField path="message" label="label.message" title="label.message" type="textarea" optional="false"
                   rows="10"/>
    <tag:catchpa privateKey="${privateKey}" publicKey="${publicKey}"/>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button id="btnSubmit" type="submit" class="btn btn-primary"><spring:message code='button.submit'/></button>
            <button id="btnReset" type="reset" class="btn btn-info"><spring:message code='button.reset'/></button>
            <a id="btnCancel" class="btn btn-default" href="/news/news.html"><spring:message code='button.cancel'/></a>
        </div>
    </div>
</form:form>
<%@ include file="jspf/footer.jspf" %>

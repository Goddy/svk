<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf" %>

<!-- Todo: WYSIWYG editor -->
<div class="panel panel-default">
    <div class="panel-body">
        <form:form modelAttribute="form" action="${command}" cssClass="form-horizontal">
        <form:hidden path="id"/>
        <tag:formField path="title" label="label.title" title="title.title" type="input" optional="false"/>
        <tag:formField path="body" label="label.body" title="title.body" type="textarea" optional="false"/>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button id="btnSubmit" type="submit" class="btn btn-primary"><spring:message code='button.submit'/></button>
                <button id="btnReset" type="reset" class="btn btn-info"><spring:message code='button.reset'/></button>
                <a id="btnCancel" class="btn btn-default" href="news.html"><spring:message code='button.cancel'/></a>
            </div>
        </div>
        </form:form>
        </div>
</div>

<%@ include file="../jspf/footer.jspf" %>

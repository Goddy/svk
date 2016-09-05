<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf" %>
<div class="col-md-12">
    <ul class="breadcrumb">
        <li><a href="/news.html"><spring:message code="nav.home"/></a>
        </li>
        <li><a href="/login.html"><spring:message code="text.login"/></a>
        </li>
        <li><spring:message code="button.register"/>
        </li>
    </ul>
</div>
<div class="col-md-12">
    <div class="box">
        <blockquote>
            <c:choose>
                <c:when test="${form.signInProvider == null}">
                    <spring:message code="text.register"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="text.register.social"/>
                </c:otherwise>
            </c:choose>
        </blockquote>
    </div>
</div>
<div class="col-md-12">
    <div class="box">
        <form:form action="register" autocomplete="false" modelAttribute="form" cssClass="form-horizontal">
            <form:hidden path="signInProvider"/>
            <tag:formField path="username" label="label.email" title="title.email" type="input" optional="false"/>
            <tag:formField path="firstName" label="label.firstName" title="label.firstName" type="input"
                           optional="false"/>
            <tag:formField path="lastName" label="label.lastName" title="label.lastName" type="input" optional="false"/>
            <c:if test="${form.signInProvider == null}">
                <tag:formField path="password" label="label.password" title="title.password" type="password"
                               optional="false"/>
                <tag:formField path="confirmPassword" label="label.confirmPassword" title="label.confirmPassword"
                               type="password" optional="false"/>
            </c:if>

            <tag:catchpa/>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="btnSubmit" type="submit" class="btn btn-primary"><spring:message
                            code='button.submit'/></button>
                    <button id="btnReset" type="reset" class="btn btn-info"><spring:message
                            code='button.reset'/></button>
                </div>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="../jspf/footer.jspf" %>
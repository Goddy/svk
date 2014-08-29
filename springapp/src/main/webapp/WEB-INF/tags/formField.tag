<%@tag description="Twitter Bootstrap Input Fields with validation message support" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="path" required="true" type="java.lang.String" %>
<%@attribute name="cssClass" required="false" type="java.lang.String" %>
<%@attribute name="label" required="true" type="java.lang.String" %>
<%@attribute name="title" required="true" type="java.lang.String" %>
<%@attribute name="type" required="true" type="java.lang.String" %>
<%@attribute name="optional" required="true" type="java.lang.Boolean" %>
<%@attribute name="placeholder" required="false" type="java.lang.String" %>

<spring:bind path="${path}">
    <c:choose>
        <c:when test="${type=='checkbox'}">
            <div class="form-group" title="${title}">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <form:checkbox path="${path}" cssClass="${cssClass}"/> <spring:message code="${label}"/>
                                <%--YAGNI For the moment it does not make sense to add support for error messages--%>
                        </label>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="form-group ${status.error ? 'has-error has-feedback' : '' }"
                 title="<spring:message code='${title}'/>">
                <label class="col-sm-2 col-sm-2 control-label" for="${path}">
                    <spring:message code="${label}"/><c:if test="${optional}">
                    <small class="optional-label">(<spring:message code='label.optional'/>)</small>
                </c:if>
                </label>

                <div class="col-sm-10">
                    <c:choose>
                        <c:when test="${type=='textarea'}">
                            <form:textarea cssClass="form-control ${cssClass}" path="${path}"
                                           placeholder="${placeholder}"/>
                        </c:when>
                        <c:when test="${type=='checkbox'}">
                            <form:checkbox cssClass="form-control ${cssClass}" path="${path}"/>
                        </c:when>
                        <c:when test="${type=='empty'}">
                        </c:when>
                        <c:when test="${type=='password'}">
                            <form:password cssClass="form-control ${cssClass}" path="${path}"/>
                        </c:when>
                        <c:otherwise>
                            <form:input cssClass="form-control ${cssClass}" path="${path}"/>
                        </c:otherwise>

                    </c:choose>
                    <c:if test="${status.error}">
                        <span class="help-block">${status.errorMessage}</span>
                        <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                    </c:if>
                        <%--Hook to add buttons to the form element--%>
                    <jsp:doBody/>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</spring:bind>


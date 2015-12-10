<%@tag description="static field" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="cssClass" required="false" type="java.lang.String" %>
<%@attribute name="label" required="true" type="java.lang.String" %>
<%@attribute name="title" required="true" type="java.lang.String" %>
<%@attribute name="type" required="true" type="java.lang.String" %>
<%@attribute name="value" required="false" type="java.lang.String" %>

<c:if test="${not empty value}">
    <div data-toggle="tooltip" class="row" title="<spring:message code='${title}'/>">
        <p>
        <c:choose>
            <c:when test="${type=='email'}">
                <i class="glyphicon glyphicon-envelope"></i>&nbsp;
            </c:when>
            <c:when test="${type=='mobile'}">
                <i class="glyphicon glyphicon-phone"></i>&nbsp;
            </c:when>
            <c:when test="${type=='phone'}">
                <i class="glyphicon glyphicon-phone-alt"></i>&nbsp;
            </c:when>
            <c:when test="${type=='address'}">
                <i class="glyphicon glyphicon-home"></i>&nbsp;
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${type=='email'}">
                <a href="mailto:${value}">${value}</a>
            </c:when>
            <c:when test="${type=='mobile' || type == 'phone'}">
                <a href="tel:${value}">${value}</a>
            </c:when>
            <c:when test="${type=='h4'}">
                <h4>${value}</h4>
            </c:when>

            <c:otherwise>
                ${value}
            </c:otherwise>
        </c:choose>
        </p>
        <jsp:doBody/>
    </div>
</c:if>


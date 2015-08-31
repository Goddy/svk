<%@tag description="Page component" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@attribute name="first" required="true" type="java.lang.String" %>
<%@attribute name="previous" required="true" type="java.lang.String" %>
<%@attribute name="next" required="true" type="java.lang.String" %>
<%@attribute name="last" required="true" type="java.lang.String" %>

<div class="text-center">
    <ul class="pagination blue">
        <c:if test="${not empty first}">
            <li><a href="<c:out value="${first}" />">&laquo;&nbsp;<spring:message code="text.first"/></a></li>
        </c:if>
        <c:if test="${not empty previous}">
            <li><a href="<c:out value="${previous}" />"><spring:message code="text.previous"/></a></li>
        </c:if>
        <c:if test="${not empty next}">
            <li><a href="<c:out value="${next}" />"><spring:message code="text.next"/></a></li>
        </c:if>
        <c:if test="${not empty last}">
            <li><a href="<c:out value="${last}" />"><spring:message code="text.last"/>&nbsp;&raquo;</a></li>
        </c:if>
    </ul>
</div>
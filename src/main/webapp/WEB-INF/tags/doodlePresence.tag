<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name='extraClass' required='false' %>
<%@ attribute name='account' required='false' type="be.spring.app.model.Account" %>
<%@ attribute name='match' required='true' type="be.spring.app.model.Match" %>
<%@ attribute name='isAdmin' required='true' %>
<%@ attribute name='isOwnAccount' required='false' %>
<%@ attribute name='returnUrl' required='true' %>

<c:set var="isPresent" value="${match.matchDoodle.isPresent(account)}"/>
<c:set var="isMatchPlayed" value="${match.played}"/>

<c:choose>
    <c:when test="${not empty account}">
        <c:set var="href" value="/doodle/changePresence.json?id=${account.id}&matchId=${match.id}"/>
    </c:when>
    <c:otherwise>
        <c:set var="href" value="${returnUrl}"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${isAdmin || (isOwnAccount && !isMatchPlayed) || empty account}">
        <c:set var="disabled" value=""/>
    </c:when>
    <c:otherwise>
        <c:set var="disabled" value="disabled"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${isPresent == 'NOT_FILLED_IN'}">
        <c:set var="classes" value="btn btn-default presence glyphicon glyphicon-question-sign grey"/>
    </c:when>
    <c:when test="${isPresent == 'PRESENT'}">
        <c:set var="classes" value="btn btn-default presence glyphicon glyphicon-ok green "/>
    </c:when>
    <c:when test="${isPresent == 'ANONYMOUS'}">
        <c:set var="classes" value="btn btn-default glyphicon glyphicon-lock grey"/>
    </c:when>
    <c:otherwise>
        <c:set var="classes" value="btn btn-default presence glyphicon glyphicon-remove red"/>
    </c:otherwise>
</c:choose>

<a href="${href}" data-toggle="tooltip" data-container="body" title="<spring:message code="title.doodleChange"/>"
   data-placement="top" ${disabled} class="${classes} ${extraClass}"></a>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name='extraClass' required='false' %>
<%@ attribute name='account' required='false' type="be.svk.webapp.model.Account" %>
<%@ attribute name='match' required='true' type="be.svk.webapp.model.Match" %>
<%@ attribute name='isAdmin' required='true' %>
<%@ attribute name='isOwnAccount' required='false' %>
<%@ attribute name='returnUrl' required='true' %>

<c:set var="isPresent" value="${match.matchDoodle.isPresent(account)}"/>
<c:set var="isNotPlayed" value="${match.status == 'NOT_PLAYED'}"/>

<c:choose>
    <c:when test="${not empty account}">
        <c:set var="href" value="/doodle/changePresence.json?id=${account.id}&matchId=${match.id}"/>
    </c:when>
    <c:otherwise>
        <c:set var="href" value="${returnUrl}"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${isAdmin || (isOwnAccount && isNotPlayed) || empty account}">
        <c:set var="disabled" value=""/>
    </c:when>
    <c:otherwise>
        <c:set var="disabled" value="disabled"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${isPresent == 'NOT_FILLED_IN'}">
        <c:set var="classes" value="glyphicon glyphicon-question-sign grey"/>
    </c:when>
    <c:when test="${isPresent == 'PRESENT'}">
        <c:set var="classes" value="glyphicon glyphicon-ok green "/>
    </c:when>
    <c:when test="${isPresent == 'ANONYMOUS'}">
        <c:set var="classes" value="glyphicon glyphicon-lock grey"/>
    </c:when>
    <c:otherwise>
        <c:set var="classes" value="glyphicon glyphicon-remove red"/>
    </c:otherwise>
</c:choose>
<c:set value="${isPresent == 'ANONYMOUS' ? '' : 'presence'}" var="presenceClass"/>

<a href="${href}" data-toggle="tooltip" data-container="body" title="<spring:message code="title.doodleChange"/>"
   data-placement="top" ${disabled} class="btn btn-default ${presenceClass} ${extraClass}"><span
        class="${classes}"
        aria-hidden="true"></span></a>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name='extraClass' required='false' %>
<%@ attribute name='account' required='true' type="be.spring.app.model.Account" %>
<%@ attribute name='match' required='true' type="be.spring.app.model.Match" %>
<%@ attribute name='isAdmin' required='true' %>

<c:set var="isPresent" value="${match.matchDoodle.isPresent(account)}"/>

<c:choose>
    <c:when test="${isAdmin}">
        <c:set var="disabled" value=""/>
    </c:when>
    <c:otherwise>
        <c:set var="disabled" value="disabled"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${isPresent == 'NOT_FILLED_IN'}">
        <c:set var="classes" value="btn btn-default presence glyphicon glyphicon-ok"/>
    </c:when>
    <c:when test="${isPresent == 'PRESENT'}">
        <c:set var="classes" value="btn btn-default presence glyphicon glyphicon-ok green "/>
    </c:when>
    <c:otherwise>
        <c:set var="classes" value="btn btn-default presence glyphicon glyphicon-remove red"/>
    </c:otherwise>
</c:choose>

<a href="/doodle/changePresence.json?id=${account.id}&matchId=${match.id}" data-toggle="tooltip"
   data-placement="top" ${disabled} class="${classes} ${extraClass}"></a>

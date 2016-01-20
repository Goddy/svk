<%@tag description="Faq component" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="returnUrl" required="true" type="java.lang.String" %>
<%@attribute name="match" required="true" type="be.spring.app.model.Match" %>
<%@attribute name="showUsers" required="true" type="java.lang.Boolean" %>

<c:set var="doodle" value="${match.matchDoodle}"/>
<c:if test="${not showUsers}">
    <c:set var="display" value="display: none"/>
</c:if>

<c:set var="isAdmin" value="false"/>
<c:set var="isAuthenticated" value="false"/>

<sec:authorize access="isAuthenticated()">
    <c:set var="isAuthenticated" value="true"/>
</sec:authorize>
<sec:authorize access="hasRole('ADMIN')">
    <c:set var="isAdmin" value="true"/>
</sec:authorize>
<div class="panel panel-default">
    <div class="panel-heading"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>&nbsp;<joda:format
            pattern="dd-MM-yyyy HH:mm" value="${match.date}" style="L-"/>
    </div>
    <div class="panel-body">
        <div class="doodle-title"><h3>${match.description}
        <c:if test="${match.status == 'CANCELLED'}">
            <b>(<spring:message code='label.match.status.CANCELLED'/>!)</b></c:if></h3>
        </div>
        <div class="doodle-badge btn-group btn-group-lg">
            <a class="btn btn-default glyphicon glyphicon-user doodle-users" data-toggle="tooltip" data-container="body"
               title="<spring:message code="title.doodlePresences"/>" aria-hidden="true"><span
                    class="count-badge">${doodle.countPresences()}</span>
            </a>
            <tag:doodlePresence account="${currentAccount}" match="${match}" isAdmin="${isAdmin}"
                                extraClass="doodle-user-btn" returnUrl="${returnUrl}" isOwnAccount="true"/>
        </div>
    </div>
    <div class="panel-body list" style="${display}">
        <c:forEach items="${accounts}" var="a">
            <div class="doodle-list">
                <c:choose>
                    <c:when test="${isAuthenticated}">
                        ${a.toString()}
                    </c:when>
                    <c:otherwise>
                        ${a.fullName}
                    </c:otherwise>
                </c:choose>
                <tag:doodlePresence account="${a}" isOwnAccount="${a.equals(currentAccount)}" match="${match}"
                                    isAdmin="${isAdmin}" returnUrl="${returnUrl}"/>
            </div>
        </c:forEach>
    </div>
</div>

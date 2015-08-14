<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
            pattern="dd-MM-yyyy HH:mm" value="${match.date}" style="L-"/></div>
    <div class="panel-body">
        <div class="doodle-title"><h3>${match.description}</h3>
        </div>
        <div class="doodle-badge btn-group">
            <a class="btn btn-default glyphicon glyphicon-user doodle-users" aria-hidden="true"><span
                    class="badge">${doodle.countPresences()}</span></a>
            <tag:doodlePresence account="${currentAccount}" match="${match}" isAdmin="${isAuthenticated}"
                                extraClass="doodle-user-btn"/>
        </div>
    </div>
    <div class="panel-body list" style="${display}">
        <c:forEach items="${accounts}" var="a">
            <div class="doodle-list">${a.fullName}
                <tag:doodlePresence account="${a}" match="${match}" isAdmin="${isAdmin}"/>
            </div>
        </c:forEach>
    </div>
</div>

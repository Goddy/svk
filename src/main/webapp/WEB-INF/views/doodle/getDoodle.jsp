<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="../jspf/header.jspf" %>
<script src="<c:url value='/resources/angular/controllers/doodles.js'/>?v=1.0"></script>
<c:set value="true" var="showUsers"/>
<div class="col-md-12" ng-app="soccerApp" ng-controller="doodleCtrl" data-ng-init="initSingle(${matchId})">
<div class="col-md-12">
    <ul class="breadcrumb">
        <li><a href="/news.html"><spring:message code="nav.home"/></a>
        </li>
        <li><a href="/doodle.html"><spring:message code="nav.doodle"/></a>
        </li>
        <li><spring:message code="nav.doodle"/> {{matchDoodle.description}}
        </li>
    </ul>
</div>

    <div class="col-md-12">
        <div ng-show="!hasMatchDoodle">
            <div class="alert alert-warning">
                <spring:message code="text.doodle.none.found"/>
            </div>
        </div>
        <div class="matchDoodle" ng-show="hasMatchDoodle">
            <div class="panel panel-default">
                <div class="panel-heading"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>&nbsp;{{matchDoodle.date}}
                </div>
                <div class="panel-body">
                    <div class="doodle-title">
                        <h3>{{matchDoodle.description}}
            <span ng-if="value.status == 'CANCELLED'">
              <b>(<spring:message code='label.match.status.CANCELLED'/>!)</b>
            </span>
                        </h3>
                    </div>
                    <div class="doodle-badge btn-group btn-group-lg">
                        <a class="btn btn-default doodle-users" data-toggle="tooltip" data-container="body"
                           ng-click="showUsers = !showUsers" ng-init="showUsers=true"
                           title="<spring:message code="title.doodlePresences"/>" aria-hidden="true"><span
                                class="glyphicon glyphicon-user"></span> <span
                                class="count-badge">{{matchDoodle.doodle.total}}</span>
                        </a>
                        <sec:authorize access="isAuthenticated()">
                            <a ng-click="changePresence(matchDoodle, matchDoodle.doodle.currentPresence, true)"
                               data-toggle="tooltip"
                               data-container="body" title="<spring:message code="title.doodleChange"/>"
                               data-placement="top" class="btn btn-default"><span
                                    ng-class="getPresenceClass(matchDoodle.doodle.currentPresence)"
                                    aria-hidden="true"></span></a>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <a href="/getMembersDoodle.html?matchId=${matchId}" data-toggle="tooltip"
                               data-container="body"
                               title="<spring:message code="title.doodleChange"/>"
                               data-placement="top" class="btn btn-default"><span class="glyphicon glyphicon-lock grey"
                                                                                  aria-hidden="true"></span></a>
                        </sec:authorize>
                    </div>
                </div>
                <div class="panel-body list">
                    <div class="doodle-list" ng-repeat="presence in matchDoodle.doodle.presences">
                                    <span class="doodle-list-name">
                          <div>{{presence.account.name}}</div>
                          <sec:authorize access="hasRole('ADMIN')">
                              <div ng-show="presence.modified != null && showModified"><i>(<spring:message
                                      code="text.modified"/>:
                                  {{presence.modified}})</i></div>
                          </sec:authorize>
                        </span>
                        <span class="doodle-list-btn">
                        <a ng-click="changePresence(matchDoodle, presence, presence.editable)" data-toggle="tooltip"
                           ng-disabled="!presence.editable"
                           data-container="body" class="btn btn-default"><span
                                ng-class="getPresenceClass(presence)"
                                aria-hidden="true"></span></a>
                        </span>
                    </div>
                </div>
                <sec:authorize access="hasRole('ADMIN')">
                    <div class="panel-body" ng-show="showUsers">
                        <a class="pull-right" data-toggle="tooltip" data-container="body"
                           ng-click="showModified = !showModified"
                           title="<spring:message code="title.toggle.doodle.time"/>" aria-hidden="true">
                            <span ng-show="!showModified"><spring:message code="text.show.dates"/></span>
                            <span ng-show="showModified"><spring:message code="text.hide.dates"/> </span>
                        </a>
                    </div>
                </sec:authorize>
            </div>
        </div>
<%@ include file="../jspf/footer.jspf" %>

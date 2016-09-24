<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="../jspf/header.jspf" %>

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
                        {{presence.account.name}}
                        <a ng-click="changePresence(matchDoodle, presence, presence.editable)" data-toggle="tooltip"
                           ng-disabled="!presence.editable"
                           data-container="body" class="btn btn-default"><span
                                ng-class="getPresenceClass(presence)"
                                aria-hidden="true"></span></a>
                    </div>
                </div>
</div>
        </div>

        <script src="<c:url value='/resources/angular/controllers/doodles.js'/>?v=1.0"></script>
<script src="<c:url value='/resources/js/svk-ui-1.5.js'/>"></script>
<script type="text/javascript">
    (function ($) {
        $(document).on('click', 'a[class*="doodle-users"]', function (e) {
            e.preventDefault();
            $(this).closest('div.panel').find('div.list').toggle();
        });

    })(jQuery);
</script>
<%@ include file="../jspf/footer.jspf" %>

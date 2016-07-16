<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>

<div class="alert alert-info"><spring:message code="info.matches"/></div>

<c:if test="${not empty nextMatch}">
    <joda:format value="${nextMatch.date}" var="nextMatchDate" pattern="dd-MM-yyyy HH:mm"/>
    <div class="panel panel-danger">
    <div class="panel-heading">
        <h3 class="panel-title"><spring:message code="text.next.match"/><c:if test="${nextMatch.status == 'CANCELLED'}">
            <b>(<spring:message code='label.match.status.CANCELLED'/>!)</b>
        </c:if></h3>
    </div>
    <div class="panel-body">
        <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
        <c:out value="${nextMatchDate}"/><br>
        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
        <c:out value="${nextMatch.description}"/><br>
        <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
        <c:choose>
            <c:when test="${not empty nextMatch.homeTeam.address.googleLink}">
                <a href="${nextMatch.homeTeam.address.googleLink}" class="map"><c:out
                        value="${nextMatch.homeTeam.address}"/></a>
            </c:when>
            <c:otherwise>
                <c:out value="${nextMatch.homeTeam.address}"/>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</c:if>

<%@ include file="../jspf/resultMessage.jspf" %>

<div class="panel-group" id="accordion" ng-app="soccerApp" ng-controller="matchCtrl" data-ng-init="init()">
        <div class="panel panel-default" ng-repeat="season in seasons">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-target="#collapse{{season.id}}"
                       href="#collapse{{season.id}}" class="season collapsed" id="{{season.id}}" ng-click="getMatches(season.id)">
                        <spring:message code="text.season"/> {{season.description}}
                    </a>
                    <a class="pull-right downloadCalendar"
                       href="/calendar/getMatchesCalendar.html?seasonId={{season.id}}"><span
                            class="glyphicon glyphicon-calendar"><spring:message code="text.export"/></span></a>
                </h4>
            </div>
            <div id="collapse{{season.id}}" class="panel-collapse {{$first ? 'collapse in' : 'collapse'}}">
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-hover rwd-table">
                            <tr>
                                <th><spring:message code='text.date'/></th>
                                <th><spring:message code='text.match'/></th>
                                <th><spring:message code='text.result'/></th>
                                <th><spring:message code='text.actions'/></th>
                            </tr>
                            <tbody ng-hide="!isNull(matchWrapper[season.id])"><tr><td colspan="4"><spring:message code='text.noMatches'/></td></tr></tbody>
                            <tbody ng-repeat="wrapper in matchWrapper[season.id]">
                            <tr>
                                <td data-th="<spring:message code='text.date'/>">{{wrapper.object.date}} - {{wrapper.object.hour}}</td>
                                <td data-th="<spring:message code='text.match'/>">{{wrapper.object.homeTeam}} - {{wrapper.object.awayTeam}}</td>
                                <td ng-switch="wrapper.object.status">
                                    <span class="animate-switch" ng-switch-when="PLAYED">{{wrapper.object.htGoals}} - {{wrapper.object.atGoals}}</span>
                                    <span class="animate-switch" ng-switch-when="NOT_PLAYED"><spring:message code='label.match.status.NOT_PLAYED'/></span>
                                    <span class="animate-switch" title="{{match.statusText}}" ng-switch-when="CANCELLED" data-toggle="tooltip" data-placement="top"><b><spring:message code='label.match.status.CANCELLED'/></b></span>
                                </td>
                                <td ng-bind-html="renderHtml(wrapper.additions['htmlActions'])"></td>
                            </tr>
                            <tr style="display: none" class="active" ng-attr-id="{{'details' + wrapper.object.id}}">
                                <td colspan="5">
                                    <spring:message code='text.goals'/>:<br/>
                                    <ul>
                                        <li ng-repeat="g in wrapper.object.goals">
                                            <span ng-if="g.scorer">{{g.scorer.name}}</span>
                                            <span ng-if="!g.scorer"><spring:message code="text.no.player"/></span>
                                            <span ng-if="g.assist">({{g.assist.name}})</span>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                            <tr style="display: none" class="active" ng-attr-id="{{'motm' + wrapper.object.id}}">
                                <td colspan="5">
                                    <div class="panel">
                                        <div class="panel-heading"><spring:message code="title.manOfTheMatchPoll"/> </div>
                                        <security:authorize access="isAuthenticated()">
                                        <div class="panel-body" ng-if="wrapper.object.poll.status == 'OPEN'" >
                                            <ul class="list-group" ng-repeat="x in wrapper.object.poll.votes">
                                                <li class="list-group item">
                                                    <div class="radio">
                                                        <label>
                                                            <input name="group-poll" ng-value="{{x.account}}" type="radio"  ng-model="$parent.selectedAccount">
                                                            {{x.account.name}}
                                                        </label>
                                                    </div>
                                                </li>
                                            </ul>
                                            <a href="#" ng-click="vote(wrapper.object, selectedAccount, wrapper.object.poll.id)" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-bell"></span> <spring:message code="label.vote"/></a>
                                        </div>
                                        </security:authorize>
                                        <div class="panel-footer">
                                            <div ng-if="wrapper.object.poll.totalVotes > 0">
                                                <div ng-repeat="x in wrapper.object.poll.votes">
                                                    {{x.account.name}}
                                                    <div class="progress">
                                                        <div class="progress-bar" role="progressbar" aria-valuenow="{{getPercentage(x.votes, wrapper.object.poll.totalVotes)}}"
                                                             aria-valuemin="0" aria-valuemax="100" ng-style="{width : ( getPercentage(x.votes, wrapper.object.poll.totalVotes) + '%' ) }">
                                                            {{getPercentage(x.votes, wrapper.object.poll.totalVotes)}}%
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div ng-if="wrapper.object.poll.totalVotes == 0">
                                                <spring:message code="text.no.votes"/>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value='/resources/js/svk-ui-1.4.js'/>"></script>

<script src="<c:url value='/resources/angular/controllers/matches.js'/>"></script>

<tag:deleteDialog dialogId="delete-match-modal"/>
<tag:calendarDialog dialogId="download-calendar-modal"/>
<tag:mapDialog dialogId="map-modal"/>
<script type="text/javascript">

    (function ($, dd, md, dc, utils) {
        var deleteMsg = "<spring:message code="text.delete.match"/>";
        var deleteTitle = "<spring:message code="title.delete.match"/>";
        var deleteMatchModal = $("#delete-match-modal");
        var mapModal = $("#map-modal");
        var downloadCalendarModal = $("#download-calendar-modal");
        var loggedIn = "${loggedIn}";

        $(document).on('click', 'a[class*="delete"]', function (e) {
            console.log("Clicked delete");
            e.preventDefault();
            var hTeam = $(this).parents('tr:first').find('td:nth-child(3)').text();
            var aTeam = $(this).parents('tr:first').find('td:nth-child(4)').text();
            var href = $(this).attr("href");
            var msg = hTeam + " - " + aTeam;
            dd.showDeleteDialog(deleteMatchModal, msg, deleteTitle, href)
        });

        $(document).on('click', 'a[class*="map"]', function (e) {
            e.preventDefault();
            var href = $(this).attr("href");
            md.showMapDialog(mapModal, href)
        });

        $(document).on('click', 'a[class*="downloadCalendar"]', function (e) {
            e.preventDefault();
            var href = $(this).attr("href");
            dc.showCalendarDialog(downloadCalendarModal, href)
        });

        $(document).on('click', 'a[class*="details"]', function (e) {
            e.preventDefault();
            var href = $(this).attr("href");
            $('#' + href).toggle();
        });

        $(document).on('click', 'a[class*="motm"]', function (e) {
            e.preventDefault();
            var href = $(this).attr("href");
            $('#' + href).toggle();
        });

        $(document).on('click', 'a[class*="presence"]', function (e) {
            e.preventDefault();
            var parent = $(this).parent();
            utils.jsonGet($(this).attr("href") , {} , function (data) {
                parent.html(data);
            });
        });

    })(jQuery, svk.deleteDialogs, svk.mapDialog, svk.calendarDialog, svk.utils);


</script>
<%@ include file="../jspf/footer.jspf" %>




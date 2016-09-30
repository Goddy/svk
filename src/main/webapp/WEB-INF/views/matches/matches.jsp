<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>
<div ng-app="soccerApp" ng-controller="matchCtrl" data-ng-init="init()">
<div class="col-md-12">
    <ul class="breadcrumb">
        <li><a href="/news.html"><spring:message code="nav.home"/></a>
        </li>
        <li><spring:message code="nav.matches"/>
        </li>
    </ul>
</div>

    <div class="col-md-12" ng-show="hasNextMatch && !loading">
        <div class="box">
            <h2><spring:message code="text.next.match"/><b ng-show="nextMatch.status == 'CANCELLED'">(<spring:message
                    code='label.match.status.CANCELLED'/>!)</b></h2>
            <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
            {{nextMatch.date}} {{nextMatch.hour}}<br>
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
            {{nextMatch.homeTeam}} - {{nextMatch.awayTeam}}<br>
            <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
            <span ng-show="nextMatch.locationUrl !== ''">
                <a href="{{nextMatch.locationUrl}}" class="map">{{nextMatch.fullAddress}}</a>
            </span>
            <span ng-show="nextMatch.locationUrl == ''">
                {{nextMatch.fullAddress}}
            </span>
        </div>
    </div>

<div class="col-md-12">
    <%@ include file="../jspf/resultMessage.jspf" %>
    <tag:loading/>
    <div class="panel-group" id="accordion" ng-show="!loading">
        <div class="panel panel-default" ng-repeat="season in seasons">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-target="#collapse{{season.id}}"
                       href="#collapse{{season.id}}" class="season collapsed" id="{{season.id}}"
                       ng-click="getMatches(season.id)">
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
                            <tbody ng-show="!loading && isNull(matchWrapper[season.id])">
                            <tr>
                                <td colspan="4"><spring:message code='text.noMatches'/></td>
                            </tr>
                            </tbody>
                            <tbody ng-repeat="wrapper in matchWrapper[season.id]">
                            <tr>
                                <td data-th="<spring:message code='text.date'/>">{{wrapper.object.date}} -
                                    {{wrapper.object.hour}}
                                </td>
                                <td data-th="<spring:message code='text.match'/>">{{wrapper.object.homeTeam}} -
                                    {{wrapper.object.awayTeam}}
                                </td>
                                <td ng-switch="wrapper.object.status">
                                    <span class="animate-switch" ng-switch-when="PLAYED">{{wrapper.object.htGoals}} - {{wrapper.object.atGoals}}</span>
                                    <span class="animate-switch" ng-switch-when="NOT_PLAYED"><spring:message
                                            code='label.match.status.NOT_PLAYED'/></span>
                                    <span class="animate-switch" title="{{match.statusText}}" ng-switch-when="CANCELLED"
                                          data-toggle="tooltip" data-placement="top"><b><spring:message
                                            code='label.match.status.CANCELLED'/></b></span>
                                </td>
                                <td>
                                    <div class="btn-group">
                                        <security:authorize access="hasRole('ADMIN')">
                                            <a href="changeMatch.html?matchId={{wrapper.object.id}}"
                                               title="<spring:message code="title.changeMatchResult"/>"
                                               data-toggle="tooltip" data-placement="top"
                                               class="btn btn-default edit ">
                                                <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                            </a>
                                            <a href="deleteMatch.html?matchId={{wrapper.object.id}}"
                                               title="<spring:message code="title.deleteMatch"/>" data-toggle="tooltip"
                                               data-placement="top"
                                               class="btn btn-default delete">
                                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                            </a>
                                        </security:authorize>
                                        <a ng-if="wrapper.object.locationUrl" href="{{wrapper.object.locationUrl}}"
                                           title="" data-toggle="tooltip" data-placement="top"
                                           class="btn btn-default map"
                                           data-original-title="Show the match location on a map">
                                            <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
                                        </a>
                                        <a ng-if="wrapper.object.status == 'PLAYED' && wrapper.object.goals.length"
                                           href="details{{wrapper.object.id}}"
                                           title="<spring:message code="title.matchDetails"/>" data-toggle="tooltip"
                                           data-placement="top"
                                           class="btn btn-default details">
                                            <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
                                        </a>
                                        <a ng-if="wrapper.object.hasDoodle"
                                           href="getDoodle.html?matchId={{wrapper.object.id}}"
                                           title="<spring:message code="title.matchDoodle"/>" data-toggle="tooltip"
                                           data-placement="top"
                                           class="btn btn-default doodle">
                                            <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
                                        </a>
                                        <a ng-if="wrapper.object.poll" href="motm{{wrapper.object.id}}" title=""
                                           data-toggle="tooltip" data-placement="top"
                                           class="btn btn-default motm"
                                           data-original-title="<spring:message code="title.manOfTheMatchPoll"/>">
                                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                        </a>
                                    </div>

                                </td>
                            </tr>
                            <tr style="display: none" class="active" ng-attr-id="{{'details' + wrapper.object.id}}"
                                ng-if="wrapper.object.goals.length">
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
                                        <div class="panel-heading"><spring:message
                                                code="title.manOfTheMatchPoll"/></div>
                                        <security:authorize access="isAuthenticated()">
                                            <div class="panel-body" ng-if="wrapper.object.poll.status == 'OPEN'">
                                                <div class="input-group">
                                                    <select class="form-control" name="group-poll"
                                                            ng-model="$parent.selectedAccount"
                                                            ng-init="$parent.selectedAccount='none'">
                                                        <option ng-selected="true" value="none"><spring:message
                                                                code="text.select.player"/></option>
                                                        <option ng-repeat="option in wrapper.object.poll.options"
                                                                ng-value="option.id">{{option.name}}
                                                        </option>
                                                    </select>

                                                    <div class="input-group-btn">
                                                        <button ng-click="vote(wrapper.object, selectedAccount, wrapper.object.poll.id)"
                                                                class="btn btn-success"><span
                                                                class="glyphicon glyphicon-bell"></span> <spring:message
                                                                code="label.vote"/></button>
                                                    </div>
                                                </div>
                                                <div class="m-t-1" ng-if="voteResultMessage[wrapper.object.poll.id]">
                                                    <b>{{voteResultMessage[wrapper.object.poll.id]}}</b>
                                                </div>
                                            </div>
                                        </security:authorize>
                                        <div class="panel-footer">
                                            <div ng-if="wrapper.object.poll.totalVotes > 0">
                                                <div ng-repeat="x in wrapper.object.poll.votes">
                                                    <div ng-show="$index < 5 || show">
                                                        {{x.account.name}}
                                                            <span ng-if="x.votes != 1">({{x.votes}} <spring:message
                                                                    code="text.votes"/>)</span>
                                                            <span ng-if="x.votes == 1">({{x.votes}} <spring:message
                                                                    code="text.vote"/>)</span>

                                                        <div class="progress">
                                                            <div class="progress-bar" role="progressbar"
                                                                 aria-valuenow="{{getPercentage(x.votes, wrapper.object.poll.totalVotes)}}"
                                                                 aria-valuemin="0" aria-valuemax="100"
                                                                 ng-style="{width : ( getPercentage(x.votes, wrapper.object.poll.totalVotes) + '%' ) }">
                                                                {{getPercentage(x.votes,
                                                                wrapper.object.poll.totalVotes)}}%
                                                            </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                <a class="btn" ng-show="!show" ng-click="show=true"><spring:message
                                                        code="text.show.more"/></a>
                                                <a class="btn" ng-show="show" ng-click="show=false"><spring:message
                                                        code="text.show.less"/></a>
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
</div>
</div>
<script src="<c:url value='/resources/js/svk-ui-1.5.js'/>"></script>

<script src="<c:url value='/resources/angular/controllers/matches.js'/>?v=1.0"></script>

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
            var hTeam = $(this).parents('tr:first').find('td:nth-child(1)').text();
            var aTeam = $(this).parents('tr:first').find('td:nth-child(2)').text();
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




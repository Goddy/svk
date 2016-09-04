<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>
<div class="col-md-12">
    <ul class="breadcrumb">
        <li><a href="/news.html"><spring:message code="nav.home"/></a>
        </li>
        <li><a href="/matches.html"><spring:message code="nav.matches"/></a>
        </li>
        <li><spring:message code="nav.matchPolls"/>
        </li>
    </ul>
</div>
<div class="m-t-1" ng-app="soccerApp" ng-controller="matchPollCtrl" data-ng-init="init()">
    <div class="row m-b-1">
        <div class="text-center">
            <ul class="pagination blue">
                <li>
                    <a ng-click="getPage(currentPage - 1)" ng-if="matchPolls.hasPrevious">
                        &laquo;
                    </a>
                </li>
                <li ng-repeat="n in [].constructor(matchPolls.totalPages) track by $index" ng-class="{'active': $index==currentPage}">
                    <a ng-click="getPage($index)">
                        {{$index + 1}}
                    </a>
                </li>
                <li>
                    <a ng-click="getPage(currentPage + 1)" ng-if="matchPolls.hasNext">
                        &raquo;
                    </a>
                </li>
            </ul>
        </div>
    </div>

    <div ng-if="!hasMatchPolls" class="alert alert-warning">
        <spring:message code="text.noMatchPolls"/>
    </div>
    <div ng-repeat="(key, value) in matchPolls.list">
        <div class="clearfix" ng-if="$index % 2 == 0"></div>
        <div class="col-md-6">
            <div class="panel panel-info">
                <div>
                    <div class="panel-heading">
                        <h3 class="text-center">{{value.matchDescription}}</h3>
                        <p class="text-center">{{value.matchDate}}</p>
                        <security:authorize access="hasRole('ADMIN')">
                            <div class="btn-group" role="group" aria-label="...">
                                <button ng-click="refresh(value)" type="button" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="<spring:message code="title.refresh.matchPoll"/>">
                                    <spring:message code="button.refresh"/>
                                </button>
                                <button ng-click="reset(value)" type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="top" title="<spring:message code="title.reset.matchPoll"/>">
                                    <spring:message code="button.reset"/>
                                </button>
                            </div>
                            <div class="m-t-1" ng-if="actionResultMessage[value.id]">
                                <b>{{actionResultMessage[value.id]}}</b>
                            </div>
                        </security:authorize>
                    </div>
                    <security:authorize access="isAuthenticated()">
                        <div class="panel-body" ng-if="value.status == 'OPEN'" >
                            <div class="input-group">
                                <select class="form-control" name="group-poll" ng-model="$parent.selectedAccount"
                                        ng-init="$parent.selectedAccount='none'">
                                    <option ng-selected="true" value="none"><spring:message
                                            code="text.select.player"/></option>
                                    <option ng-repeat="option in value.options" ng-value="option.id">{{option.name}}
                                    </option>
                                </select>

                                <div class="input-group-btn">
                                    <button ng-click="vote(selectedAccount, value)" class="btn btn-success"><span
                                            class="glyphicon glyphicon-bell"></span> <spring:message code="label.vote"/>
                                    </button>
                                </div>
                            </div>
                            <div class="m-t-1" ng-if="voteResultMessage[value.id]">
                                <b>{{voteResultMessage[value.id]}}</b>
                            </div>
                        </div>
                    </security:authorize>
                    <div class="panel-footer">
                        <div ng-if="value.totalVotes > 0">
                            <div ng-repeat="x in value.votes">
                                <div ng-show="$index < 4 || show">
                                    {{x.account.name}}
                                    <span ng-if="x.votes != 1">({{x.votes}} <spring:message code="text.votes"/>)</span>
                                    <span ng-if="x.votes == 1">({{x.votes}} <spring:message code="text.vote"/>)</span>

                                    <div class="progress">
                                        <div class="progress-bar" role="progressbar"
                                             aria-valuenow="{{getPercentage(x.votes, value.totalVotes)}}"
                                             aria-valuemin="0" aria-valuemax="100"
                                             ng-style="{width : ( getPercentage(x.votes, value.totalVotes) + '%' ) }">
                                            {{getPercentage(x.votes, value.totalVotes)}}%
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <a class="btn" ng-show="!show" ng-click="show=true"><spring:message
                                    code="text.show.more"/></a>
                            <a class="btn" ng-show="show" ng-click="show=false"><spring:message
                                    code="text.show.less"/></a>
                        </div>
                        <div ng-if="value.totalVotes == 0">
                            <spring:message code="text.no.votes"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="text-center">
            <ul class="pagination blue">
                <li>
                    <a ng-click="getPage(currentPage - 1)" ng-if="matchPolls.hasPrevious">
                        &laquo;
                    </a>
                </li>
                <li ng-repeat="n in [].constructor(matchPolls.totalPages) track by $index" ng-class="{'active': $index==currentPage}">
                    <a ng-click="getPage($index)">
                        {{$index + 1}}
                    </a>
                </li>
                <li>
                    <a ng-click="getPage(currentPage + 1)" ng-if="matchPolls.hasNext">
                        &raquo;
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>

<script src="<c:url value='/resources/angular/controllers/polls.js'/>"></script>

<%@ include file="../jspf/footer.jspf" %>
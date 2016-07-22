<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>

<div class="m-t-1" ng-app="soccerApp" ng-controller="matchPollCtrl" data-ng-init="init()">
    <div class="col-md-6" ng-repeat="poll in matchPolls.list">
        <div class="panel panel-info">
            <div>
                <div class="panel-heading"><h2><spring:message code="title.manOfTheMatchPoll"/></h2><h3>{{poll.matchDescription}} - {{poll.matchDate}}</h3></div>
                <security:authorize access="isAuthenticated()">
                    <div class="panel-body" ng-if="poll.status == 'OPEN'" >
                        <ul class="list-group" ng-repeat="x in poll.votes">
                            <li class="list-group item">
                                <div class="radio">
                                    <label>
                                        <input name="group-poll" ng-value="{{x.account}}" type="radio"  ng-model="$parent.selectedAccount">
                                        {{x.account.name}}
                                    </label>
                                </div>
                            </li>
                        </ul>
                        <a href="#" ng-click="vote(selectedAccount, poll)" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-bell"></span> <spring:message code="label.vote"/></a>
                    </div>
                </security:authorize>
                <div class="panel-footer">
                    <div ng-if="poll.totalVotes > 0">
                        <div ng-repeat="x in poll.votes">
                            {{x.account.name}}
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="{{getPercentage(x.votes, poll.totalVotes)}}"
                                     aria-valuemin="0" aria-valuemax="100" ng-style="{width : ( getPercentage(x.votes, poll.totalVotes) + '%' ) }">
                                    {{getPercentage(x.votes, poll.totalVotes)}}%
                                </div>
                            </div>
                        </div>
                    </div>
                    <div ng-if="poll.totalVotes == 0">
                        <spring:message code="text.no.votes"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value='/resources/angular/controllers/polls.js'/>"></script>

<%@ include file="../jspf/footer.jspf" %>
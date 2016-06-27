<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>

<div class="row" ng-app="soccerApp" ng-controller="matchPollCtrl">
    <div class="col-md-4 col-md-offset-4">
        <div class="user-poll-section">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <strong>Question : </strong>Which is the best responsive framework to start web designing ?
                </div>
                <div class="panel-body">
                    <div class="radio" ng-repeat="x in votes">
                        <label>
                            <input name="group-poll" ng-value="{{x.account}}" type="radio"  ng-model="$parent.selectedAccount">
                            {{x.account.name}}
                        </label>
                    </div>
                    Selected value {{selectedAccount}}
                </div>

                <hr>
                <h5 class="text-danger">Result Of User Votes :</h5>
                <hr>
                <div ng-if="totalVotes > 0">
                    <div ng-repeat="x in votes">
                        <div class="progress progress-striped active">
                            <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="{{getPercentage(x.votes)}}"
                                 aria-valuemin="0" aria-valuemax="100" ng-style="{width : ( getPercentage(x.votes) + '%' ) }">
                                {{x.account.name}} {{getPercentage(x.votes)}}%
                            </div>
                        </div>
                    </div>
                </div>
                <div ng-if="totalVotes == 0">
                    No votes yet.
                </div>


                <div class="panel-footer">
                    <a href="#" ng-click="vote(selectedAccount)" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-bell"></span> Mark Your Vote</a>
                </div>

                {{message}}
            </div>
        </div>
        <!-- POLL DIV END-->
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>

<script type="text/javascript">
    (function ($, angular) {
        var app = angular.module('soccerApp', []);
        app.controller('matchPollCtrl', function($scope, $http) {
            $scope.selectedAccount = "Test";

            $scope.getVotes = function () {
                $http.get('/api/v1/match/3/poll').success(function (data) {
                    $scope.votes = data.votes;
                    $scope.totalVotes = data.totalVotes;
                });
            };

            $scope.getPercentage = function (votes) {
                if ($scope.totalVotes === 0) {
                    return 0;
                }
                else {
                    return ((votes / $scope.totalVotes) * 100).toFixed(2);
                }
            };

            $scope.vote = function(selectedAccount) {
                $http({
                    url: '/api/v1/match/poll/1/vote',
                    method: "POST",
                    data: {answer: selectedAccount.id},
                }).success(function (data, status, headers, config) {
                    $scope.message = "Vote recorded"; // assign  $scope.persons here as promise is resolved here
                    $scope.getVotes();
                }).error(function (data, status, headers, config) {
                    $scope.message = "Vote failed";
                });
            };

            //Init votes
            $scope.getVotes();
        });
    })(jQuery, angular);


</script>
<%@ include file="../jspf/footer.jspf" %>
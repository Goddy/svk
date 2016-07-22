'use strict'
app.controller('matchPollCtrl', function($scope, $http, pollService) {
    var getPolls = pollService.getMatchPollPage(0);

    $scope.getPercentage = pollService.getPercentage;

    $scope.vote = function(account, pollId) {
        pollService.vote(account, pollId).success(function (data, status, headers, config) {
            $scope.message = "Vote recorded";
            pollService.getMatchPoll(pollId).success(function(data) {
                matchPoll.votes = data.votes;
                console.log('voted');
            });
        }).error(function (data, status, headers, config) {
            $scope.message = "Vote failed";
        });
    };

    $scope.init = function () {
        //Get all seasons
        pollService.getMatchPollPage(0).success(function (data) {
            $scope.matchPolls = data;
        });
    };
});

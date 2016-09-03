'use strict'
app.controller('newsCtrl', function($scope, $http, $sce, pollService) {
    $scope.vote = function(option, poll) {
        pollService.vote(option, poll.id).success(function (data, status, headers, config) {
            $scope.message = "Vote recorded";
            pollService.getMatchPoll(pollId).success(function(data) {
                poll = data;
                console.log('voted');
            });
        }).error(function (data, status, headers, config) {
            $scope.message = "Vote failed";
        });
    };

    $scope.getPercentage = pollService.getPercentage;

    var getPoll = function() {
        pollService.getLatestMatchPoll().success(function (data) {
            $scope.motmPoll = data;
        })
    };


    $scope.init = function () {
        //Get latest poll
        getPoll();
    };
});

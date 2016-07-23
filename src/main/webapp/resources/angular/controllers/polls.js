'use strict';
app.controller('matchPollCtrl', function($scope, $http, pollService) {
    var getPolls = function(page) {
        return pollService.getMatchPollPage(page).success(function(data){
            $scope.matchPolls = data;
            $scope.currentPage = page;
        });
    };

    $scope.getPercentage = pollService.getPercentage;

    $scope.vote = function(account, poll) {
        pollService.vote(account, poll.id).success(function (data, status, headers, config) {
            $scope.message = "Vote recorded";
            pollService.getMatchPoll(poll.id).success(function(data) {
                poll.votes = data.votes;
                poll.totalVotes = data.totalVotes; //update totalvotes as well, to update dom
                console.log('voted');
            });
        }).error(function (data, status, headers, config) {
            $scope.message = "Vote failed";
        });
    };

    $scope.refresh = function(poll) {
      pollService.refresh(poll).success(function(data) {
          poll.options = data;
      });
    };

    $scope.reset = function(poll) {
        pollService.reset(poll).success(function(data) {
            poll.votes = data;
        });
    };

    $scope.getPage = function(page) {
       getPolls(page)
    };

    $scope.init = function () {
        getPolls(0);
    };
});

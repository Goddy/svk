'use strict';
app.controller('matchPollCtrl', function($scope, $http, pollService, messageService, $timeout, $parse) {
    $scope.doodleResultMessage = [];
    $scope.actionResultMessage = [];

    var getPolls = function(page) {
        return pollService.getMatchPollPage(page).success(function(data){
            $scope.matchPolls = data;
            $scope.currentPage = page;
            $scope.hasPrevious = data.hasPrevious;
            $scope.hasNext = data.hasNext;
            $scope.totalPages = data.totalPages;
            $scope.hasMatchPolls=data.list.length;
        });
    };

    $scope.getPercentage = pollService.getPercentage;

    $scope.vote = function(option, poll) {
        if (option !== 'none') {
            pollService.vote(option, poll.id)
                .success(function (data, status, headers, config) {
                    pollService.getMatchPoll(poll.id)
                        .success(function (data) {
                            poll.votes = data.votes;
                            poll.totalVotes = data.totalVotes; //update totalvotes as well, to update dom
                            messageService.showMessage(function (message) {
                                $scope.doodleResultMessage[poll.id] = message;
                            }, 'alert.vote.success');

                            console.log('voted');
                        });
                }).error(function (data, status, headers, config) {
                messageService.showMessage(function (message) {
                    $scope.doodleResultMessage[poll.id] = message;
                }, 'alert.vote.fail');
                console.log('vote failed');
            });
        }
    };

    $scope.refresh = function(poll) {
      pollService.refresh(poll)
          .success(function(data) {
          poll.options = data;
          messageService.showMessage(function (message) {
              $scope.actionResultMessage[poll.id] = message;
          }, 'alert.poll.refresh.success');
      }).error(function() {
          messageService.showMessage(function (message) {
              $scope.actionResultMessage[poll.id] = message;
          }, 'alert.poll.refresh.fail');
      });
    };

    $scope.reset = function(poll) {
        pollService.reset(poll).success(function(data) {
            poll.totalVotes = 0;
            messageService.showMessage(function (message) {
                $scope.actionResultMessage[poll.id] = message;
            }, 'alert.poll.reset.success');
        }).error(function() {
            messageService.showMessage(function (message) {
                $scope.actionResultMessage[poll.id] = message;
            }, 'alert.poll.reset.fail');
        });
    };

    $scope.getPage = function(page) {
       getPolls(page)
    };

    $scope.init = function () {
        getPolls(0);
    };
});

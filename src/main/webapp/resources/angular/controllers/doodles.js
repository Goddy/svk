'use strict';
app.controller('doodleCtrl', function ($scope, $http, doodleService, messageService, $timeout, $parse) {
    $scope.doodleResultMessage = [];
    $scope.actionResultMessage = [];

    var getDoodles = function (page) {
        return pollService.getMatchPollPage(page).success(function (data) {
            $scope.matchDoodles = data;
            $scope.currentPage = page;
            $scope.hasMatchDoodles = data.list.length;
        });
    };

    $scope.changePresence = function (accountId, doodleId) {
        doodleService.changePresence(accountId, doodleId)
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
    };

    $scope.refresh = function (poll) {
        pollService.refresh(poll)
            .success(function (data) {
                poll.options = data;
                messageService.showMessage(function (message) {
                    $scope.actionResultMessage[poll.id] = message;
                }, 'alert.poll.refresh.success');
            }).error(function () {
            messageService.showMessage(function (message) {
                $scope.actionResultMessage[poll.id] = message;
            }, 'alert.poll.refresh.fail');
        });
    };

    $scope.reset = function (poll) {
        pollService.reset(poll).success(function (data) {
            poll.totalVotes = 0;
            messageService.showMessage(function (message) {
                $scope.actionResultMessage[poll.id] = message;
            }, 'alert.poll.reset.success');
        }).error(function () {
            messageService.showMessage(function (message) {
                $scope.actionResultMessage[poll.id] = message;
            }, 'alert.poll.reset.fail');
        });
    };

    $scope.getPage = function (page) {
        getPolls(page)
    };

    $scope.init = function () {
        getPolls(0);
    };
});

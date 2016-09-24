'use strict';
app.controller('doodleCtrl', function ($scope, $http, doodleService, messageService, $timeout, $parse) {
    $scope.doodleResultMessage = [];
    $scope.actionResultMessage = [];

    var getDoodles = function (page) {
        $scope.loading = true;
        return doodleService.getMatchDoodles(page).success(function (data) {
            $scope.loading = false;
            $scope.matchDoodles = data;
            $scope.currentPage = page;
            $scope.hasPrevious = data.hasPrevious;
            $scope.hasNext = data.hasNext;
            $scope.totalPages = data.totalPages;
            $scope.hasMatchDoodles = data.list.length;
        }).finally(function () {
            $scope.loading = false;
        });
    };

    $scope.changePresence = function (matchDoodle, presence, editable) {
        if (editable) {
            $scope.loading = true;
            var doodleId = matchDoodle.doodle.id;
            var matchId = matchDoodle.id;
            doodleService.changePresence(presence.account.id, matchId)
                .success(function () {
                    doodleService.getMatchDoodle(matchId).success(function (data) {
                        matchDoodle.doodle = data.doodle;
                        $scope.loading = false;
                    }).$error(function (data) {
                        $scope.loading = false;
                    });

                    console.log('changed doodle succesfully');
                }).error(function (data, status, headers, config) {
                $scope.loading = false;
                messageService.showMessage(function (message) {
                    $scope.doodleResultMessage[matchId] = message;
                }, 'alert.vote.fail');
                console.log('vote failed');
            });
        }
    };

    $scope.getPresenceClass = function (presence) {
        return doodleService.getPresenceClass(presence);
    };

    $scope.getPage = function (page) {
        getDoodles(page)
    };

    $scope.init = function () {
        getDoodles(0);
    };

    $scope.initSingle = function (matchId) {
        return doodleService.getMatchDoodle(matchId).success(function (data) {
            $scope.matchDoodle = data;
            $scope.hasMatchDoodle = true;
        }).error(function () {
            $scope.hasMatchDoodle = false;
        });
    };


});
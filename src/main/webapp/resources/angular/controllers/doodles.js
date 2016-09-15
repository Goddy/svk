'use strict';
app.controller('doodleCtrl', function ($scope, $http, doodleService, messageService, $timeout, $parse) {
    $scope.doodleResultMessage = [];
    $scope.actionResultMessage = [];

    var getDoodles = function (page) {
        return doodleService.getMatchDoodles(page).success(function (data) {
            $scope.matchDoodles = data;
            $scope.currentPage = page;
            $scope.hasMatchDoodles = data.list.length;
        });
    };

    $scope.changePresence = function (matchDoodle, presence) {
        var doodleId = matchDoodle.doodle.id;
        doodleService.changePresence(presence.account.id, doodleId)
            .success(function () {
                doodleService.getMatchDoodle(doodleId).succes(function (data) {
                    matchDoodle = data;
                });

                console.log('changed doodle succesfully');
            }).error(function (data, status, headers, config) {
            messageService.showMessage(function (message) {
                $scope.doodleResultMessage[doodleId] = message;
            }, 'alert.vote.fail');
            console.log('vote failed');
        });
    };

    $scope.getPresenceClass = function (presence) {
        return doodleService.getPresenceClass(presence);
    };

    $scope.getPage = function (page) {
        getPolls(page)
    };

    $scope.init = function () {
        getDoodles(0);
    };
});

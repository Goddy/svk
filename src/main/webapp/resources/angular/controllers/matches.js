'use strict';
app.controller('matchCtrl', function ($scope, $http, $sce, pollService, messageService) {
    $scope.cached = {};
    $scope.voteResultMessage = [];
    $scope.matchWrapper = {};

    var getSeasons = function () {
        $http({
            url: '/api/v1/seasons',
            method: "GET"
        }).success(function (data, status, headers, config) {
            $scope.seasons = data;
            //Get matches
            getMatches(data[0].id);
        }).error(function (data, status, headers, config) {
            console.log("Error getting seasons")
        });
    };

    $scope.isNull = function (element) {
        return element == null || element == undefined;
    };

    var getMatches = function (season) {
        if (!$scope.cached[season]) {
            $http({
                url: '/api/v1/matches/season/' + season,
                method: "GET"
            }).success(function (data, status, headers, config) {
                $scope.matchWrapper[season] = data;
                //Mark as cached
                $scope.cached[season] = true;
            }).error(function (data, status, headers, config) {
                console.log("Error getting matches")
            });
        }
    };

    $scope.getMatches = function (season) {
        getMatches(season)
    };

    $scope.renderHtml = function (htmlCode) {
        return $sce.trustAsHtml(htmlCode);
    };

    $scope.getPercentage = pollService.getPercentage;

    $scope.vote = function(matchObject, account, pollId) {
        pollService.vote(account, pollId).success(function (data, status, headers, config) {
            $scope.message = "Vote recorded";
            pollService.getMatchPoll(pollId).success(function(data) {
                matchObject.poll = data;
                messageService.showMessage(function (message) {
                    $scope.voteResultMessage[pollId] = message;
                }, 'alert.vote.success');
            });
        }).error(function (data, status, headers, config) {
            messageService.showMessage(function (message) {
                $scope.voteResultMessage[pollId] = message;
            }, 'alert.vote.fail');
        });
    };

    $scope.init = function () {
        //Get all seasons
        getSeasons();
    };
});

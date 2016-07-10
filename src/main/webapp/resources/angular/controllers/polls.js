'use strict'
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

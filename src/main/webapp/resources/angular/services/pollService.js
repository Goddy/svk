'use strict';
app.factory('pollService', function($http) {

    return {
        getPoll: function (matchId) {
            return $http.get('/api/v1/match/' + matchId + '/poll');
        },

        getMatchPollPage: function (page) {
            return $http.get('/api/v1/matchPoll?start=' + page + '&size=10');
        },

        getMatchPoll: function (pollId) {
            return $http.get('/api/v1/matchPoll/' + pollId);
        },

        getLatestMatchPoll: function () {
            return $http.get('/api/v1//match/latest/poll');
        },

        getPercentage: function (votes, totalVotes) {
            if (totalVotes === 0) {
                return 0;
            }
            else {
                return ((votes / totalVotes) * 100).toFixed(2);
            }
        },

        vote: function (selectedAccount, matchPollId) {
            if (selectedAccount && matchPollId) {
                return $http({
                    url: '/api/v1/matchPoll/' + matchPollId + '/vote',
                    method: "POST",
                    data: {answer: selectedAccount.id}
                });
            }
        }
    }
});

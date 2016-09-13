'use strict';
app.factory('pollService', function($http) {

    return {
        getPoll: function (matchId) {
            return $http.get('/api/v1/match/' + matchId + '/poll');
        },

        getMatchPollPage: function (page) {
            return $http.get('/api/v1/matchPoll?page=' + page + '&size=5');
        },

        refresh: function(poll) {
            return $http.put('/api/v1/matchPoll/match/' + poll.matchId +'/refresh');
        },

        reset: function(poll) {
            return $http.put('/api/v1/poll/' + poll.id +'/reset');
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
                return ((votes / totalVotes) * 100).toFixed(1);
            }
        },

        vote: function (option, matchPollId) {
            if (option && matchPollId) {
                return $http({
                    url: '/api/v1/matchPoll/' + matchPollId + '/vote',
                    method: "POST",
                    data: {answer: option}
                });
            }
        }
    }
});

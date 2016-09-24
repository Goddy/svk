'use strict';
app.factory('doodleService', function ($http) {

    return {
        getMatchDoodles: function (page) {
            return $http.get('/api/v1/matchDoodle?page=' + page + '&size=10');
        },

        getMatchDoodle: function (matchId) {
            return $http.get('/api/v1/matchDoodle/' + matchId);
        },

        changePresence: function (accountId, matchId) {
            if (accountId && matchId) {
                return $http.put('/api/v1/doodle/match/' + matchId + '/presence/' + accountId);
            }
        },
        getPresenceClass: function (presence) {
            switch (presence.type) {
                case 'NOT_FILLED_IN':
                    return "glyphicon glyphicon-question-sign grey";
                case 'PRESENT':
                    return "glyphicon glyphicon-ok green";
                case 'NOT_PRESENT':
                    return "glyphicon glyphicon-remove red";
                default:
                    return "glyphicon glyphicon-lock grey";
            }
        }
    }
});

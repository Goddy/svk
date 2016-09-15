'use strict';
app.factory('doodleService', function ($http) {

    return {
        getMatchDoodles: function (page) {
            return $http.get('/api/v1/matchDoodles?page=' + page + '&size=10');
        },

        getMatchDoodle: function (doodleId) {
            return $http.get('/api/v1/matchDoodles/' + doodleId);
        },

        changePresence: function (accountId, doodleId) {
            if (accountId && doodleId) {
                return $http.put('/api/v1/doodle/' + doodleId + '/presence/' + accountId);
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

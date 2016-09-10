'use strict';
app.factory('doodleService', function ($http) {

    return {
        getMatchDoodles: function (page) {
            return $http.get('/api/v1/matchDoodles/page=' + page + '&size=10');
        },

        getMatchDoodle: function (doodleId) {
            return $http.get('/api/v1/matchDoodles/' + doodleId);
        },

        changePresence: function (accountId, doodleId) {
            if (accountId && doodleId) {
                return $http({
                    url: '/api/v1/doodle/' + doodleId + '/presence/' + accountId,
                    method: "PUT"
                });
            }
        }
    }
});

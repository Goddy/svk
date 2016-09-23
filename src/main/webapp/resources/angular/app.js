'use strict';

var app = angular.module('soccerApp', ['ngCookies', 'ngSanitize']);

app.directive('focusFunction', function () {
    'use strict';
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            scope[attr.focusFunction] = function () {
                element[0].focus();
            };
        }
    };
});
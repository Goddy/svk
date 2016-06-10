<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>

<div ng-app="pollApp">
    <div ng-controller="PollCtrl">
        <p> Click <a ng-click="loadPeople()">here</a> to load data.</p>
        <table>
            <tr>
                <th>Id</th>
                <th>First Name</th>
                <th>Last Name</th>
            </tr>
            <tr ng-repeat="person in people">
                <td>{{person.id}}</td>
                <td>{{person.firstName}}</td>
                <td>{{person.lastName}}</td>
            </tr>
        </table>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>

<script type="text/javascript">
    (function ($, angular) {
        var mockDataForThisTest = "json=" + encodeURI(JSON.stringify([
                    {
                        id: 1,
                        firstName: "Peter",
                        lastName: "Jhons"
                    },
                    {
                        id: 2,
                        firstName: "David",
                        lastName: "Bowie"
                    }
                ]));


        var app = angular.module('polApp', []);

        function PollCtrl($scope, $http) {

            $scope.people = [];

            $scope.loadPeople = function () {
                var httpRequest = $http({
                    method: 'POST',
                    url: '/echo/json/',
                    data: mockDataForThisTest

                }).success(function (data, status) {
                    $scope.people = data;
                });

            };

        }
    })(jQuery, angular);


</script>
<%@ include file="../jspf/footer.jspf" %>
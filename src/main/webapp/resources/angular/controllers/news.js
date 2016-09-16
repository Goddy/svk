'use strict';
app.controller('newsCtrl', function ($scope, $http, $sce, newsService) {
    var getNews = function (page) {
        $scope.loading = true;
        return newsService.getNews(page).success(function (data) {
            setVars(page, data);
        }).finally(function () {
            $scope.loading = false;
        });
    };

    var searchNews = function (term, page) {
        $scope.loading = true;
        return newsService.searchNews(term, page).success(function (data) {
            setVars(page, data);
        }).finally(function () {
            $scope.loading = false;
        });
    };

    var setVars = function (page, data) {
        $scope.loading = false;
        $scope.page = data;
        $scope.currentPage = page;
        $scope.hasMatchDoodles = data.list.length;
    };

    $scope.changeComment = function (news, comment) {
        if (comment.editable) {
            $scope.loading = true;
            newsService.editComment(news.id, comment)
                .success(function () {
                    doodleService.getMatchDoodle(matchId).success(function (data) {
                        matchDoodle.doodle = data.doodle;
                        $scope.loading = false;
                    }).$error(function (data) {
                        $scope.loading = false;
                    });

                    console.log('changed doodle succesfully');
                }).error(function (data, status, headers, config) {
                $scope.loading = false;
                messageService.showMessage(function (message) {
                    $scope.doodleResultMessage[matchId] = message;
                }, 'alert.vote.fail');
                console.log('vote failed');
            });
        }
    };

    $scope.getPage = function (page) {
        getNews(page)
    };

    $scope.init = function () {
        getNews(0);
    };

});

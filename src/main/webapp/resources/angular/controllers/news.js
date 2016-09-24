'use strict';
app.controller('newsCtrl', function ($scope, $http, $sce, newsService) {
    var getNews = function (page) {
        $scope.loading = true;
        return newsService.getPagedNews(page).success(function (data) {
            setVars(page, data);
        }).finally(function () {
            $scope.loading = false;
        });
    };

    var getSingleNewsItem = function (news) {
        newsService.getNews(news.id).success(function (data) {
            news.comments = data.comments;
            $scope.loading = false;
        }).error(function () {
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
                    getSingleNewsItem(news);
                    console.log('changed comment succesfully');
                }).error(function (data, status, headers, config) {
                $scope.loading = false;
                console.log('Change failed');
            });
        }
    };

    $scope.postComment = function (news, comment) {
        $scope.loading = true;
        newsService.postComment(news.id, comment)
            .success(function () {
                getSingleNewsItem(news);
                console.log('Posted comment succesfully');
            }).error(function (data, status, headers, config) {
            $scope.loading = false;
            console.log('Post failed');
        });
    };

    $scope.deleteComment = function (news, comment) {
        if (comment.editable) {
            $scope.loading = true;
            newsService.deleteComment(news.id, comment.id)
                .success(function () {
                    getSingleNewsItem(news);
                    console.log('Deleted comment succesfully');
                }).error(function (data, status, headers, config) {
                $scope.loading = false;
                console.log('Delete failed');
            });
        }
    };

    $scope.deleteNews = function (news, index) {
        if (news.editable) {
            $scope.loading = true;
            newsService.deleteNews(news.id)
                .success(function () {
                    if (index !== undefined) {
                        $scope.page.list.splice(index, 1);
                    }
                    console.log('Deleted news succesfully');
                }).error(function (data, status, headers, config) {
                $scope.loading = false;
                console.log('Delete failed');
            });
        }
    };

    $scope.searchNews = function (term, page) {
        if (term !== '' && term !== undefined) {
            searchNews(term, page);
        }
        else {
            getNews(0);
        }
    };

    $scope.getPage = function (page, term) {
        if (term !== '' && term !== undefined) {
            searchNews(term, page);
        }
        else {
            getNews(page);
        }
    };

    $scope.init = function (newsId) {
        getNews(0);
    };

    $scope.initSingle = function (newsId) {
        newsService.getNews(newsId).success(function (data) {
            $scope.newsItem = data;
            $scope.loading = false;
        }).error(function () {
            $scope.loading = false;
        });
    };
});

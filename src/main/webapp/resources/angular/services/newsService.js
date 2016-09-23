'use strict';
app.factory('newsService', function ($http) {

    return {
        getPagedNews: function (page) {
            return $http.get('/api/v1/news?page=' + page + '&size=10');
        },

        getNews: function (newsId) {
            return $http.get('/api/v1/news/' + newsId);
        },

        deleteNews: function (newsId) {
            return $http.delete('/api/v1/news/' + newsId);
        },

        searchNews: function (term, page) {
            return $http.get('/api/v1/news/search/' + term + '?page=' + page + '&size=10');
        },

        postComment: function (newsId, comment) {
            return $http.post('/api/v1/news/' + newsId + '/comment', comment);
        },

        editComment: function (newsId, comment) {
            var json = JSON.stringify(comment);
            return $http.put('/api/v1/news/' + newsId + '/comment', comment);
        },

        deleteComment: function (newsId, commentId) {
            return $http.delete('/api/v1/news/' + newsId + '/comment/' + commentId);
        }
    }
});

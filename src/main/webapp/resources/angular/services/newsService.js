'use strict';
app.factory('newsService', function ($http) {

    return {
        getPagedNews: function (page) {
            return $http.get('/api/v1/news?page=' + page + '&size=10');
        },

        getNews: function (newsId) {
            return $http.get('/api/v1/news/' + newsId);
        },

        searchNews: function (term, page) {
            return $http.get('/api/v1/news/search/' + term + '?page=' + page + '&size=10');
        },

        postComment: function (newsId, comment) {
            return $http({
                url: '/api/v1/news/' + newsId + '/comment',
                method: "POST",
                data: {content: comment}
            });
        },

        editComment: function (newsId, comment) {
            return $http({
                url: '/api/v1/news/comment/' + comment.id,
                method: "PUT",
                data: {content: comment.content}
            });
        },

        deleteComment: function (newsId, commentId) {
            return $http.delete('/api/v1/news/' + newsId + '/comment/' + commentId);
        }
    }
});

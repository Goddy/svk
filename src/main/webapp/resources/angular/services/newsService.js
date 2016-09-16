'use strict';
app.factory('newsService', function ($http) {

    return {
        getNews: function (page) {
            return $http.get('/api/v1/news?page=' + page + '&size=10');
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

        deleteComment: function (commentId) {
            return $http.delete('/api/v1/news/comment/' + commentId);
        }
    }
});

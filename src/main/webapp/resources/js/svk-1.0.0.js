/**
 * Created by u0090265 on 5/11/14.
 */

var svk = svk || {};
svk.utils = {
    postOrGet : function(type, url, data, func) {
        $.ajax({
            type: type,
            url: url,
            data:  data,
            success: func,
            error: function(xhr, status, error) {
                console.log("status: " + status + ", error: " + error);
            },
            dataType: "text"
        });
    },
    jsonGet : function(url, data, func) {
        this.postOrGet("GET", url, data, func);
    },
    jsonPost : function(url, data, func) {
        console.log("Executed jsonPost");
        this.postOrGet("POST", url, data, func);
    },
    setKeyupAndBlur: function (element, func) {
        element.on({
            keyup: function () {
                func();
            },
            blur:  function () {
                func();
            }
        });
    },
    toggleDiv: function (element, e) {
        e.preventDefault();
        var div = element.attr("href");
        $('#' + div).toggle();
    },

    toggleAndFocus: function (id) {
        $(id).show();
        $(id).focus();
    }
};

svk.deleteDialogs = (function($){
    function setModal(dialog, message, title, f) {
        $("#delete-modal-title").html(title);
        $("#delete-modal-body").html(message);
        $("#delete-modal-btn").click(function (e) {
            f(e);
            dialog.modal('hide');
        });
        dialog.modal();
    }

    return {
        showDeleteDialog : function(dialog, message, title, href) {
            setModal(dialog, message, title, function () {
                window.location.href = href;
            });
        },
        showJsonDeleteDialog: function (dialog, message, title, jsonF) {
            setModal(dialog, message, title, jsonF);
        }
    }

})(jQuery);

svk.mapDialog = (function($){
    return {
        showMapDialog : function(dialog, link) {
            $("#mapFrame").attr("src", link);
            dialog.modal();
        }
    }

})(jQuery);

svk.news = (function ($, utils, dd) {
    var deleteDialog = $("#delete-modal");
    var deleteCommentMsg;
    var deleteCommentTitle;
    var deleteNewsTitle;
    var deleteNewsMessage;
    var searchNoResults;
    var lastSearchTerm; // Keeps track of last search string to avoid redundant searches.
    var to; // timeoutId
    var loader = $('#loader');
    var searchResult = $('#searchResult');
    var defaultDiv = $('#default');

    function post(e, element) {
        console.log("Executed post");
        e.preventDefault();
        var parentDiv = '#' + element.closest(("div[id^='comments_']")).attr('id');
        var newsDiv = element.closest("div.news-div");
        var data = element.prev().val();

        utils.jsonPost(element.attr('href'), { comment: data}, function (data) {
            newsDiv.html(data);
            updateDelegates();
            utils.toggleAndFocus(parentDiv);
        });

    }

    function updateDelegates() {
        $('.addComment, .editComment').click(function (e) {
            if ($(this).prev().val() != '') {
                post(e, $(this));
            }
        });

        $('.showEditComment').click(function (e) {
            utils.toggleDiv($(this), e);
        });
        $('.commentBtn').click(function (e) {
            utils.toggleDiv($(this), e);
        });
        $('.addCommentBtn').click(function () {
            $(this).hide();
        });
        $('.deleteComment').click(function (e) {
            e.preventDefault();
            var element = $(this);
            dd.showJsonDeleteDialog(deleteDialog, deleteCommentMsg, deleteCommentTitle, function (e) {
                post(e, element);
            });
        });
        $('.delete').click(function (e) {
            e.preventDefault();
            var href = $(this).attr("href");
            dd.showDeleteDialog(deleteDialog, deleteNewsMessage, deleteNewsTitle, href)
        });


    }

    function isSearchChanged(search) {
        return lastSearchTerm != search;
    }

    function doSearch(search) {
        //console.log("doSearch " + search);
        loader.show();
        defaultDiv.hide();

        $.ajax({
            url: '/news/getNewsSearch.json',
            data: {search: search},
            dataType: 'json',
            cache: false,
            success: function (json) {
                var divContent = "";
                if (json != null) {
                    if (json.length > 0) {
                        $.each(json, function (i, newsItem) {
                            var content = newsItem.content;

                            divContent += '<div class="panel panel-info">' +
                                '<div class="panel-heading"><a href="/news/newsItem.html?newsId=' + newsItem.id + '"\">' + newsItem.header + '</a></div>' +
                                '<div class="panel-body">' +
                                '<p align="left">' + content + ' </p>' +
                                '</div></div>';
                        });


                    } else {
                        if (search.length > 0) {
                            divContent = searchNoResults;
                        }
                    }
                } else {
                    divContent = searchNoResults;
                }
                searchResult.html(divContent);
                searchResult.show();
                loader.hide();
            }

        });
    }

    // Delays searching for an amount of time.
    function delayedSearch(name) {
        //console.log("delayedSearch " + name);
        to = setTimeout(function () {
            doSearch(name);
        }, 500);
    }

    // Cancels the delayed search.
    function cancelSearch() {
        //console.log("cancelSearch");
        clearTimeout(to);
    }

    function initSearchButtons() {
        $("#search").keyup(function () {
            var filter = $(this).val();
            if (filter) {
                if (isSearchChanged(filter)) {
                    cancelSearch();
                    delayedSearch(filter);
                }
            }
            else {
                defaultDiv.show();
                searchResult.hide();
            }
        });
    }

    return {
        initCommentFunctions: function (deleteDialogVars, deleteNewsDialogVars) {
            deleteCommentTitle = deleteDialogVars[0];
            deleteCommentMsg = deleteDialogVars[1];
            deleteNewsTitle = deleteNewsDialogVars[0];
            deleteNewsMessage = deleteNewsDialogVars[1];

            return {
                updateButtons: function () {
                    updateDelegates();
                }
            }
        },
        initSearchFunctions: function (messages) {
            searchNoResults = messages[0];
            initSearchButtons();
        }
    }
})(jQuery, svk.utils, svk.deleteDialogs);

svk.updatePassword = (function($){
    function checkPassword(newPwd, repeatPwd) {
        return newPwd.val() === repeatPwd.val();
    }

    function getFormElement(form, name) {
        return form.find(":input[name='" + name + "']");
    }

    function sendPassword(form, msg, resultDiv) {
        $.ajax({
            type: "POST",
            url: 'changePassword.json',
            data: form.serialize(),
            cache: false,
            dataType: "text",
            success: function(data) {
                showSuccessBox(resultDiv, data);
            },
            error: function(err) {
                console.log(err);
                showErrorBox(resultDiv, msg[1]);
            }
        });
    }

    function showSuccessBox(div, txt) {
        $(div).removeAttr('class');
        div.html(txt);
        $(div).addClass('alert alert-success');
        div.show();
    }

    function showErrorBox(div, txt) {
        div.removeAttr('class');
        div.html(txt);
        div.addClass('alert alert-danger');
        div.show();
    }

    return {
        changePassword: function(form, msg, resultDiv) {
            resultDiv.hide();
            var newPassword = getFormElement(form, "newPassword");
            var confirmPassword = getFormElement(form, "confirmPassword");

            if (!checkPassword(newPassword, confirmPassword)) {
                showErrorBox(resultDiv, msg[0]);
            }
            else {
                sendPassword(form, msg, resultDiv);
            }
        }
    }
})(jQuery);
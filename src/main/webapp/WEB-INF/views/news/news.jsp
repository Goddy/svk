<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<sec:authentication var="principal" property="principal" />
<div class="row">
    <div class="col-md-4">
    </div>
    <div class="col-md-4">
    </div>
    <div class="col-md-4">
        <div class="input-group">
            <span class="input-group-addon">
  				<span class="glyphicon glyphicon-search blue"></span>
  			</span>
            <input id="search" type="text" class="form-control" placeholder="<spring:message code="label.search" />">
        </div>
    </div>
</div>
<h1><p><spring:message code="text.news" /></h1>
<div id="default">
    <c:choose>
        <c:when test="${empty newsList}"><p><spring:message code="text.nomessages"/></p></c:when>
        <c:otherwise>
            <c:forEach var="newsItem" items="${newsList}">
            <div class="news-div">
                <tag:news newsItem="${newsItem}"/>
                <tag:comment newsItem="${newsItem}"/>
            </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <div class="text-center">
        <ul class="pagination blue">
            <li><a href="<c:out value="${first}" />">&laquo;&nbsp;<spring:message code="text.first"/></a></li>
            <li><a href="<c:out value="${previous}" />"><spring:message code="text.previous"/></a></li>
            <li><a href="<c:out value="${next}" />"><spring:message code="text.next"/></a></li>
            <li><a href="<c:out value="${last}" />"><spring:message code="text.last"/>&nbsp;&raquo;</a></li>
        </ul>
    </div>
</div>
<div id="loader" class="text-center"></div>
<div id="searchResult" style="display: none;">
</div>
<%@ include file="../jspf/footer.jspf" %>
<tag:deleteDialog dialogId="delete-modal"/>

<script src="<c:url value='/resources/js/svk-1.0.0.js'/>"></script>

<script type="text/javascript">

    (function ($, utils, dd) {

        var lastSearchTerm; // Keeps track of last search string to avoid redundant searches.
    var to; // timeoutId
    var loader = $('#loader');
    var searchResult = $('#searchResult');
    var defaultDiv = $('#default');
        var deleteDialog = $("#delete-modal");
        var activeDiv = "#comments_${id}";

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
                var message = "";
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
                            divContent = "<spring:message code='text.noResults'/>";
                        }
                    }
                } else {
                    divContent = "<spring:message code='text.noResults'/>";
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

    $(document).ready(function () {
        updateDelegates();
        toggleAndFocusOnParent(activeDiv);

        function toggleDiv(element, e) {
            e.preventDefault();
            var div = element.attr("href");
            $('#' + div).toggle();
        }

        function toggleAndFocusOnParent(id) {
            $(id).show();
            $(id).focus();
        }

        function post(e, element) {
            e.preventDefault();
            var parentDiv = '#' + element.closest(("div[id^='comments_']")).attr('id');
            var newsDiv = element.closest("div.news-div");
            var data = element.prev().val();
            utils.jsonPost(element.attr('href'), { comment: data}, function (data) {
                newsDiv.html(data);
                updateDelegates();
                toggleAndFocusOnParent(parentDiv);
            });
        }

        function updateDelegates() {
            $('.addComment, .editComment').click(function (e) {
                post(e, $(this));
            });

            $('.showEditComment').click(function (e) {
                toggleDiv($(this), e);
            });
            $('.commentBtn').click(function(e) {
                toggleDiv($(this), e);
            });
            $('.addCommentBtn').click(function () {
                $(this).hide();
            });
            $('.deleteComment').click(function (e) {
                e.preventDefault();
                var element = $(this);
                var msg = "<spring:message code="text.delete.comment"/>";
                var title = "<spring:message code="text.delete.comment.title"/>";
                dd.showJsonDeleteDialog(deleteDialog, msg, title, function (e) {
                    post(e, element);
                });
            });
        }

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
    });
    })(jQuery, svk.utils, svk.deleteDialogs);

</script>


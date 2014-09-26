<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>
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
                <div class="panel panel-info">
                    <div class="panel-heading"><c:out value="${newsItem.header}"/></div>
                    <div class="panel-body">
                        <p align="left">${newsItem.content}</p>

                        <div style="text-align: right"><spring:message code="text.postedby"/> <c:out
                                value="${newsItem.account.getFullName()}"/> <spring:message code="text.on"/>
                            <fmt:formatDate value="${newsItem.postDate}"/>
                            <sec:authorize access="hasRole('ADMIN')">
                            <div class="btn-group">
                                <a href="/news/editNews.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top" class="btn btn-default glyphicon glyphicon-edit edit"><span class=""></span></a>
                                <a href="/news/deleteItem.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top" class="btn btn-default glyphicon glyphicon-trash delete"><span class="delete"></span></a>
                            </div>
                            </sec:authorize>
                            <sec:authorize access="hasRole('USER')">
                                <c:if test="${principal.username == newsItem.account.username}">
                                <div class="btn-group">
                                    <a href="/news/editNews.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top" class="btn btn-default glyphicon glyphicon-edit edit"><span class=""></span></a>
                                    <a href="/news/deleteItem.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top" class="btn btn-default glyphicon glyphicon-trash delete"><span class="delete"></span></a>
                                </div>
                                </c:if>
                            </sec:authorize>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${not single}"></c:when>
        <c:otherwise>

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

<script type="text/javascript">

    var lastSearchTerm; // Keeps track of last search string to avoid redundant searches.
    var to; // timeoutId
    var loader = $('#loader');
    var searchResult = $('#searchResult');
    var defaultDiv = $('#default');

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

</script>


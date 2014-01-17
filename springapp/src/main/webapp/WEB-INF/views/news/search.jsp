<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../header.jsp"%>
<h1><p><spring:message code="text.news" /></h1>
<table>
    <tr>
        <form action="search" method="post" name="search_form">
        <td><spring:message code="label.search" /></td>
        <td><input id="search" name="search" type="text" value="${searchValue}"/></td>
        <td><img id="loader" src="<c:url value='/resources/images/ajax-loader.gif' />" height="12" width="12" style="display: none;"></td>
        </form>
    </tr>
</table>
<table id="searchTable" class="border" style="display: none;"></table>
<%@ include file="../footer.jsp"%>

<script type="text/javascript">

    var lastSearchTerm; // Keeps track of last search string to avoid redundant searches.
    var to; // timeoutId

    function isSearchChanged(search) {
         return lastSearchTerm != search;

    }

    function doSearch(search) {
        //console.log("doSearch " + search);

        var loader = $('#loader');
        loader.show();

        $.ajax({
            url: 'getNewsSearch.json',
            data: {search: search},
            dataType: 'json',
            cache: false,
            success: function(json) {
                var tableContent = "";
                var message = "";
                if (json != null) {
                    if (json.length > 0) {
                        $.each(json, function(i, newsItem) {
                            var content = newsItem.content.substring(0,100) + " ..."
                            tableContent += "<tr><td>"
                                + "<h2/>< a href=\news\news.html?newsItem=" + newsItem.id +"</a></h2></td></tr>"
                                + "<tr><td>" + newsItem. + " </td></tr>"
                                + "<td>" + account.givenName + " </td>"
                                + "</tr>";
                        });

                        $("#searchTable tbody").html(tableContent);
                        $("#searchTable").show();
                    } else {
                        if (search && search.length > 2) {
                            message = "<spring:message code='text.noResults'/>";
                        }
                        $("#searchTable").hide();
                    }
                    $("#message").html(message);
                } else {
                    $("#searchTable").hide();
                }
                loader.hide();
            }

        });
    }

    // Delays searching for an amount of time.
    function delayedSearch(name) {
        //console.log("delayedSearch " + name);
        to = setTimeout(function() {
            doSearch(name);
        }, 500);
    }

    // Cancels the delayed search.
    function cancelSearch() {
        //console.log("cancelSearch");
        clearTimeout(to);
    }

    $(document).ready(function() {


        $("#search").live("keyup", function() {
            var filter = $(this).val();
            if (isSearchChanged(filter)) {
                cancelSearch();
                delayedSearch(filter);
            }
        });

        // When doing page load (or going back), check content of search input field
        // and do a search.
        $("#searchTable tbody tr").remove();
        var search = $("#search").val();
        if (search) {
            doSearch(search);
        }
    });

</script>

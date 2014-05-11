<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../jspf/header.jspf" %>
<div id="matches"></div>
<%@ include file="../jspf/footer.jspf" %>
<script type="javascript">
    $(document).ready(function () {
        function getMatches() {
            $.ajax({
                url: '/matches/matchesPerSeason.json',
                dataType: 'json',
                cache: false,
                success: function (json) {
                    var divContent = "";
                    var message = "";
                    if (json != null) {
                        if (json.length > 0) {
                            $.each(json, function (i, newsItem) {
                                var content = newsItem.content.substring(0, 100) + " ..."

                                divContent += '<div class="panel panel-info">' +
                                        '<div class="panel-heading"><a href=\"\\news\\news.html?newsItem=" + newsItem.id +"\">' + newsItem.header + '</a></div>' +
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
    })
</script>


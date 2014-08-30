<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>
<div class="alert alert-info"><spring:message code="info.matches"/></div>

<div class="panel-group" id="accordion">
    <c:forEach items="${seasons}" var="season">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-target="#collapse${season.id}"
                       href="#collapse${season.id}" class="collapsed" id="${season.id}">
                        <spring:message code="text.season"/> ${season.description}
                    </a>
                </h4>
            </div>
            <div id="collapse${season.id}" class="panel-collapse collapse">
                <div class="panel-body">
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<%@ include file="../jspf/footer.jspf" %>
<script type="text/javascript">


    $(document).ready(function () {
        function populateFirstDiv() {
            var firstDiv = $("div.panel-group:first");
            var seasonId = firstDiv.find("a.collapsed").attr('id');
            var resultDiv = firstDiv.find(".panel-collapse:first");
            resultDiv.collapse();
            getMatches(seasonId, resultDiv);

        }

        $(".collapsed").click(function () {
            var seasonId = $(this).attr('id');
            var resultDiv = $('#collapse' + seasonId);
            getMatches(seasonId, resultDiv);

        });

        function getMatches(season, div) {
            if (div.find('.table:first').length === 0) {
                $.ajax({
                    url: '/matches/matchesForSeason.json',
                    data: {seasonId: season},
                    dataType: 'json',
                    cache: true,
                    success: function (json) {
                        var divContent = "";
                        var message = "";
                        if (json != null) {
                            divContent += '<table class="table table-hover">'
                                    + '<tr><td><spring:message code='text.date'/></td>'
                                    + '<td><spring:message code='text.homeTeam'/></td>'
                                    + '<td><spring:message code='text.awayTeam'/></td>'
                                    + '<td><spring:message code='text.result'/></td>'
                                    + '<td><spring:message code='text.actions'/></td></tr>';

                            $.each(json, function (i, o) {

                                divContent +=
                                        '<tr><td>' + o.object.date.dayOfMonth + '/' + o.object.date.monthOfYear + '/' + o.object.date.year  + '</td>' +
                                                '<td>' + o.object.homeTeam.name + '</td>' +
                                                '<td>' + o.object.awayTeam.name + '</td>' +
                                                '<td>' + o.object.htGoals + ' - ' + o.object.atGoals + '</td>' +
                                                '<td>' + o.htmlActions + ' </td></tr>';
                            });
                            divContent += '</table>';

                        } else {
                            divContent = "<spring:message code='text.noMatches'/>";
                        }
                        div.html(divContent);
                    }

                });
            }

        }

        //Populate the first div with matches
        populateFirstDiv();
    })
</script>


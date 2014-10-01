<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>
<div class="alert alert-info"><spring:message code="info.matches"/></div>
<%@ include file="../jspf/resultMessage.jspf" %>
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
<tag:deleteDialog dialogId="delete-match-modal"/>
<tag:mapDialog dialogId="map-modal"/>
<script src="<c:url value='/resources/js/svk-1.0.0.js'/>"></script>
<script type="text/javascript">

    (function($, dd, md, utils){
        var deleteMsg = "<spring:message code="text.delete.match"/>";
        var deleteTitle = "<spring:message code="title.delete.match"/>";
        var deleteMatchModal = $("#delete-match-modal");
        var mapModal = $("#map-modal");
        var loggedIn = "${loggedIn}";

        $(document).ready(function() {
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
                                divContent += '<div class="table-responsive"><table class="table table-hover">'
                                        + '<tr>' + '<td style="display:none"></td>'
                                        + '<td><spring:message code='text.date'/></td>'
                                        + '<td><spring:message code='text.hour'/></td>'
                                        + '<td><spring:message code='text.homeTeam'/></td>'
                                        + '<td><spring:message code='text.awayTeam'/></td>'
                                        + '<td><spring:message code='text.result'/></td>';

                                if (loggedIn == 'true') {
                                    divContent += '<td><spring:message code='text.presence'/></td>';
                                }

                                divContent += '<td><spring:message code='text.actions'/></td></tr>';

                                $.each(json, function (i, o) {
                                    var result = o.object.played ? '<td>' + o.object.htGoals + ' - ' + o.object.atGoals + '</td>' : '<td><spring:message code='text.notYetPlayed'/></td>';
                                    var minute = o.object.date.minuteOfHour < 10 ? "0" + o.object.date.minuteOfHour :  o.object.date.minuteOfHour;
                                    var doodle = loggedIn == "true"? '<td><div id="presenceActions"> ' + o.additions['presenceActions'] + '<div></td>' : "";
                                    divContent +=
                                            '<tr><td>' + o.object.date.dayOfMonth + '/' + o.object.date.monthOfYear + '/' + o.object.date.year  + '</td>' +
                                                    '<td>' + o.object.date.hourOfDay + ':' + minute + '</td>' +
                                                    '<td>' + o.object.homeTeam.name + '</td>' +
                                                    '<td>' + o.object.awayTeam.name + '</td>' +
                                                    result +
                                                    doodle +
                                                    '<td>' + o.additions['htmlActions'] + ' </td></tr>';
                                });
                                divContent += '</table></div>';

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

            $(document).on('click', 'a[class*="delete"]', function (e) {
                console.log("Clicked delete");
                e.preventDefault();
                var hTeam = $(this).parents('tr:first').find('td:nth-child(3)').text();
                var aTeam = $(this).parents('tr:first').find('td:nth-child(4)').text();
                var href = $(this).attr("href");
                var msg = hTeam + " - " + aTeam;
                dd.showDeleteDialog(deleteMatchModal, msg, deleteTitle, href)
            });

            $(document).on('click', 'a[class*="map"]', function (e) {
                e.preventDefault();
                var href = $(this).attr("href");
                md.showMapDialog(mapModal, href)
            });

            $(document).on('click', 'a[class*="presence"]', function (e) {
                e.preventDefault();
                if (!($(this).parent().next().attr("class") == "tableBtn") ) {
                    alterPresence($(this));
                }
            });

            $(document).on('click', '#submitPresence', function (e) {
                e.preventDefault();
                var presenceActions = $(this).parent();
                var checkBox = $(this).parent().closest().find('[type=checkbox]');
                utils.postForm($(this).attr("href"), { present : checkBox.checked }, function (data) {
                    presenceActions.html(data);
                    presenceActions.next().remove();
                });

            });

            function alterPresence(element) {
                element.hide();
                element.next().show();
                element.parent().after('<div class="tableBtn"><button id="submitPresence" class="btn btn-primary btn-xs"><spring:message code="button.update"/></button></div>')
            }
        });

    })(jQuery, svk.deleteDialogs, svk.mapDialog, svk.utils);


</script>




<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>

<div class="alert alert-info"><spring:message code="info.matches"/></div>

<c:if test="${not empty nextMatch}">
    <joda:format value="${nextMatch.date}" var="nextMatchDate" pattern="dd-MM-yyyy HH:mm"/>
    <div class="panel panel-danger">
    <div class="panel-heading">
        <h3 class="panel-title"><spring:message code="text.next.match"/><c:if test="${nextMatch.status == 'CANCELLED'}">
            <b>(<spring:message code='label.match.status.CANCELLED'/>!)</b>
        </c:if></h3>
    </div>
    <div class="panel-body">
        <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
        <c:out value="${nextMatchDate}"/><br>
        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
        <c:out value="${nextMatch.description}"/><br>
        <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
        <c:choose>
            <c:when test="${not empty nextMatch.homeTeam.address.googleLink}">
                <a href="${nextMatch.homeTeam.address.googleLink}" class="map"><c:out
                        value="${nextMatch.homeTeam.address}"/></a>
            </c:when>
            <c:otherwise>
                <c:out value="${nextMatch.homeTeam.address}"/>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</c:if>

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
                    <a class="pull-right downloadCalendar"
                       href="/calendar/getMatchesCalendar.html?seasonId=${season.id}"><span
                            class="glyphicon glyphicon-calendar"><spring:message code="text.export"/></span></a>
                </h4>
            </div>
            <div id="collapse${season.id}" class="panel-collapse collapse">
                <div class="panel-body">
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<script src="<c:url value='/resources/js/svk-ui-1.4.js'/>"></script>

<tag:deleteDialog dialogId="delete-match-modal"/>
<tag:calendarDialog dialogId="download-calendar-modal"/>
<tag:mapDialog dialogId="map-modal"/>
<script type="text/javascript">

    (function ($, dd, md, dc, utils) {
        var deleteMsg = "<spring:message code="text.delete.match"/>";
        var deleteTitle = "<spring:message code="title.delete.match"/>";
        var deleteMatchModal = $("#delete-match-modal");
        var mapModal = $("#map-modal");
        var downloadCalendarModal = $("#download-calendar-modal");
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
                        url: '/matchesForSeason.json',
                        data: {seasonId: season},
                        dataType: 'json',
                        cache: true,
                        success: function (json) {
                            var divContent = "";
                            var message = "";
                            //Disabled
                            var loggedIn = "false";
                            var count = 0;
                            if (json != null) {
                                divContent += '<div class="table-responsive"><table class="table table-hover rwd-table">'
                                        + '<th><spring:message code='text.date'/></th>'
                                        + '<th><spring:message code='text.match'/></th>'
                                        + '<th><spring:message code='text.result'/></th>';

                                if (loggedIn == 'true') {
                                    divContent += '<td><spring:message code='text.presence'/></td>';
                                }


                                divContent += '<th><spring:message code='text.actions'/></th></tr>';

                                $.each(json, function (i, o) {
                                    var result = "";

                                    switch (o.object.status) {
                                        case "PLAYED":
                                            result = '<td>' + o.object.htGoals + ' - ' + o.object.atGoals + '</td>';
                                            break;
                                        case "NOT_PLAYED":
                                            result = '<td><spring:message code='label.match.status.NOT_PLAYED'/></td>';
                                            break;
                                        case "CANCELLED":
                                            result = '<td title="' + $('<div/>').text(o.object.statusText).html() + '" data-toggle="tooltip" data-placement="top"><b><spring:message code='label.match.status.CANCELLED'/></b></td>';
                                            break;
                                    }
                                    var doodle = loggedIn == "true"? '<td><div id="presenceActions"> ' + o.additions['presenceActions'] + '<div></td>' : "";
                                    //Fix for dynamic odd rows
                                    var trClass = count % 2 !== 0 ? '' : 'class="odd"';
                                    count++;

                                    divContent +=
                                            '<tr ' + trClass + '>' +
                                            '<td data-th="<spring:message code='text.date'/>">' + o.object.stringDate + ' : ' + o.object.stringHour + '</td>' +
                                            '<td data-th="<spring:message code='text.match'/>">' + o.object.homeTeam.name + ' - ' + o.object.awayTeam.name + '</td>' +
                                            result +
                                            doodle +
                                            '<td>' + o.additions['htmlActions'] + ' </td></tr>';

                                    divContent += '<tr style="display: none" class="active" id="details' + o.object.id + '"><td colspan="5">';
                                    divContent += '<div><spring:message code='text.goals'/>:<br/><ul>';
                                     for (i = 0; i < o.object.goals.length; i++) {
                                         var scorer = !o.object.goals[i].scorer ? "<spring:message code="text.no.player"/>" : o.object.goals[i].scorer.fullName;
                                         var assist = !o.object.goals[i].assist ? "" : " (" + o.object.goals[i].assist.fullName + ")";
                                         divContent += "<li>";
                                         divContent += scorer;
                                         divContent += assist;
                                         divContent += "</li>";
                                    }
                                     divContent += "</ul>";
                                    divContent += '</td></div></tr>';
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

            $(document).on('click', 'a[class*="downloadCalendar"]', function (e) {
                e.preventDefault();
                var href = $(this).attr("href");
                dc.showCalendarDialog(downloadCalendarModal, href)
            });

            $(document).on('click', 'a[class*="details"]', function (e) {
                e.preventDefault();
                var href = $(this).attr("href");
                $('#' + href).toggle();
            });

            $(document).on('click', 'a[class*="presence"]', function (e) {
                e.preventDefault();
                var parent = $(this).parent();
                utils.jsonGet($(this).attr("href") , {} , function (data) {
                    parent.html(data);
                });
            });
        });

    })(jQuery, svk.deleteDialogs, svk.mapDialog, svk.calendarDialog, svk.utils);


</script>
<%@ include file="../jspf/footer.jspf" %>




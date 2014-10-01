<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>
<div class="alert alert-info"><spring:message code="info.teams"/></div>
<%@ include file="../jspf/resultMessage.jspf" %>
<div class="panel-group" id="accordion">
    <div class="table-responsive">
    <table class="table table-hover">
        <tr>
            <th style="display:none;"></th>
            <th><spring:message code="label.teamName"/> </th>
            <th><spring:message code="label.address"/> </th>
            <th><spring:message code="text.actions"/> </th>
        </tr>
    <c:forEach items="${teams}" var="team">
        <tr>
            <td style="display:none;">${team.object.address.googleLink}</td>
            <td>${team.object.name}</td>
            <td>${team.object.address}</td>
            <td>${team.additions['htmlActions']}</td>
        </tr>
    </c:forEach>
    </table>
    </div>
</div>
<%@ include file="../jspf/footer.jspf" %>
<tag:deleteDialog dialogId="delete-team-modal"/>
<tag:mapDialog dialogId="map-modal"/>

<script src="<c:url value='/resources/js/svk-1.0.0.js'/>"></script>
<script type="text/javascript">
    (function($, dd, md){
        var deleteMsg = "<spring:message code="text.delete.team"/>";
        var deleteTitle = "<spring:message code="title.delete.team"/>";
        var deleteMatchModal = $("#delete-team-modal");
        var mapModal = $("#map-modal");
        $(document).ready(function() {
            $(document).on('click', 'a[class*="delete"]', function (e) {
                e.preventDefault();
                var name = $(this).parents('tr:first').find('td:nth-child(2)').text();
                var href = $(this).attr("href");
                var msg = deleteMsg + name;
                dd.showDeleteDialog(deleteMatchModal, msg, deleteTitle, href)
            });

            $(document).on('click', 'a[class*="map"]', function (e) {
                e.preventDefault();
                var href = $(this).parents('tr:first').find('td:nth-child(1)').text();
                md.showMapDialog(mapModal, href)
            });
        });
    })(jQuery, svk.deleteDialogs, svk.mapDialog);
</script>



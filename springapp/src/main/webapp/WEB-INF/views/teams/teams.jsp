<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/header.jspf" %>

<%@ include file="../jspf/resultMessage.jspf" %>
<div class="panel-group" id="accordion">
    <table class="table table-hover">
        <tr>
            <th><spring:message code="label.id"/> </th>
            <th><spring:message code="label.teamName"/> </th>
            <th><spring:message code="label.address"/> </th>
            <th><spring:message code="text.actions"/> </th>
        </tr>
    <c:forEach items="${teams}" var="team">
        <tr>
            <td>${team.object.id}</td>
            <td>${team.object.name}</td>
            <td>${team.object.address}</td>
            <td>${team.htmlActions}</td>
        </tr>
    </c:forEach>
    </table>
</div>
<%@ include file="../jspf/footer.jspf" %>
<tag:deleteDialog dialogId="delete-match-modal"/>
<script src="<c:url value='/resources/js/svk-1.0.0.js'/>"></script>
<script type="text/javascript">
    (function($, dd){
        var deleteMsg = "<spring:message code="text.delete.team"/>";
        var deleteTitle = "<spring:message code="title.delete.team"/>";
        var deleteMatchModal = $("#delete-match-modal");
        $(document).ready(function() {
            $(document).on('click', 'a[class*="delete"]', function (e) {
                console.log("Clicked delete");
                e.preventDefault();
                var name = $(this).parents('tr:first').find('td:nth-child(2)').text();
                var href = $(this).attr("href");
                var msg = deleteMsg + name;
                dd.showDeleteDialog(deleteMatchModal, msg, deleteTitle, href)
            });
        });
    })(jQuery, svk.deleteDialogs);
</script>



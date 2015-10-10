<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>

<sec:authorize access="isAuthenticated()">
    <c:set var="isAuthenticated" value="true"/>
</sec:authorize>

<form class="form-inline">
    <div class="form-group">
        <label for="season"><spring:message code="text.season"/></label>
        <select name="season" id="season" class="form-control" onchange="getSeason(this)">
            <c:forEach items="${seasons}" var="season">
                <option value="${season.id}"
                        <c:if test="${season.id == selectedSeason}">selected</c:if>>${season.description}</option>
            </c:forEach>
        </select>
    </div>
</form>


<div class="panel-group" id="accordion">
    <div class="table-responsive">
        <table class="table table-hover table-bordered" id="statisticsTable">
            <thead>
            <tr>
                <th><spring:message code="text.scorer"/></th>
                <th><spring:message code="label.goals"/></th>
                <th><spring:message code="label.assists"/></th>
                <th><spring:message code="label.presences"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${stats}" var="stat">
                <tr>
                    <c:choose>
                        <c:when test="${isAuthenticated}">
                            <td>${stat.account.toString()}</td>
                        </c:when>
                        <c:otherwise>
                            <td>${stat.account.fullName}</td>
                        </c:otherwise>
                    </c:choose>

                    <td>${stat.goals}</td>
                    <td>${stat.assists}</td>
                    <td>${stat.played}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script src="//cdn.datatables.net/1.10.9/js/jquery.dataTables.min.js"></script>
<script src="//cdn.datatables.net/1.10.9/js/dataTables.bootstrap.min.js"></script>

<script type="text/javascript">
    function getSeason(element) {
        window.location = "/statistics.html?seasonId=" + element.value;
    }

    (function ($) {
        $('#statisticsTable').DataTable({
            "paging": false,
            "filter": false,
            "bInfo": false,
            "order": [[1, "desc"]],
            "aoColumns": [
                {sWidth: '70%'},
                {sWidth: '10%'},
                {sWidth: '10%'},
                {sWidth: '10%'}]
        });

    })(jQuery);


</script>
<%@ include file="../jspf/footer.jspf" %>





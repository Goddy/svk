<%@ page pageEncoding="UTF-8" %>
<%@ include file="../jspf/header.jspf" %>

<h1><spring:message code="nav.doodle"/></h1>

<c:set value="true" var="showUsers"/>

<div class="matchDoodle">
    <%@ include file="../jspf/matchDoodle.jsp" %>
</div>

<%@ include file="../jspf/footer.jspf" %>
<script src="<c:url value='/resources/js/svk-1.0.1.js'/>"></script>
<script type="text/javascript">
    (function ($, doodle) {
        $(document).on('click', 'a[class*="presence"]', function (e) {
            e.preventDefault();
            doodle.changePresence($(this));
        });
        $(document).on('click', 'a[class*="doodle-users"]', function (e) {
            e.preventDefault();
            $(this).closest('div.panel').find('div.list').toggle();
        });

    })(jQuery, svk.doodle);
</script>

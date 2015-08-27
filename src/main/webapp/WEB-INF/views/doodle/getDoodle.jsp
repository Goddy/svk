<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="../jspf/header.jspf" %>

<h1><spring:message code="nav.doodle"/></h1>

<c:set value="true" var="showUsers"/>

<div class="matchDoodle">
    <tag:matchDoodle returnUrl="/getMembersDoodle.html?matchId=${match.id}" match="${match}" showUsers="${showUsers}"/>
</div>

<%@ include file="../jspf/footer.jspf" %>

<script type="text/javascript">
    (function ($, doodle) {
        $(document).on('click', 'a[class*="presence"]', function (e) {
            e.preventDefault();
            $(this).tooltip('hide');
            doodle.changePresence($(this));
        });
        $(document).on('click', 'a[class*="doodle-users"]', function (e) {
            e.preventDefault();
            $(this).closest('div.panel').find('div.list').toggle();
        });

    })(jQuery, svk.doodle);
</script>

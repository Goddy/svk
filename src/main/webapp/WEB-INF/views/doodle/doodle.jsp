<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="../jspf/header.jspf" %>

<h1><p><spring:message code="nav.doodle"/></h1>
<div class="alert alert-info"><spring:message code="info.doodle"/></div>
<c:if test="${empty matches}">
  <spring:message code="text.doodle.none.found"/>
</c:if>
<c:forEach var="match" items="${matches}">
  <div class="matchDoodle">
    <tag:matchDoodle returnUrl="/membersDoodle.html" match="${match}" showUsers="${showUsers}"/>
  </div>
</c:forEach>
<tag:pageComponent first="${first}" previous="${previous}" next="${next}" last="${last}"/>

<script src="<c:url value='/resources/js/svk-ui-1.3.js'/>"></script>
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
<%@ include file="../jspf/footer.jspf" %>

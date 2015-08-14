<%@ page pageEncoding="UTF-8" %>
<%@ include file="../jspf/header.jspf" %>

<h1><p><spring:message code="nav.doodle"/></h1>
<div class="alert alert-info"><spring:message code="info.doodle"/></div>
<c:if test="${empty matches}">
  <spring:message code="text.doodle.none.found"/>
</c:if>
<c:forEach var="match" items="${matches}">
  <div class="matchDoodle">
    <%@ include file="../jspf/matchDoodle.jsp" %>
  </div>
</c:forEach>
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

<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="../jspf/header.jspf" %>

<div class="col-md-12">
  <ul class="breadcrumb">
    <li><a href="/news.html"><spring:message code="nav.home"/></a>
    </li>
    <li><spring:message code="nav.doodle"/>
    </li>
  </ul>
</div>
<div class="col-md-12">
  <div class="box">
    <blockquote><spring:message code="info.doodle"/></blockquote>
  </div>
</div>
<div class="col-md-12">
<c:if test="${empty matches}">
  <spring:message code="text.doodle.none.found"/>
</c:if>
<c:forEach var="match" items="${matches}">
  <div class="matchDoodle">
    <tag:matchDoodle returnUrl="${baseUrl}/membersDoodle.html" match="${match}" showUsers="${showUsers}"/>
  </div>
</c:forEach>
<tag:pageComponent first="${first}" previous="${previous}" next="${next}" last="${last}"/>
</div>

<script src="<c:url value='/resources/js/svk-ui-1.5.js'/>"></script>
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

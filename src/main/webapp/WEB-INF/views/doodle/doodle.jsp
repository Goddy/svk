<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ include file="../jspf/header.jspf" %>

<c:forEach var="match" items="${matches}">

  <c:set var="presences" value="${match.presences}"/>

  <div class="panel panel-primary">
  <div class="panel-heading">Vrijdag 13/02/2013 20:30</div>
  <div class="panel-body">
    <div class="doodle-title">SVK - REUTEMETEUT
    </div>
      <div class="doodle-badge btn-group">
          <a class="btn btn-default glyphicon glyphicon-user doodle-user" aria-hidden="true"><span class="badge">${presences.countPresences}</span></a>
          <a href="#" data-toggle="tooltip" data-placement="top" class="btn btn-default doodle-user-btn presence glyphicon <c:if test="${presence.isPresent(currentAccount)}">glyphicon-ok green"></a>
      </div>
  </div>
  <div class="panel-body" style="display: none">
    <span class="doodle-list">Gebruikersnaam
    <a href="#" data-toggle="tooltip" data-placement="top" disabled class="btn btn-default presence glyphicon glyphicon-ok green"></a>
    </span>
  </div>
</div>
</c:forEach>
<%@ include file="../jspf/footer.jspf" %>
<script src="<c:url value='/resources/js/svk-1.0.1.js'/>"></script>

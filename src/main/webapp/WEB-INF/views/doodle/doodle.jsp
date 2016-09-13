<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
<div class="col-md-12" ng-app="soccerApp" ng-controller="matchDoodleCtrl" data-ng-init="init()">
  <div ng-if="!hasMatchDoodles">
  <spring:message code="text.doodle.none.found"/>
  </div>
  <div class="matchDoodle" ng-repeat="(key, value) in matchDoodles.list">
    <tag:matchDoodle returnUrl="${baseUrl}/membersDoodle.html" match="${match}" showUsers="${showUsers}"/>
    <div class="panel panel-default">
      <div class="panel-heading"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>&nbsp;{{value.date}}
      </div>
      <div class="panel-body">
        <div class="doodle-title">
          <h3>{{value.description}}
            <span ng-if="value.status == 'CANCELLED'">
              <b>(<spring:message code='label.match.status.CANCELLED'/>!)</b>
            </span>
          </h3>
        </div>
        <div class="doodle-badge btn-group btn-group-lg">
          <a class="btn btn-default doodle-users" data-toggle="tooltip" data-container="body"
             title="<spring:message code="title.doodlePresences"/>" aria-hidden="true"><span
                  class="glyphicon glyphicon-user"></span> <span
                  class="count-badge">{{value.doodle.total}}</span>
          </a>
          <sec:authorize access="isAuthenticated()">
            <a ng-click="changePresence(value.doodle.id, value.doodle.currentPresence)" data-toggle="tooltip"
               data-container="body" title="<spring:message code="title.doodleChange"/>"
               data-placement="top" ng-disabled="value.doodle.currentPresence.isPresent" class="btn btn-default"><span
                    class=""
                    aria-hidden="true"></span></a>
          </sec:authorize>
          <sec:authorize access="isAnonymous()">
            <a href="${href}" data-toggle="tooltip" data-container="body"
               title="<spring:message code="title.doodleChange"/>"
               data-placement="top" disabled class="btn btn-default"><span class="glyphicon glyphicon-lock grey"
                                                                           aria-hidden="true"></span></a>
          </sec:authorize>
          <c:when test="${isPresent == 'NOT_FILLED_IN'}">
            <c:set var="classes" value="glyphicon glyphicon-question-sign grey"/>
          </c:when>
          <c:when test="${isPresent == 'PRESENT'}">
            <c:set var="classes" value="glyphicon glyphicon-ok green "/>
          </c:when>
          <c:when test="${isPresent == 'ANONYMOUS'}">
            <c:set var="classes" value="glyphicon glyphicon-lock grey"/>
          </c:when>
          <c:otherwise>
            <c:set var="classes" value="glyphicon glyphicon-remove red"/>
          </c:otherwise>
          </c:choose>
          <c:set value="${isPresent == 'ANONYMOUS' ? '' : 'presence'}" var="presenceClass"/>
        </div>
      </div>
      <div class="panel-body list" style="${display}">
        <c:forEach items="${accounts}" var="a">
        <div class="doodle-list" ng-repeat="presence in matchDoodles.presences>
            {{presence.account.name}}

            <tag:doodlePresence account="${a}" isOwnAccount="${a.equals(currentAccount)}" match="${match}"
                                isAdmin="${isAdmin}" returnUrl="${returnUrl}"/>
          </div>
        </c:forEach>
      </div>
  </div>
<tag:pageComponent first="${first}" previous="${previous}" next="${next}" last="${last}"/>
</div>

<script src="<c:url value='/resources/js/svk-ui-1.5.js'/>"></script>
        <
        script;
        type = "text/javascript" >
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

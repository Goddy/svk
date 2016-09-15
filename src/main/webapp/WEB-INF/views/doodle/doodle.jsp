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
<div class="col-md-12" ng-app="soccerApp" ng-controller="doodleCtrl" data-ng-init="init()">
  <div ng-if="!hasMatchDoodles">
  <spring:message code="text.doodle.none.found"/>
  </div>
  <div class="matchDoodle" ng-repeat="(key, value) in matchDoodles.list">
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
            <a ng-click="changePresence(value, value.doodle.currentPresence)" data-toggle="tooltip"
               data-container="body" title="<spring:message code="title.doodleChange"/>"
               data-placement="top" class="btn btn-default"><span
                    ng-class="getPresenceClass(value.doodle.currentPresence)"
                    aria-hidden="true"></span></a>
          </sec:authorize>
          <sec:authorize access="isAnonymous()">
            <a href="/membersDoodle.html" data-toggle="tooltip" data-container="body"
               title="<spring:message code="title.doodleChange"/>"
               data-placement="top" class="btn btn-default"><span class="glyphicon glyphicon-lock grey"
                                                                  aria-hidden="true"></span></a>
          </sec:authorize>
        </div>
      </div>
      <div class="panel-body list">
        <div class="doodle-list" ng-repeat="presence in value.doodle.presences">
            {{presence.account.name}}
          <a ng-click="changePresence(value, presence)" data-toggle="tooltip"
             ng-disabled="!presence.isEditable"
             data-container="body" title="<spring:message code="title.doodleChange"/>"
             data-placement="top" class="btn btn-default"><span
                  ng-class="getPresenceClass(presence)"
                  aria-hidden="true"></span></a>
          </div>
      </div>
  </div>
<tag:pageComponent first="${first}" previous="${previous}" next="${next}" last="${last}"/>
</div>

  <script src="<c:url value='/resources/angular/controllers/doodles.js'/>"></script>
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

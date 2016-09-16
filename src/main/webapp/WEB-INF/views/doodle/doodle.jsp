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
  <tag:pagination/>
  <div ng-show="loading" class="ajax-loading">
    <img id="ajax-loading-image" src="<c:url value='/resources/images/pacman.gif'/>" alt="Loading..."/>
  </div>
  <div ng-show="!loading && !hasMatchDoodles">
    <div class="alert alert-warning">
      <spring:message code="text.doodles.none.found"/>
    </div>
  </div>
  <div class="matchDoodle" ng-show="hasMatchDoodles" ng-repeat="(key, value) in page.list">
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
            <a ng-click="changePresence(value, value.doodle.currentPresence, true)" data-toggle="tooltip"
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
          <a ng-click="changePresence(value, presence, presence.editable)" data-toggle="tooltip"
             ng-disabled="!presence.editable"
             data-container="body" class="btn btn-default"><span
                  ng-class="getPresenceClass(presence)"
                  aria-hidden="true"></span></a>
          </div>
      </div>
  </div>
  </div>
  <tag:pagination/>
</div>

  <script src="<c:url value='/resources/angular/controllers/doodles.js'/>"></script>
  <script type="text/javascript">
    (function ($) {
    $(document).on('click', 'a[class*="doodle-users"]', function (e) {
      e.preventDefault();
      $(this).closest('div.panel').find('div.list').toggle();
    });

    })(jQuery);
</script>
<%@ include file="../jspf/footer.jspf" %>

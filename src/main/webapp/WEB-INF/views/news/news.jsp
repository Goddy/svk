<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<sec:authentication var="principal" property="principal" />
<div class="row">
    <div class="col-md-4 col-md-offset-8">
        <div class="input-group">
            <span class="input-group-addon">
  				<span class="glyphicon glyphicon-search blue"></span>
  			</span>
            <input id="search" type="text" class="form-control" placeholder="<spring:message code="label.search" />">
        </div>
    </div>
</div>
<div class="row m-t-1" ng-app="soccerApp" ng-controller="newsCtrl" data-ng-init="init()">
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-info" ng-if="motmPoll !== null">
            <div>
                <div class="panel-heading"><h2><spring:message code="title.manOfTheMatchPoll"/></h2></div>
                <security:authorize access="isAuthenticated()">
                    <div class="panel-body" ng-if="motmPoll.status == 'OPEN'" >
                        <ul class="list-group" ng-repeat="x in motmPoll.poll.votes">
                            <li class="list-group item">
                                <div class="radio">
                                    <label>
                                        <input name="group-poll" ng-value="{{x.account}}" type="radio"  ng-model="$parent.selectedAccount">
                                        {{x.account.name}}
                                    </label>
                                </div>
                            </li>
                        </ul>
                        <a href="#" ng-click="vote(selectedAccount, motmPoll)" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-bell"></span> <spring:message code="label.vote"/></a>
                    </div>
                </security:authorize>
                <div class="panel-footer">
                    <div ng-if="motmPoll.totalVotes > 0">
                        <div ng-repeat="x in motmPoll.votes">
                            {{x.account.name}}
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="{{getPercentage(x.votes, motmPoll.totalVotes)}}"
                                     aria-valuemin="0" aria-valuemax="100" ng-style="{width : ( getPercentage(x.votes, motmPoll.totalVotes) + '%' ) }">
                                    {{getPercentage(x.votes, motmPoll.totalVotes)}}%
                                </div>
                            </div>
                        </div>
                    </div>
                    <div ng-if="motmPoll.totalVotes == 0">
                        <spring:message code="text.no.votes"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<h1><p><spring:message code="text.news" /></h1>
<div id="default">
    <c:choose>
        <c:when test="${empty newsList}"><p><spring:message code="text.nomessages"/></p></c:when>
        <c:otherwise>
            <c:forEach var="newsItem" items="${newsList}">
            <div class="news-div">
                <tag:news newsItem="${newsItem}"/>
                <tag:comment newsItem="${newsItem}"/>
            </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <tag:pageComponent first="${first}" previous="${previous}" next="${next}" last="${last}"/>
</div>
<div id="loader" class="text-center"></div>
<div id="searchResult" style="display: none;"></div>
<script src="<c:url value='/resources/js/svk-ui-1.4.js'/>"></script>

<tag:deleteDialog dialogId="delete-modal"/>

<script src="<c:url value='/resources/js/svk-ui-1.4.js'/>"></script>

<script src="<c:url value='/resources/angular/controllers/news.js'/>"></script>

<script type="text/javascript">

    (function ($, news) {
        var newsFunctions = news.initCommentFunctions(
                ["<spring:message code="text.delete.comment.title"/>", "<spring:message code="text.delete.comment"/>"],
                ["<spring:message code="text.delete.news.title"/>", "<spring:message code="text.delete.news"/>"]
        );
        var searchFunctions = news.initSearchFunctions(["<spring:message code='text.noResults'/>"]);
        newsFunctions.updateButtons();
    })(jQuery, svk.news);
</script>
<%@ include file="../jspf/footer.jspf" %>


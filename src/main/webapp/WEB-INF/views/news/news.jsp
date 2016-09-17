<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<sec:authentication var="principal" property="principal"/>
<div class="row">
    <div class="col-md-12">
        <ul class="breadcrumb">
            <li><spring:message code="nav.home"/>
            </li>
        </ul>
    </div>
</div>
<div class="row m-b-1">
    <div class="col-md-4 col-md-offset-8">
        <div class="input-group">
            <span class="input-group-addon">
  				<span class="glyphicon glyphicon-search"></span>
  			</span>
            <input id="search" type="text" class="form-control" placeholder="<spring:message code="label.search" />">
        </div>
    </div>
</div>
<div class="row m-t-1">
    <div class="col-md-12">
        <div id="blog-homepage">
            <div id="default">
                <div class="news-div" ng-app="soccerApp" ng-controller="newsCtrl" data-ng-init="init()">
                    <div class="row" ng-repeat="(key, value) in page.list">
                        <div class="col-md-12">
                            <div class="post">
                                <h4>
                                    <span ng-bind-html="value.header"></span>
                                    <span style="float: right;">
                                    <a class="commentBtn" data-target="#comments_{{value.id}}" data-toggle="collapse">
                                        <spring:message
                                            code="text.reactions"/>&NonBreakingSpace;<span
                                            class="badge">{{value.comments.length}}</span></a>
                                    </span>
                                </h4>
                                <hr>
                                <span align="left" ng-bind-html="value.content"></span>

                                <p class="author-category"><spring:message code="text.postedby"/>
                                    {{value.postedBy.name}} <spring:message code="text.on"/>
                                    {{value.postDate}}
                                    <span class="btn-group" ng-show="value.editable">
                                        <a href="/editNews.html?newsId={{value.id}}" data-toggle="tooltip"
                                           data-placement="top"
                                           title="<spring:message code="title.editNews"/>"
                                           class="btn btn-default edit"><span
                                                class="glyphicon glyphicon-edit"></span></a>
                                        <a href="/deleteItem.html?newsId={{value.id}}" data-toggle="tooltip"
                                           data-placement="top"
                                           title="<spring:message code="title.deleteNews"/>"
                                           class="btn btn-default delete"><span
                                                class="glyphicon glyphicon-trash delete"></span></a>
                                    </span>

                                </p>
                                <hr/>
                                <div id="comments_{{value.id}}" class="collapse">
                                    <div class="comment post" ng-repeat="comment in value.comments">
                                        <span>{{comment.content}} -
                                            <spring:message code="text.comment.by"/>&nbsp;{{comment.postedBy.name}}&nbsp;<spring:message
                                                    code="text.on"/>&nbsp{{comment.postDate}}
                                            <a ng-show="comment.editable"
                                               data-target="#editComment_{{comment.id}}"
                                               data-toggle="collapse"><spring:message
                                                    code="button.update"/></a>&NonBreakingSpace;|&NonBreakingSpace;<a
                                                    ng-click="showConfirmationMessage = true"
                                                    class="deleteComment" data-toggle="tooltip"
                                                    data-placement="top"><spring:message
                                                    code="button.remove"/></a>
                                                    <span ng-show="showConfirmationMessage">
                                                        <i><spring:message code="text.delete.comment"/></i>
                                                        <a ng-click="showConfirmationMessage = false; deleteComment(value, comment)">
                                                            <i><spring:message code="text.yes"/></i>
                                                        </a>&nbsp;|&nbsp;
                                                        <a ng-click="showConfirmationMessage = false">
                                                            <i><spring:message code="text.no"/></i>
                                                        </a>
                                                    </span>
                                        </span>

                                        <div id="editComment_{{comment.id}}" class="collapse">
                                            <textarea class="form-control" ng-model="comment.content"></textarea>
                                            <a ng-click="editComment(comment, value.id)"
                                               data-toggle="tooltip"
                                               data-placement="top" class="btn btn-primary editComment"><spring:message
                                                    code="button.update"/></a>
                                        </div>
                                    </div>
                                    <sec:authorize access="isAuthenticated()">
                                        <div class="collapse" id="addComment_{{value.id}}">
                                            <textarea class="form-control"></textarea>
                                            <a href="/addComment.html?newsId={{value.id}}" data-toggle="tooltip"
                                               data-placement="top"
                                               class="btn btn-primary addComment"><spring:message
                                                    code="button.add"/></a>
                                        </div>
                                        <a class="btn btn-xs btn-primary addCommentBtn addCommentBtn"
                                           data-toggle="collapse"
                                           data-target="#addComment_{{value.id}}"><spring:message
                                                code="button.add.comment"/></a>
                                    </sec:authorize>
                                    <sec:authorize access="isAnonymous()">
                                        <a class="btn btn-xs btn-primary addCommentBtn" href="/login.html"
                                           data-target="#comment{{value.id}}"><spring:message
                                                code="button.add.comment"/></a></p>
                                    </sec:authorize>
                                </div>
                            </div>
                            </div>
                    </div>


                </div>
            </div>
</div>
</div>


    <script src="<c:url value='/resources/angular/controllers/news.js'/>"></script>

<tag:deleteDialog dialogId="delete-modal"/>

<%@ include file="../jspf/footer.jspf" %>


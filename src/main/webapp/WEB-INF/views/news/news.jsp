<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../jspf/header.jspf" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<sec:authentication var="principal" property="principal"/>
<div ng-app="soccerApp" ng-controller="newsCtrl" data-ng-init="init()">
<div class="row">
    <div class="col-md-12">
        <ul class="breadcrumb">
            <li><spring:message code="nav.home"/>
            </li>
        </ul>
    </div>
</div>
<div class="row m-b-1">
    <div class="col-md-3 col-md-offset-9">
        <div class="input-group">
            <input type="text" ng-model="searchTerm" class="form-control" id="search"
                   placeholder="<spring:message code="label.search" />">
            <span class="input-group-btn">
                    <button ng-click="searchNews(searchTerm, 0)" class="btn btn-primary"><span
                            class="glyphicon glyphicon-search"></span></button>
            </span>
        </div>
    </div>
</div>
<div class="row m-t-1">
    <div class="col-md-12">
        <tag:pagination/>
        <tag:loading/>
        <div id="blog-homepage">
            <div id="default">
                <div class="news-div">
                    <div class="row" ng-repeat="(key, value) in page.list">
                        <div class="col-md-12">
                            <div class="post">
                                <h4>
                                    <span ng-bind-html="value.header"></span>
                                    <span style="float: right;">
                                    <a class="commentBtn"
                                       ng-click="showAllComments=!showAllComments" href="{{'#comments_' + value.id}}">
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
                                        <a ng-click="showDeleteNewsConfirmationMessage = true" data-toggle="tooltip"
                                           data-placement="top"
                                           title="<spring:message code="title.deleteNews"/>"
                                           class="btn btn-default delete"><span
                                                class="glyphicon glyphicon-trash delete"></span></a>
                                        <div class="text-center" ng-show="showDeleteNewsConfirmationMessage">
                                            <i><spring:message code="text.delete.news"/></i>
                                            <a ng-click="showDeleteNewsConfirmationMessage = false; deleteNews(value, $index)">
                                                <i><spring:message code="text.yes"/></i>
                                            </a>&nbsp;|&nbsp;
                                            <a ng-click="showDeleteNewsConfirmationMessage = false">
                                                <i><spring:message code="text.no"/></i>
                                            </a>
                                        </div>
                                    </span>

                                </p>
                                <hr/>
                                <div ng-show="showAllComments" id="{{'comments_' + value.id}}">
                                    <div class="comment post" ng-repeat="comment in value.comments">
                                        <span ng-init="showComment=true" ng-show="showComment">{{comment.content}} -
                                            <spring:message code="text.comment.by"/>&nbsp;{{comment.postedBy.name}}&nbsp;<spring:message
                                                    code="text.on"/>&nbsp{{comment.postDate}}
                                            <span ng-show="comment.editable">
                                            <a ng-click="showComment=false; showEditComment=true"><spring:message
                                                    code="button.update"/></a>
                                                &NonBreakingSpace;|&NonBreakingSpace;
                                                <a ng-click="showConfirmationMessage = true">
                                                    <spring:message code="button.remove"/>
                                                </a>
                                                <div class="m-t-1" ng-show="showConfirmationMessage">
                                                    <i><spring:message code="text.delete.comment"/></i>
                                                    <a ng-click="showConfirmationMessage = false; deleteComment(value, comment)">
                                                        <i><spring:message code="text.yes"/></i>
                                                    </a>&nbsp;|&nbsp;
                                                    <a ng-click="showConfirmationMessage = false">
                                                        <i><spring:message code="text.no"/></i>
                                                    </a>
                                                </div>
                                            </span>
                                        </span>

                                        <div ng-show="showEditComment">
                                            <textarea class="form-control" ng-model="comment.content"></textarea>
                                            <a ng-click="changeComment(value, comment);showEditComment=false"
                                               data-placement="top" class="btn btn-primary editComment"><spring:message
                                                    code="button.update"/></a>
                                        </div>
                                    </div>
                                    <sec:authorize access="isAuthenticated()">
                                        <div ng-show="showNewComment">
                                            <textarea class="form-control" ng-model="newComment.content"></textarea>
                                            <a data-placement="top"
                                               ng-click="postComment(value, newComment); showNewComment=false; showNewCommentBtn=true"
                                               class="btn btn-primary addComment"><spring:message
                                                    code="button.add"/></a>
                                        </div>
                                        <a class="btn btn-xs btn-primary addCommentBtn addCommentBtn"
                                           ng-init="showNewCommentBtn=true" ng-show="showNewCommentBtn"
                                           ng-click="showNewCommentBtn=false; showNewComment=true"><spring:message
                                                code="button.add.comment"/></a>
                                    </sec:authorize>
                                    <sec:authorize access="isAnonymous()">
                                        <a class="btn btn-xs btn-primary addCommentBtn"
                                           href="/login.html"><spring:message
                                                code="button.add.comment"/></a>
                                    </sec:authorize>
                                </div>
                            </div>
                            </div>
                    </div>


                </div>
            </div>
</div>
</div>

</div>

    <script src="<c:url value='/resources/angular/controllers/news.js'/>"></script>

<%@ include file="../jspf/footer.jspf" %>


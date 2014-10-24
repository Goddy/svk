<%@tag description="Comment processing" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="newsItem" required="true" type="be.spring.app.model.News" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<sec:authentication var="principal" property="principal" />

<div id="comments_${newsItem.id}" style="display: none">
<c:forEach var="comment" items="${newsItem.comments}">
    <fmt:formatDate value="${comment.postDate}" var="postDate"/>
    <div class="comment">
        <p><c:out value="${comment.content}"/> -
            <spring:message code="text.commentPostedBy" arguments="${comment.account.fullName},${postDate}" argumentSeparator=","/>

            <sec:authorize access="hasRole('ADMIN')">
                    <a href="/news/editComment.html?commentId=${comment.id}&newsId=${newsItem.id}"><spring:message code="button.update"/></a>&NonBreakingSpace;|&NonBreakingSpace;<a href="/news/deleteComment.html?commentId=${comment.id}newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top"><spring:message code="button.remove"/></a>
            </sec:authorize>
            <sec:authorize access="hasRole('USER')">
                <c:if test="${principal.username == comment.account.username}">
                     <a href="/news/editComment.html?commentId=${comment.id}&newsId=${newsItem.id}"><spring:message code="button.update"/></a>&NonBreakingSpace;|&NonBreakingSpace;<a href="/news/deleteComment.html?commentId=${comment.id}newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top"><spring:message code="button.remove"/></a>
                </c:if>
            </sec:authorize>
        </p>
    </div>
</c:forEach>
        <sec:authorize access="isAuthenticated()">
            <div>
                <textarea class="form-control" id="comment${newsItem.id}"></textarea>
                <a href="addComment.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top" class="btn btn-primary addComment"><spring:message code="button.add"/></a>
            </div>
        </sec:authorize>
</div>
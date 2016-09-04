<%@tag description="News item" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="newsItem" required="true" type="be.spring.app.model.News" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<sec:authentication var="principal" property="principal" />


<sec:authorize access="isAuthenticated()">
    <c:set var="isAuthenticated" value="true"/>
</sec:authorize>
<c:choose>
    <c:when test="${isAuthenticated}">
        <c:set var="accountName" value="${newsItem.account.toString()}"/>
    </c:when>
    <c:otherwise>
        <c:set var="accountName" value="${newsItem.account.fullName}"/>
    </c:otherwise>
</c:choose>

<div class="row">
    <div class="col-md-12">
        <div class="post">
            <h4>
                <c:out value="${newsItem.header}"/>
                <span style="float: right;">
                    <a href="comments_${newsItem.id}" class="commentBtn"><spring:message
                            code="text.reactions"/>&NonBreakingSpace;<span
                            class="badge">${fn:length(newsItem.comments)}</span></a>
                </span>
            </h4>
            <hr>
            <span align="left">${newsItem.content}</span>

            <p class="author-category"><spring:message code="text.postedby"/> <c:out
                    value="${accountName}"/> <spring:message code="text.on"/>
                <fmt:formatDate value="${newsItem.postDate}"/>
                <sec:authorize access="hasRole('ADMIN')">
            <span class="btn-group">
                <a href="/editNews.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top"
                   title="<spring:message code="title.editNews"/>"
                   class="btn btn-default edit"><span class="glyphicon glyphicon-edit"></span></a>
                <a href="/deleteItem.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top"
                   title="<spring:message code="title.deleteNews"/>"
                   class="btn btn-default delete"><span class="glyphicon glyphicon-trash delete"></span></a>
            </span>
                </sec:authorize>
                <sec:authorize access="hasRole('USER')">
                    <c:if test="${principal.username == newsItem.account.username}">
                    <span class="btn-group">
                        <a href="/editNews.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top"
                           title="<spring:message code="title.editNews"/>"
                           class="btn btn-default edit"><span class="glyphicon glyphicon-edit"></span></a>
                        <a href="/deleteItem.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top"
                           title="<spring:message code="title.deleteNews"/>"
                           class="btn btn-default delete"><span class="glyphicon glyphicon-trash delete"></span></a>
                    </span>
                    </c:if>
                </sec:authorize>
            </p>
            <jsp:doBody/>
        </div>
    </div>
</div>
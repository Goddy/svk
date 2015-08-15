<%@tag description="News item" pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="newsItem" required="true" type="be.spring.app.model.News" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<sec:authentication var="principal" property="principal" />

<div class="panel panel-info">
<div class="panel-heading"><c:out value="${newsItem.header}"/>
    <div style="float: right;">
        <a href="comments_${newsItem.id}" class="commentBtn"><spring:message
                code="text.reactions"/>&NonBreakingSpace;<span class="badge">${fn:length(newsItem.comments)}</span></a>
    </div>
</div>
<div class="panel-body">
    <p align="left">${newsItem.content}</p>

    <div style="text-align: right"><spring:message code="text.postedby"/> <c:out
            value="${newsItem.account.fullName}"/> <spring:message code="text.on"/>
        <fmt:formatDate value="${newsItem.postDate}"/>
        <sec:authorize access="hasRole('ADMIN')">
            <div class="btn-group">
                <a href="/editNews.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top"
                   title="<spring:message code="title.editNews"/>"
                   class="btn btn-default glyphicon glyphicon-edit edit"><span class=""></span></a>
                <a href="/deleteItem.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top"
                   title="<spring:message code="title.deleteNews"/>"
                   class="btn btn-default glyphicon glyphicon-trash delete"><span class="delete"></span></a>
            </div>
        </sec:authorize>
        <sec:authorize access="hasRole('USER')">
            <c:if test="${principal.username == newsItem.account.username}">
                <div class="btn-group">
                    <a href="/editNews.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top"
                       title="<spring:message code="title.editNews"/>"
                       class="btn btn-default glyphicon glyphicon-edit edit"><span class=""></span></a>
                    <a href="/deleteItem.html?newsId=${newsItem.id}" data-toggle="tooltip" data-placement="top"
                       title="<spring:message code="title.deleteNews"/>"
                       class="btn btn-default glyphicon glyphicon-trash delete"><span class="delete"></span></a>
                </div>
            </c:if>
        </sec:authorize>
    </div>
</div>
</div>